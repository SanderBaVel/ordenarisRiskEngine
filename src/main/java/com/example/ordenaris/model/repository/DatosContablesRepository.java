package com.example.ordenaris.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordenaris.model.entity.DatosContables;
import com.example.ordenaris.model.entity.Empresa;

@Repository
public interface DatosContablesRepository extends JpaRepository<DatosContables, Long>{
	Optional<DatosContables> findTopByEmpresaOrderByFechaActualizacionDesc(Empresa empresa);
}
