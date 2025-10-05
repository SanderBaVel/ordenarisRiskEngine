package com.example.ordenaris.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.ordenaris.model.entity.Empresa;
import com.example.ordenaris.model.entity.VerificacionLegal;
import com.example.ordenaris.model.repository.VerificacionLegalRepository;
import com.example.ordenaris.service.provider.IVerificacionLegalProvider;

@Component
public class VerificacionLegalProviderImpl implements IVerificacionLegalProvider {

    private final VerificacionLegalRepository repository;

    public VerificacionLegalProviderImpl(VerificacionLegalRepository repository) {
        this.repository = repository;

    }

	@Override
	public Optional<VerificacionLegal> obtenerVerificacionLegal(Empresa empresa) {
		return repository.findTopByEmpresaOrderByFechaVerificacionDesc(empresa);
	}

}
