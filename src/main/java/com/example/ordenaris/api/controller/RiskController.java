package com.example.ordenaris.api.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordenaris.model.dto.ResultadoEvaluacionDTO;
import com.example.ordenaris.model.dto.SolicitudEvaluacionDTO;
import com.example.ordenaris.model.entity.Empresa;
import com.example.ordenaris.model.repository.EmpresaRepository;
import com.example.ordenaris.service.OrdenarisRiskEngineService;

@RestController
@RequestMapping("/api/riesgo")
public class RiskController {
	
    private final OrdenarisRiskEngineService riskEngineService;
    private final EmpresaRepository empresaRepository;

    public RiskController(OrdenarisRiskEngineService riskEngineService, EmpresaRepository empresaRepository) {
        this.riskEngineService = riskEngineService;
        this.empresaRepository = empresaRepository;
    }
    
    /**
     * endpoint para evaluar el riesgo crediticio en una empresa
     * @param solicitud DTO con datos de la solicitud
     * @return Resultado de evaluación de riesgo
     */
//    @PostMapping("/evaluar")
//    public ResponseEntity<ResultadoEvaluacionDTO> evaluarRiesgo(@RequestBody SolicitudEvaluacionDTO solicitud) {
//        Empresa empresa = empresaRepository.findById(Long.valueOf(solicitud.getEmpresaId()))
//                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
//
//        ResultadoEvaluacionDTO resultado = riskEngineService.evaluarRiesgo(empresa, solicitud);
//        return ResponseEntity.ok(resultado);
//    }
    
    @PostMapping("/evaluar")
    public ResponseEntity<?> evaluarRiesgo(@RequestBody SolicitudEvaluacionDTO solicitud) {
        try {
            Empresa empresa = empresaRepository.findById(Long.valueOf(solicitud.getEmpresaId()))
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

            ResultadoEvaluacionDTO resultado = riskEngineService.evaluarRiesgo(empresa, solicitud);
            return ResponseEntity.ok(resultado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Error inesperado al evaluar el riesgo",
                    "detalle", e.getMessage()
            ));
        }
    }


}
