package com.tde.motorSALTIBUS.service.scheduled.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.tde.motorSALTIBUS.service.AVLTransferenciaService;
import com.tde.motorSALTIBUS.service.CARDTransferenciaService;
import com.tde.motorSALTIBUS.service.TDETransferenciaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class TransferenciaJob {

    @Autowired
    private AVLTransferenciaService avltransferenciaService;
    
   @Autowired 
   private CARDTransferenciaService cardTtrasferenciaService;
   
   @Autowired
   private TDETransferenciaService tdeTranferenciaService;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    
    private static final Logger log = LoggerFactory.getLogger(TransferenciaJob.class);

    @Scheduled(cron = "0 * * * * ?")
    public void ejecutarTransferencia() {
        log.info("Inicia proceso de transferencia de datos");
//CAMBIA CAMBIO TEMPORAL DE INICIO DE 0 A 60 SEGUNDOS
        scheduledExecutorService.schedule(() -> executeSafely(() -> avltransferenciaService.transferirDatos()), 0, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> executeSafely(() -> cardTtrasferenciaService.transferirDatos()), 30, TimeUnit.SECONDS);
        scheduledExecutorService.schedule(() -> executeSafely(() -> tdeTranferenciaService.transferirDatos()), 60, TimeUnit.SECONDS);
       
        
        
        log.info("Tareas programadas");
    }

    private synchronized void executeSafely(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            log.error("Error ejecutando tarea", e);
        }
    }
}