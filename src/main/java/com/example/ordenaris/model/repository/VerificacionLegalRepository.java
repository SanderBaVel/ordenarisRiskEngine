package com.example.ordenaris.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordenaris.model.entity.Empresa;
import com.example.ordenaris.model.entity.VerificacionLegal;

@Repository
public interface VerificacionLegalRepository extends JpaRepository<VerificacionLegal, Long> {
	Optional<VerificacionLegal> findTopByEmpresaOrderByFechaVerificacionDesc(Empresa empresa);

}
