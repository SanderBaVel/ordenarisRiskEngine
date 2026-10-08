package com.example.ordenaris.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ordenaris.exception.ResourceNotFoundException;
import com.example.ordenaris.model.dto.ResultadoEvaluacionDTO;
import com.example.ordenaris.model.dto.ResultadoReglaDTO;
import com.example.ordenaris.model.dto.SolicitudEvaluacionDTO;
import com.example.ordenaris.model.entity.DatosContables;
import com.example.ordenaris.model.entity.Empresa;
import com.example.ordenaris.model.entity.VerificacionLegal;
import com.example.ordenaris.model.enums.ProductoFinanciero;
import com.example.ordenaris.model.enums.Riesgo;
import com.example.ordenaris.model.repository.EmpresaRepository;
import com.example.ordenaris.service.provider.IDatosContablesProvider;
import com.example.ordenaris.service.provider.IHistorialPagosProvider;
import com.example.ordenaris.service.provider.IVerificacionLegalProvider;

@Service
public class OrdenarisRiskEngineService {

    private final EmpresaRepository empresaRepository;
    private final IDatosContablesProvider datosContablesProvider;
    private final IHistorialPagosProvider historialPagosProvider;
    private final IVerificacionLegalProvider verificacionLegalProvider;

    public OrdenarisRiskEngineService(
            EmpresaRepository empresaRepository,
            IDatosContablesProvider datosContablesProvider,
            IHistorialPagosProvider historialPagosProvider,
            IVerificacionLegalProvider verificacionLegalProvider
    ) {
        this.empresaRepository = empresaRepository;
        this.datosContablesProvider = datosContablesProvider;
        this.historialPagosProvider = historialPagosProvider;
        this.verificacionLegalProvider = verificacionLegalProvider;
    }

    public ResultadoEvaluacionDTO evaluarRiesgo(SolicitudEvaluacionDTO solicitud) {
        Long empresaId;
        try {
            empresaId = Long.valueOf(solicitud.getEmpresaId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El id de la empresa debe ser numérico: " + solicitud.getEmpresaId());
        }

        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con id: " + empresaId));

        return evaluarRiesgo(empresa, solicitud);
    }

    public ResultadoEvaluacionDTO evaluarRiesgo(Empresa empresa, SolicitudEvaluacionDTO solicitud) {
        List<ResultadoReglaDTO> resultados = new ArrayList<>();
        Riesgo nivelRiesgo = Riesgo.BAJO;
        LocalDate fechaCorte = solicitud.getFechaSolicitud() != null ? solicitud.getFechaSolicitud() : LocalDate.now();

        DatosContables contables = datosContablesProvider
                .obtenerDatosContables(empresa)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontraron datos contables para la empresa: " + empresa.getNombre()));

        // Regla 1: Deuda Activa > 90 días -> RECHAZADO
        boolean tieneDeudaVencida = historialPagosProvider.tieneDeudaVencida(empresa.getId(), fechaCorte);

        ResultadoReglaDTO reglaDeuda = new ResultadoReglaDTO();
        reglaDeuda.setNombreRegla("Deuda Activa");
        if (tieneDeudaVencida) {
            reglaDeuda.setCumple(true);
            reglaDeuda.setDescripcionResultado("Empresa con deuda activa > 90 días");
            nivelRiesgo = Riesgo.RECHAZADO;
        } else {
            reglaDeuda.setCumple(false);
            reglaDeuda.setDescripcionResultado("Sin deuda activa relevante");
        }
        resultados.add(reglaDeuda);

        // Regla 2: Alta Solicitud vs Ventas (monto > 8x promedio mensual de ventas)
        ResultadoReglaDTO reglaAltaSolicitud = new ResultadoReglaDTO();
        reglaAltaSolicitud.setNombreRegla("Alta Solicitud vs Ventas");
        BigDecimal ventasAnuales = contables.getVentasAnuales() != null ? contables.getVentasAnuales() : BigDecimal.ZERO;
        BigDecimal promedioMensual = ventasAnuales.divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);
        BigDecimal limite = promedioMensual.multiply(BigDecimal.valueOf(8));

        if (solicitud.getMontoSolicitado() != null && solicitud.getMontoSolicitado().compareTo(limite) > 0) {
            reglaAltaSolicitud.setCumple(true);
            reglaAltaSolicitud.setDescripcionResultado("Monto solicitado (" + solicitud.getMontoSolicitado() + ") supera el límite de 8x ventas mensuales (" + limite.setScale(2, RoundingMode.HALF_UP) + ")");
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.ALTO);
        } else {
            reglaAltaSolicitud.setCumple(false);
            reglaAltaSolicitud.setDescripcionResultado("Monto dentro del límite razonable de ventas");
        }
        resultados.add(reglaAltaSolicitud);

