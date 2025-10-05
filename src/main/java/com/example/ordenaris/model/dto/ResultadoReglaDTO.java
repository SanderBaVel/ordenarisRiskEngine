package com.example.ordenaris.model.dto;


public class ResultadoReglaDTO {
    private String nombreRegla;
    private boolean cumple;
    private String descripcionResultado;


	public String getNombreRegla() {
		return nombreRegla;
	}
	public void setNombreRegla(String nombreRegla) {
		this.nombreRegla = nombreRegla;
	}
	public boolean isCumple() {
		return cumple;
	}
	public void setCumple(boolean cumple) {
		this.cumple = cumple;
	}
	public String getDescripcionResultado() {
		return descripcionResultado;
	}
	public void setDescripcionResultado(String descripcionResultado) {
		this.descripcionResultado = descripcionResultado;
	}
    
    
}
