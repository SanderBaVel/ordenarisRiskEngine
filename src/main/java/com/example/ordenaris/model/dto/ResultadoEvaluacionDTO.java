package com.example.ordenaris.model.dto;

import java.util.List;

import com.example.ordenaris.model.enums.Riesgo;

public class ResultadoEvaluacionDTO {

    private Riesgo nivelRiesgoFinal;
    private List<ResultadoReglaDTO> reglasEvaluadas;
    private String motivoFinal;
    
	public Riesgo getNivelRiesgoFinal() {
		return nivelRiesgoFinal;
	}
	public void setNivelRiesgoFinal(Riesgo nivelRiesgoFinal) {
		this.nivelRiesgoFinal = nivelRiesgoFinal;
	}
	public List<ResultadoReglaDTO> getReglasEvaluadas() {
		return reglasEvaluadas;
	}
	public void setReglasEvaluadas(List<ResultadoReglaDTO> reglasEvaluadas) {
		this.reglasEvaluadas = reglasEvaluadas;
	}
	public String getMotivoFinal() {
		return motivoFinal;
	}
	public void setMotivoFinal(String motivoFinal) {
		this.motivoFinal = motivoFinal;
	}
    
    
}
