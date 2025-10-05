package com.example.ordenaris.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ordenaris.model.dto.ResultadoEvaluacionDTO;
import com.example.ordenaris.model.dto.ResultadoReglaDTO;
import com.example.ordenaris.model.dto.SolicitudEvaluacionDTO;
import com.example.ordenaris.model.entity.DatosContables;
import com.example.ordenaris.model.entity.Empresa;
import com.example.ordenaris.model.entity.VerificacionLegal;
import com.example.ordenaris.model.enums.ProductoFinanciero;
import com.example.ordenaris.model.enums.Riesgo;

@Service
public class OrdenarisRiskEngineService {

    private final DatosContablesProviderImpl datosContablesProvider;
    private final HistorialPagosProviderImpl historialPagosProvider;
    private final VerificacionLegalProviderImpl verificacionLegalProvider;

    public OrdenarisRiskEngineService(
    		DatosContablesProviderImpl datosContablesProvider,
            HistorialPagosProviderImpl historialPagosProvider,
            VerificacionLegalProviderImpl verificacionLegalProvider
    ) {
        this.datosContablesProvider = datosContablesProvider;
        this.historialPagosProvider = historialPagosProvider;
        this.verificacionLegalProvider = verificacionLegalProvider;
    }

    public ResultadoEvaluacionDTO evaluarRiesgo(Empresa empresa, SolicitudEvaluacionDTO solicitud) {
        List<ResultadoReglaDTO> resultados = new ArrayList<>();
        Riesgo nivelRiesgo = Riesgo.BAJO;

        DatosContables contables = datosContablesProvider
                .obtenerDatosContables(empresa)
                .orElseThrow(() -> new RuntimeException("No se encontraron datos contables"));

        // deuda Activa > 90 días -> RECHAZADO
        boolean tieneDeudaVencida = historialPagosProvider.tieneDeudaVencida(empresa.getId());

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

        // alta solicitud vs ventas
        ResultadoReglaDTO reglaAltaSolicitud = new ResultadoReglaDTO();
        reglaAltaSolicitud.setNombreRegla("Alta Solicitud vs Ventas");
        double limite = contables.getVentasAnuales() / 12 * 8;
        if (solicitud.getMontoSolicitado().doubleValue() > limite) {
            reglaAltaSolicitud.setCumple(true);
            reglaAltaSolicitud.setDescripcionResultado("Monto solicitado > 8x promedio mensual de ventas");
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.ALTO);
        } else {
            reglaAltaSolicitud.setCumple(false);
            reglaAltaSolicitud.setDescripcionResultado("Monto dentro de límite razonable");
        }
        resultados.add(reglaAltaSolicitud);

        //empresa nueva menro a 18 meses
        ResultadoReglaDTO reglaEmpresaNueva = new ResultadoReglaDTO();
        reglaEmpresaNueva.setNombreRegla("Empresa Nueva");
        if (empresa.getAntiguedadAnios() < 2) {
            reglaEmpresaNueva.setCumple(true);
            reglaEmpresaNueva.setDescripcionResultado("Empresa con <18 meses de existencia");
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.MEDIO);
        } else {
            reglaEmpresaNueva.setCumple(false);
            reglaEmpresaNueva.setDescripcionResultado("Empresa con antigüedad suficiente");
        }
        resultados.add(reglaEmpresaNueva);

        //verificacion legal
        VerificacionLegal legal = verificacionLegalProvider
                .obtenerVerificacionLegal(empresa)
                .orElse(null);

        ResultadoReglaDTO reglaLegal = new ResultadoReglaDTO();
        reglaLegal.setNombreRegla("Demanda Legal Abierta");
        if (legal != null && Boolean.TRUE.equals(legal.getTieneDemandas())) {
            reglaLegal.setCumple(true);
            reglaLegal.setDescripcionResultado("Empresa con juicio mercantil en curso");
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.ALTO);
        } else {
            reglaLegal.setCumple(false);
            reglaLegal.setDescripcionResultado("Sin demandas legales activas");
        }
        resultados.add(reglaLegal);

        //Historial pagos
        boolean historialExcelente = historialPagosProvider.tieneHistorialExcelente(empresa.getId());


        ResultadoReglaDTO reglaHistorial = new ResultadoReglaDTO();
        reglaHistorial.setNombreRegla("Historial Excelente");
        if (historialExcelente) {
            reglaHistorial.setCumple(true);
            reglaHistorial.setDescripcionResultado("Últimos pagos en tiempo y sin refinanciamiento");
            nivelRiesgo = reducirRiesgo(nivelRiesgo);
        } else {
            reglaHistorial.setCumple(false);
            reglaHistorial.setDescripcionResultado("Historial de pagos con retrasos");
        }
        resultados.add(reglaHistorial);

        //producto estricto
        ResultadoReglaDTO reglaProducto = new ResultadoReglaDTO();
        reglaProducto.setNombreRegla("producto estricto");
        if (solicitud.getProductoFinanciero() == ProductoFinanciero.ARRENDAMIENTO_FINANCIERO) {
            reglaProducto.setCumple(true);
            reglaProducto.setDescripcionResultado("producto arrendamiento financiero => riesgo aumentado");
            nivelRiesgo = elevarRiesgo(nivelRiesgo, Riesgo.MEDIO);
        } else {
            reglaProducto.setCumple(false);
            reglaProducto.setDescripcionResultado("producto sin restriccion especial");
        }
        resultados.add(reglaProducto);

        //resultado final
        ResultadoEvaluacionDTO resultado = new ResultadoEvaluacionDTO();
        resultado.setNivelRiesgoFinal(nivelRiesgo);
        resultado.setReglasEvaluadas(resultados);

        // Motivo final (puede ser la primera regla determinante)
        resultado.setMotivoFinal(resultados.stream()
                .filter(ResultadoReglaDTO::isCumple)
                .map(ResultadoReglaDTO::getNombreRegla)
                .findFirst()
                .orElse("Evaluación estándar"));

        return resultado;
        }
    private Riesgo elevarRiesgo(Riesgo actual, Riesgo sugerido) {
        if (actual == Riesgo.RECHAZADO || sugerido == Riesgo.RECHAZADO) return Riesgo.RECHAZADO;
        if (sugerido.ordinal() > actual.ordinal()) return sugerido;
        return actual;
    }

    private Riesgo reducirRiesgo(Riesgo actual) {
        if (actual == Riesgo.RECHAZADO) return actual;
        if (actual == Riesgo.ALTO) return Riesgo.MEDIO;
        if (actual == Riesgo.MEDIO) return Riesgo.BAJO;
        return actual;
    }
}
