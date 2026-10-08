package com.example.ordenaris.model.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long id;
	

    @Column(name = "nombre")
	private String nombre;
    

    @Column(name = "rfc")
	private String rfc;
	

    @Column(name = "fecha_registro")
	private LocalDate fechaRegistro;

    
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRfc() {
		return rfc;
	}


	public void setRfc(String rfc) {
		this.rfc = rfc;
	}


	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
    @Transient
    public long getAntiguedadMeses(LocalDate fechaCorte) {
        if (this.fechaRegistro == null || fechaCorte == null) return 0;
        return ChronoUnit.MONTHS.between(this.fechaRegistro, fechaCorte);
    }

    @Transient
    public int getAntiguedadAnios(LocalDate fechaCorte) {
        if (this.fechaRegistro == null || fechaCorte == null) return 0;
        return Period.between(this.fechaRegistro, fechaCorte).getYears();
    }

    @Transient 
    public int getAntiguedadAnios() {
        return getAntiguedadAnios(LocalDate.now());
    }
}



