package com.example.ordenaris.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.ordenaris.model.entity.DatosContables;
import com.example.ordenaris.model.entity.Empresa;
import com.example.ordenaris.model.repository.DatosContablesRepository;
import com.example.ordenaris.service.provider.IDatosContablesProvider;

@Component
public class DatosContablesProviderImpl implements IDatosContablesProvider{

    private final DatosContablesRepository repository;

    public DatosContablesProviderImpl(DatosContablesRepository repository) {
        this.repository = repository;
    }
    
	@Override
	public Optional<DatosContables> obtenerDatosContables(Empresa empresa) {
		return repository.findTopByEmpresaOrderByFechaActualizacionDesc(empresa);
	}

}
