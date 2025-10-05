package com.example.ordenaris.model.entity;

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
    private Double ventasAnuales;

    @Column(name = "deuda_activa")
    private Double deudaActiva;

    @Column(name = "flujo_efectivo")
    private Double flujoEfectivo;

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

	public Double getVentasAnuales() {
		return ventasAnuales;
	}

	public void setVentasAnuales(Double ventasAnuales) {
		this.ventasAnuales = ventasAnuales;
	}

	public Double getDeudaActiva() {
		return deudaActiva;
	}

	public void setDeudaActiva(Double deudaActiva) {
		this.deudaActiva = deudaActiva;
	}

	public Double getFlujoEfectivo() {
		return flujoEfectivo;
	}

	public void setFlujoEfectivo(Double flujoEfectivo) {
		this.flujoEfectivo = flujoEfectivo;
	}

	public LocalDate getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDate fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

    
}
