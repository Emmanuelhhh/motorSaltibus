package com.tde.motorSALTIBUS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tde.motorSALTIBUS.persistence.origin.entity.*;
import com.tde.motorSALTIBUS.persistence.origin.repo.*;
import com.tde.motorSALTIBUS.persistence.destination.entity.*;
import com.tde.motorSALTIBUS.persistence.destination.repo.*;



@Service
public class AVLTransferenciaService {
	
    @Autowired
    private DescargasAVLRepository descargasAvlRepoO;
    
    @Autowired
    private DescargasAVLSaltibusRepository descargasAvlRepoD;
    
    @Transactional
    public void transferirDatos() {
    	
    	Long lastId = descargasAvlRepoD.findTopByOrderByIdDesc()
                .map(DescargasAVLSaltibus::getId)
                .orElse(0L);
    	System.out.println("ID MAS ALTO "+ lastId); 
        // Convertimos el Iterable a una lista para poder manipularlo
        List<DescargasAVL> registros = new ArrayList<>();
        //borrador. se esta poniento un top de 1 para fines de prueba
        descargasAvlRepoO.findTop1ByIdGreaterThanOrderByIdAsc(lastId).forEach(registros::add);

        System.out.println("REGISTROS AVL SALTIBUS  " + "imprimir registros");

        // Lista para almacenar los registros que fallaron
        List<DescargasAVL> registrosFallidos = new ArrayList<>();

        for (DescargasAVL registroO : registros) {
            try {
                DescargasAVLSaltibus registroD = convertirADestino(registroO);
                descargasAvlRepoD.save(registroD); // Guardar registro individualmente
            } catch (Exception e) {
                registrosFallidos.add(registroO); // Almacenar los fallidos
                System.err.println("Error al transferir registro: " + registroO.getStrModemID() + ", " + e.getMessage());
            }
        }

        // Elimina solo los registros que no fallaron
       // registros.removeAll(registrosFallidos);
       // descargasAvlRepoO.deleteAll(registros);
        //no se van a eliminar registro para este caso 
        // se va a usar el mecanismo de buscar el idgprs y id avl 

        System.out.println("FIN DEL PROCESO");
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
    destino.setFechaHoraComputadora(origen.getFechaHoraComputadora());
    destino.setIntVarControl(origen.getIntVarControl());

    // Campos adicionales en DescargasAvlD que no están en DescargasAvlO
    // Puedes inicializar `avl` como null o asignar un valor predeterminado
  //  destino.setAvl(null);  // O asigna un objeto `Avl` según tu lógica.

    return destino;
}
}
