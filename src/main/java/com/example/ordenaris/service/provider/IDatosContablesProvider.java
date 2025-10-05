package com.example.ordenaris.service.provider;

import java.util.Optional;

import com.example.ordenaris.model.entity.DatosContables;
import com.example.ordenaris.model.entity.Empresa;

public interface IDatosContablesProvider {
	Optional<DatosContables> obtenerDatosContables(Empresa empresa);
}
