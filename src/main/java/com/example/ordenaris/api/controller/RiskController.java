package com.example.ordenaris.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordenaris.model.dto.ResultadoEvaluacionDTO;
import com.example.ordenaris.model.dto.SolicitudEvaluacionDTO;
import com.example.ordenaris.service.OrdenarisRiskEngineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = {"/api/riesgo", "/api"})
public class RiskController {

    private final OrdenarisRiskEngineService riskEngineService;

    public RiskController(OrdenarisRiskEngineService riskEngineService) {
        this.riskEngineService = riskEngineService;
    }

    /**
     * Endpoint para evaluar el riesgo crediticio de una empresa.
     * Compatible con /api/riesgo/evaluar y /api/evaluar.
     *
     * @param solicitud DTO con datos de la solicitud
     * @return Resultado detallado de la evaluación de riesgo
     */
    @PostMapping("/evaluar")
    public ResponseEntity<ResultadoEvaluacionDTO> evaluarRiesgo(@Valid @RequestBody SolicitudEvaluacionDTO solicitud) {
        ResultadoEvaluacionDTO resultado = riskEngineService.evaluarRiesgo(solicitud);
        return ResponseEntity.ok(resultado);
    }
}
