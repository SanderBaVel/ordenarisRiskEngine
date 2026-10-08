package com.example.ordenaris.service.provider;

import java.time.LocalDate;

public interface IHistorialPagosProvider {
	boolean tieneDeudaVencida(Long empresaId);
	boolean tieneDeudaVencida(Long empresaId, LocalDate fechaCorte);
	boolean tieneHistorialExcelente(Long empresaId);
}
