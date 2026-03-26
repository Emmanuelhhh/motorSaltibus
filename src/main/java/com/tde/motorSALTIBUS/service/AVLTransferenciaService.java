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

import com.tde.motorSALTIBUS.persistence.origin.entity.*;
import com.tde.motorSALTIBUS.persistence.origin.repo.*;
import com.tde.motorSALTIBUS.persistence.destination.entity.*;
import com.tde.motorSALTIBUS.persistence.destination.repo.*;

@Service
public class AVLTransferenciaService {
    private static final int BATCH_SIZE = 500; // ajusta a tu carga/ventana

    @Autowired
    private DescargasAVLRepository descargasAvlRepoO;
    
    @Autowired
    private DescargasAVLSaltibusRepository descargasAvlRepoD;
    
    @Transactional
    public void transferirDatos() {
    	
    	Long lastId = descargasAvlRepoD.findTopByOrderByIdDesc()
                .map(DescargasAVLSaltibus::getId)
                .orElse(0L);
    	System.out.println("SALTIBUS AVL - LAST ID DESTINO: " + lastId);
        
        List<DescargasAVL> origen = leerLoteOrigen(lastId);
    	  
        System.out.println("REGISTROS SALTIBUS AVL ENCONTRADOS " + origen.size());
        
        if (origen.isEmpty()) {
            System.out.println("SALTIBUS AVL - Sin registros nuevos para transferir.");
            System.out.println("SALTIBUS FIN DEL PROCESO AVL");
            return;
        }
        
        List<DescargasAVLSaltibus> destino = new ArrayList<>(origen.size());  
        
        for (DescargasAVL o : origen) {
            destino.add(convertirADestino(o));
        }
        
        long insertados = 0;
        long duplicados = 0;
        long fallidos = 0;
        
        try {
            descargasAvlRepoD.saveAll(destino);
            insertados = destino.size();
        } catch (DataIntegrityViolationException bulkEx) {
            System.err.println("SALTIBUS AVL - saveAll falló (posibles duplicados). Fallback a inserción individual. Detalle: "
                    + bulkEx.getMostSpecificCause().getMessage());
            for (DescargasAVLSaltibus d : destino) {
                try {
                    descargasAvlRepoD.save(d);
                    insertados++;
                } catch (DataIntegrityViolationException dup) {
                    duplicados++;
                } catch (Exception e) {
                    fallidos++;
                    System.err.println("SALTIBUS AVL - Error insertando id=" + d.getId() + ". Detalle: " + e.getMessage());
                }
            }
        }
        
        Long maxIdLote = obtenerMaxId(origen).orElse(lastId);

        System.out.println("SALTIBUS AVL - Leídos: " + origen.size()
                + " | Insertados: " + insertados
                + " | Duplicados: " + duplicados
                + " | Fallidos: " + fallidos
                + " | MaxIdLote: " + maxIdLote);
        System.out.println("SALTIBUS FIN DEL PROCESO AVL");
    }

    private List<DescargasAVL> leerLoteOrigen(Long lastId) {
        Pageable page = PageRequest.of(0, BATCH_SIZE, Sort.by(Sort.Direction.ASC, "id"));
        // Se usa funcion temporal 
        return descargasAvlRepoO.findByIdGreaterThan(lastId);
       // return descargasAvlRepoO.findByIdGreaterThan(lastId, page);

    }

    private Optional<Long> obtenerMaxId(List<DescargasAVL> registros) {
        Long max = null;
        for (DescargasAVL r : registros) {
            if (r.getId() == null) {
                continue;
            }
            if (max == null || r.getId() > max) {
                max = r.getId();
            }
        }
        return Optional.ofNullable(max);
    }

    private DescargasAVLSaltibus convertirADestino(DescargasAVL origen) {
        DescargasAVLSaltibus destino = new DescargasAVLSaltibus();

        destino.setId(origen.getId());
        destino.setIntTipoAVL(origen.getIntTipoAVL());
        destino.setStrModemID(origen.getStrModemID());
        destino.setFLongitudGrad(origen.getFLongitudGrad());
        destino.setFLatitudGrad(origen.getFLatitudGrad());
        destino.setIntVelocidad(origen.getIntVelocidad());
        destino.setIntNumSat(origen.getIntNumSat());
        destino.setFechaHoraSat(origen.getFechaHoraSat());
        destino.setIntTipoEvento(origen.getIntTipoEvento());
        destino.setIntVariable1(origen.getIntVariable1());
        destino.setIntVarControl(origen.getIntVarControl());

        return destino;
    }
}