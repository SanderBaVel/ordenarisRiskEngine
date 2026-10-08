package com.example.ordenaris.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.ordenaris.model.entity.DetallePago;
import com.example.ordenaris.model.repository.DetallePagoRepository;
import com.example.ordenaris.service.provider.IHistorialPagosProvider;

@Component
public class HistorialPagosProviderImpl implements IHistorialPagosProvider{
	
    private final DetallePagoRepository repository;

    public HistorialPagosProviderImpl(DetallePagoRepository repository) {
        this.repository = repository;
    }

	@Override
	public boolean tieneDeudaVencida(Long empresaId) {
		return tieneDeudaVencida(empresaId, LocalDate.now());
	}

	@Override
	public boolean tieneDeudaVencida(Long empresaId, LocalDate fechaCorte) {
		if (fechaCorte == null) {
			fechaCorte = LocalDate.now();
		}
		final LocalDate corte = fechaCorte;
		List<DetallePago> pagosPendientes = repository.findByEmpresaIdAndFechaPagoIsNull(empresaId);
		return pagosPendientes.stream()
				.anyMatch(p -> ChronoUnit.DAYS.between(p.getFechaVencimiento(), corte) > 90);
	}

	@Override
	public boolean tieneHistorialExcelente(Long empresaId) {
		List<DetallePago> ultimos12 = repository.findTop12ByEmpresaIdAndFechaPagoIsNotNullOrderByFechaPagoDesc(empresaId);
		if (ultimos12.size() < 12) {
			return false;
		}

		return ultimos12.stream().allMatch(p -> !p.isRefinanciado() && !p.getFechaPago().isAfter(p.getFechaVencimiento()));
	}

}
