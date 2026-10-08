package com.example.ordenaris.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.ordenaris.model.enums.ProductoFinanciero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SolicitudEvaluacionDTO {

    @NotBlank(message = "El id de empresa es obligatorio")
    private String empresaId;

    @NotNull(message = "El monto solicitado es obligatorio")
    @Positive(message = "El monto solicitado debe ser mayor a cero")
    private BigDecimal montoSolicitado;

    @NotNull(message = "El producto financiero es obligatorio")
    private ProductoFinanciero productoFinanciero;

    @NotNull(message = "La fecha de solicitud es obligatoria")
    private LocalDate fechaSolicitud;
	public String getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(String empresaId) {
		this.empresaId = empresaId;
	}
	public BigDecimal getMontoSolicitado() {
		return montoSolicitado;
	}
	public void setMontoSolicitado(BigDecimal montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}
	public ProductoFinanciero getProductoFinanciero() {
		return productoFinanciero;
	}
	public void setProductoFinanciero(ProductoFinanciero productoFinanciero) {
		this.productoFinanciero = productoFinanciero;
	}
	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
    
    
}
