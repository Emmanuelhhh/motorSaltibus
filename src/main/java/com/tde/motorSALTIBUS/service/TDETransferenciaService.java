package com.tde.motorSALTIBUS.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tde.motorSALTIBUS.persistence.origin.entity.DescargasTDE;
import com.tde.motorSALTIBUS.persistence.origin.repo.DescargasTDERepository;
import com.tde.motorSALTIBUS.persistence.destination.entity.DescargasTDESaltibus;
import com.tde.motorSALTIBUS.persistence.destination.repo.DescargasTDESaltibusRepository;

@Service
public class TDETransferenciaService {
    private static final int BATCH_SIZE = 500; // ajusta a tu carga/ventana

    @Autowired
    private DescargasTDERepository tdeRepoO;

    @Autowired
    private DescargasTDESaltibusRepository tdeRepoD;

    @Transactional
    public void transferirDatos() {
        Long lastId = tdeRepoD.findTopByOrderByIdDGprsDesc()
                .map(DescargasTDESaltibus::getIdDGprs)
                .orElse(0L);

        System.out.println("TDE - LAST ID DESTINO: " + lastId);

        List<DescargasTDE> origen = leerLoteOrigen(lastId);

        System.out.println("TDE - REGISTROS ENCONTRADOS: " + origen.size());

        if (origen.isEmpty()) {
            System.out.println("TDE - Sin registros nuevos para transferir.");
            System.out.println("TDE - FIN DEL PROCESO");
            return;
        }

        Date now = new Date();
        List<DescargasTDESaltibus> destino = new ArrayList<>(origen.size());

        for (DescargasTDE o : origen) {
            destino.add(convertirADestino(o, now));
        }

        long insertados = 0;
        long duplicados = 0;
        long fallidos = 0;

        try {
            tdeRepoD.saveAll(destino);
            insertados = destino.size();
        } catch (DataIntegrityViolationException bulkEx) {
            System.err.println("TDE - saveAll falló (posibles duplicados). Fallback a inserción individual. Detalle: "
                    + bulkEx.getMostSpecificCause().getMessage());

            for (DescargasTDESaltibus d : destino) {
                try {
                    tdeRepoD.save(d);
                    insertados++;
                } catch (DataIntegrityViolationException dup) {
                    duplicados++;
                } catch (Exception e) {
                    fallidos++;
                    System.err.println("TDE - Error insertando idDGprs=" + d.getIdDGprs() + ". Detalle: " + e.getMessage());
                }
            }
        }

        Long maxIdLote = obtenerMaxId(origen).orElse(lastId);

        System.out.println("TDE - Leídos: " + origen.size()
                + " | Insertados: " + insertados
                + " | Duplicados: " + duplicados
                + " | Fallidos: " + fallidos
                + " | MaxIdLote: " + maxIdLote);
        System.out.println("TDE - FIN DEL PROCESO");
    }

    private List<DescargasTDE> leerLoteOrigen(Long lastId) {
        Pageable page = PageRequest.of(0, BATCH_SIZE, Sort.by(Sort.Direction.ASC, "idDGprs"));
        return tdeRepoO.findByIdDGprsGreaterThan(lastId);
    }

    private Optional<Long> obtenerMaxId(List<DescargasTDE> registros) {
        Long max = null;
        for (DescargasTDE r : registros) {
            if (r.getIdDGprs() == null) {
                continue;
            }
            if (max == null || r.getIdDGprs() > max) {
                max = r.getIdDGprs();
            }
        }
        return Optional.ofNullable(max);
    }

    private DescargasTDESaltibus convertirADestino(DescargasTDE o, Date fechaInsert) {
        Integer varControl = null;
        if (o.getIntVarControl() != null) {
            varControl = o.getIntVarControl() ? 1 : 0;
        }

        return DescargasTDESaltibus.builder()
                .idDGprs(o.getIdDGprs())
                .intTipoFrame(o.getIntTipoFrame())
                .intSubidasPta1(o.getIntSubidasPta1())
                .intBajadasPta1(o.getIntBajadasPta1())
                .intBloqueosPta1(o.getIntBloqueosPta1())
                .intPablosPta1(o.getIntPablosPta1())
                .intSubidasPta2(o.getIntSubidasPta2())
                .intBajadasPta2(o.getIntBajadasPta2())
                .intBloqueosPta2(o.getIntBloqueosPta2())
                .intPablosPta2(o.getIntPablosPta2())
                .intNumOperador(o.getIntNumOperador())
                .intVarControl(varControl)
                .intNumApagadosPta1(o.getIntNumApagadosPta1())
                .intNumApagadosPta2(o.getIntNumApagadosPta2())
                .modemId(o.getModemId())
                .intStatus(0)
                .fechaHoraInsert(fechaInsert)
                .build();
    }
}