package com.example.ordenaris.service.provider;



public interface IHistorialPagosProvider {
	boolean tieneDeudaVencida(Long empresaId);
	boolean tieneHistorialExcelente(Long empresaId);
}