        // Regla 3: Empresa Nueva (antigüedad < 2 años / 24 meses)
        ResultadoReglaDTO reglaEmpresaNueva = new ResultadoReglaDTO();
        reglaEmpresaNueva.setNombreRegla("Empresa Nueva");
        long mesesAntiguedad = empresa.getAntiguedadMeses(fechaCorte);
        if (mesesAntiguedad < 24) {
            reglaEmpresaNueva.setCumple(true);
            reglaEmpresaNueva.setDescripcionResultado("Empresa con antigüedad menor a 2 años (" + mesesAntiguedad + " meses)");
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.MEDIO);
        } else {
            reglaEmpresaNueva.setCumple(false);
            reglaEmpresaNueva.setDescripcionResultado("Empresa con antigüedad suficiente (" + mesesAntiguedad + " meses)");
        }
        resultados.add(reglaEmpresaNueva);

        // Regla 4: Verificación Legal (Demandas abiertas)
        VerificacionLegal legal = verificacionLegalProvider
                .obtenerVerificacionLegal(empresa)
                .orElse(null);

        ResultadoReglaDTO reglaLegal = new ResultadoReglaDTO();
        reglaLegal.setNombreRegla("Demanda Legal Abierta");
        if (legal != null && Boolean.TRUE.equals(legal.getTieneDemandas())) {
            reglaLegal.setCumple(true);
            reglaLegal.setDescripcionResultado("Empresa con juicio mercantil en curso: " + (legal.getDescripcion() != null ? legal.getDescripcion() : "Demandas activas"));
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.ALTO);
        } else {
            reglaLegal.setCumple(false);
            reglaLegal.setDescripcionResultado("Sin demandas legales activas");
        }
        resultados.add(reglaLegal);

        // Regla 5: Historial Pagos Excelente (reduce riesgo)
        boolean historialExcelente = historialPagosProvider.tieneHistorialExcelente(empresa.getId());

        ResultadoReglaDTO reglaHistorial = new ResultadoReglaDTO();
        reglaHistorial.setNombreRegla("Historial Excelente");
        if (historialExcelente) {
            reglaHistorial.setCumple(true);
            reglaHistorial.setDescripcionResultado("Últimos 12 pagos en tiempo y forma sin refinanciamientos");
            nivelRiesgo = reducirRiesgo(nivelRiesgo);
        } else {
            reglaHistorial.setCumple(false);
            reglaHistorial.setDescripcionResultado("Historial de pagos con retrasos o pagos insuficientes");
        }
        resultados.add(reglaHistorial);

        // Regla 6: Producto Estricto (arrendamiento financiero eleva a MEDIO)
        ResultadoReglaDTO reglaProducto = new ResultadoReglaDTO();
        reglaProducto.setNombreRegla("Producto Estricto");
        if (solicitud.getProductoFinanciero() == ProductoFinanciero.ARRENDAMIENTO_FINANCIERO) {
            reglaProducto.setCumple(true);
            reglaProducto.setDescripcionResultado("Producto de arrendamiento financiero con mayor exigencia crediticia");
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.MEDIO);
        } else {
            reglaProducto.setCumple(false);
            reglaProducto.setDescripcionResultado("Producto sin restricción especial");
        }
        resultados.add(reglaProducto);

        // Resultado Final
        ResultadoEvaluacionDTO resultado = new ResultadoEvaluacionDTO();
        resultado.setNivelRiesgoFinal(nivelRiesgo);
        resultado.setReglasEvaluadas(resultados);
        resultado.setMotivoFinal(determinarMotivoFinal(nivelRiesgo, resultados));

        return resultado;
    }

    private String determinarMotivoFinal(Riesgo nivelRiesgoFinal, List<ResultadoReglaDTO> reglas) {
        if (nivelRiesgoFinal == Riesgo.RECHAZADO) {
            return reglas.stream()
                    .filter(r -> r.isCumple() && r.getNombreRegla().equalsIgnoreCase("Deuda Activa"))
                    .map(ResultadoReglaDTO::getDescripcionResultado)
                    .findFirst()
                    .orElse("Solicitud rechazada por políticas de riesgo crediticio");
        }
        if (nivelRiesgoFinal == Riesgo.ALTO) {
            return reglas.stream()
                    .filter(r -> r.isCumple() && (r.getNombreRegla().contains("Alta Solicitud") || r.getNombreRegla().contains("Demanda Legal")))
                    .map(ResultadoReglaDTO::getDescripcionResultado)
                    .findFirst()
                    .orElse("Riesgo alto detectado en la evaluación");
        }
        if (nivelRiesgoFinal == Riesgo.MEDIO) {
            return reglas.stream()
                    .filter(r -> r.isCumple() && (r.getNombreRegla().contains("Empresa Nueva") || r.getNombreRegla().contains("Producto Estricto")))
                    .map(ResultadoReglaDTO::getDescripcionResultado)
                    .findFirst()
                    .orElse("Riesgo moderado identificado");
        }
        return reglas.stream()
                .filter(r -> r.isCumple() && r.getNombreRegla().equalsIgnoreCase("Historial Excelente"))
                .map(ResultadoReglaDTO::getDescripcionResultado)
                .findFirst()
                .orElse("Evaluación favorable sin alertas de riesgo");
    }

    private Riesgo elevarRiesgo(Riesgo actual, Riesgo sugerido) {
        if (actual == Riesgo.RECHAZADO || sugerido == Riesgo.RECHAZADO) return Riesgo.RECHAZADO;
        if (actual == Riesgo.ALTO || sugerido == Riesgo.ALTO) return Riesgo.ALTO;
        if (actual == Riesgo.MEDIO || sugerido == Riesgo.MEDIO) return Riesgo.MEDIO;
        return Riesgo.BAJO;
    }

    private Riesgo reducirRiesgo(Riesgo actual) {
        if (actual == Riesgo.RECHAZADO) return actual;
        if (actual == Riesgo.ALTO) return Riesgo.MEDIO;
        if (actual == Riesgo.MEDIO) return Riesgo.BAJO;
        return Riesgo.BAJO;
    }
}
