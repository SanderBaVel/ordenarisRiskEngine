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

        List<DetallePago> pagos = repository.findByEmpresaId(empresaId);

        return pagos.stream().anyMatch(p -> p.getFechaPago() == null && ChronoUnit.DAYS.between(p.getFechaVencimiento(), LocalDate.now()) > 90);
	}

	@Override
	public boolean tieneHistorialExcelente(Long empresaId) {

        List<DetallePago> pagos = repository.findByEmpresaId(empresaId);

        // ordenar por fecha de pago descendente y tomar los ultimos 12 pagos completados
        List<DetallePago> ultimos12 = pagos.stream()
                .filter(p -> p.getFechaPago() != null)
                .sorted(Comparator.comparing(DetallePago::getFechaPago).reversed())
                .limit(12)
                .toList();

        if (ultimos12.size() < 12) return false;

        return ultimos12.stream().allMatch(p -> !p.isRefinanciado() && !p.getFechaPago().isAfter(p.getFechaVencimiento()));
	}

}
