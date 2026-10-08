package com.example.ordenaris.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "datos_contables")
public class DatosContables {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contable")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @Column(name = "ventas_anuales")
    private BigDecimal ventasAnuales;

    @Column(name = "deuda_activa")
    private BigDecimal deudaActiva;

    @Column(name = "flujo_efectivo")
    private BigDecimal flujoEfectivo;

    @Column(name = "fecha_actualizacion")
    private LocalDate fechaActualizacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public BigDecimal getVentasAnuales() {
		return ventasAnuales;
	}

	public void setVentasAnuales(BigDecimal ventasAnuales) {
		this.ventasAnuales = ventasAnuales;
	}

	public BigDecimal getDeudaActiva() {
		return deudaActiva;
	}

	public void setDeudaActiva(BigDecimal deudaActiva) {
		this.deudaActiva = deudaActiva;
	}

	public BigDecimal getFlujoEfectivo() {
		return flujoEfectivo;
	}

	public void setFlujoEfectivo(BigDecimal flujoEfectivo) {
		this.flujoEfectivo = flujoEfectivo;
	}

	public LocalDate getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDate fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

    
}
