package com.tde.motorSALTIBUS.service.scheduled.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.tde.motorSALTIBUS.service.AVLTransferenciaService;
//import com.tde.motorSALTUBUS.service.PagabusTransferenciaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class TransferenciaJob {

    @Autowired
    private AVLTransferenciaService avltransferenciaService;
    
   // @Autowired 
   // private PagabusTransferenciaService pagabusTransferenciaService;
   
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    
    private static final Logger log = LoggerFactory.getLogger(TransferenciaJob.class);

    @Scheduled(cron = "0 * * * * ?")
    public void ejecutarTransferencia() {
        log.info("Inicia proceso de transferencia de datos");

        scheduledExecutorService.schedule(() -> executeSafely(() -> avltransferenciaService.transferirDatos()), 0, TimeUnit.SECONDS);
       // scheduledExecutorService.schedule(() -> executeSafely(() -> pagabusTransferenciaService.transferirDatos()), 30, TimeUnit.SECONDS);
       // scheduledExecutorService.schedule(() -> executeSafely(() -> cardTransferenciaService.tranferirDatos(7)), 60, TimeUnit.SECONDS);
       // scheduledExecutorService.schedule(() -> executeSafely(() -> eventoMiniSigoTransferenciaService.transferirDatos(7)), 90, TimeUnit.SECONDS);
       
        
        
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