package com.example.ordenaris.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordenaris.model.entity.DetallePago;


@Repository
public interface DetallePagoRepository extends JpaRepository<DetallePago, Long>{
	List<DetallePago> findByEmpresaId(Long empresaId);

}
