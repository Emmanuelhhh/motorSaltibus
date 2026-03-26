package com.tde.motorSALTIBUS.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tde.motorSALTIBUS.persistence.origin.entity.DescargasCARD;
import com.tde.motorSALTIBUS.persistence.origin.repo.DescargasCARDRepository;
import com.tde.motorSALTIBUS.persistence.destination.entity.DescargasCARDSaltibus;
import com.tde.motorSALTIBUS.persistence.destination.repo.DescargasCARDSaltibusRepository;

@Service
public class CARDTransferenciaService {
    private static final int BATCH_SIZE = 500; // ajusta a tu carga/ventana

    @Autowired
    private DescargasCARDRepository descargasCardRepoO;

    @Autowired
    private DescargasCARDSaltibusRepository descargasCardRepoD;

    @Transactional
    public void transferirDatos() {
        Long lastId = descargasCardRepoD.findTopByOrderByIdDGprsDesc()
                .map(DescargasCARDSaltibus::getIdDGprs)
                .orElse(0L);

        System.out.println("CARD - LAST ID DESTINO: " + lastId);

        List<DescargasCARD> origen = leerLoteOrigen(lastId);

        System.out.println("CARD - REGISTROS ENCONTRADOS: " + origen.size());

        if (origen.isEmpty()) {
            System.out.println("CARD - Sin registros nuevos para transferir.");
            System.out.println("CARD - FIN DEL PROCESO");
            return;
        }

        List<DescargasCARDSaltibus> destino = new ArrayList<>(origen.size());

        for (DescargasCARD o : origen) {
            destino.add(convertirADestino(o));
        }

        long insertados = 0;
        long duplicados = 0;
        long fallidos = 0;

        try {
            descargasCardRepoD.saveAll(destino);
            insertados = destino.size();
        } catch (DataIntegrityViolationException bulkEx) {
            System.err.println("CARD - saveAll falló (posibles duplicados). Fallback a inserción individual. Detalle: "
                    + bulkEx.getMostSpecificCause().getMessage());
            for (DescargasCARDSaltibus d : destino) {
                try {
                    descargasCardRepoD.save(d);
                    insertados++;
                } catch (DataIntegrityViolationException dup) {
                    duplicados++;
                } catch (Exception e) {
                    fallidos++;
                    System.err.println("CARD - Error insertando idDGprs=" + d.getIdDGprs() + ". Detalle: " + e.getMessage());
                }
            }
        }

        Long maxIdLote = obtenerMaxId(origen).orElse(lastId);

        System.out.println("CARD - Leídos: " + origen.size()
                + " | Insertados: " + insertados
                + " | Duplicados: " + duplicados
                + " | Fallidos: " + fallidos
                + " | MaxIdLote: " + maxIdLote);
        System.out.println("CARD - FIN DEL PROCESO");
    }

    private List<DescargasCARD> leerLoteOrigen(Long lastId) {
        Pageable page = PageRequest.of(0, BATCH_SIZE, Sort.by(Sort.Direction.ASC, "idDGprs"));
        return descargasCardRepoO.findByIdDGprsGreaterThan(lastId);
    }

    private Optional<Long> obtenerMaxId(List<DescargasCARD> registros) {
        Long max = null;
        for (DescargasCARD r : registros) {
            if (r.getIdDGprs() == null) {
                continue;
            }
            if (max == null || r.getIdDGprs() > max) {
                max = r.getIdDGprs();
            }
        }
        return Optional.ofNullable(max);
    }

    private DescargasCARDSaltibus convertirADestino(DescargasCARD origen) {
        return DescargasCARDSaltibus.builder()
                .idDGprs(origen.getIdDGprs())
                .strValidador(origen.getStrValidador())
                .strIDTarjeta(origen.getStrIDTarjeta())
                .intTipoTarjeta(origen.getIntTipoTarjeta())
                .intTipoEventoTarjeta(origen.getIntTipoEventoTarjeta())
                .intSaldoInicialTarjeta(origen.getIntSaldoInicialTarjeta())
                .intSaldoFinalTarjeta(origen.getIntSaldoFinalTarjeta())
                .fechaEventoTarjeta(origen.getFechaEventoTarjeta())
                .intVarControl(origen.getIntVarControl())
                .intStatus(origen.getIntStatus())
                .fechaAvl(origen.getFechaAvl())
                .intNumOperador(origen.getIntNumOperador())
                .intIDPuntoVenta(origen.getIntIDPuntoVenta())
                .intFolioVenta(origen.getIntFolioVenta())
                .intFolioTarjeta(origen.getIntFolioTarjeta())
                .strModemId(origen.getStrModemId())
                .build();
    }
}