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
@Table(name = "verificacion_legal")
public class VerificacionLegal {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_verificacion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @Column(name = "tiene_demandas")
    private Boolean tieneDemandas;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_verificacion")
    private LocalDate fechaVerificacion;

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

	public Boolean getTieneDemandas() {
		return tieneDemandas;
	}

	public void setTieneDemandas(Boolean tieneDemandas) {
		this.tieneDemandas = tieneDemandas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaVerificacion() {
		return fechaVerificacion;
	}

	public void setFechaVerificacion(LocalDate fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}
    
    
}
