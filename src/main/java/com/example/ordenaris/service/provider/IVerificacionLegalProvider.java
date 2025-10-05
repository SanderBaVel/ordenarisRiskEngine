package com.example.ordenaris.service.provider;

import java.util.Optional;

import com.example.ordenaris.model.entity.Empresa;
import com.example.ordenaris.model.entity.VerificacionLegal;

public interface IVerificacionLegalProvider {

	Optional<VerificacionLegal> obtenerVerificacionLegal(Empresa empresa);
}
