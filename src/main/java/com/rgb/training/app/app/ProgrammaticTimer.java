package com.rgb.training.app.app;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Timeout;
import jakarta.ejb.TimerService;
import java.time.LocalDateTime;

/**
 *
 * @author luiscarlosgonzalez
 */
//@Singleton
//@Startup
public class ProgrammaticTimer {

    @Resource
    TimerService timerService;

    public ProgrammaticTimer() {
        System.out.println("============ Programmatic timer initialized =============");
    }

    @PostConstruct
    public void createTimer() {
        // buscar parametro de configuracion dinamico
        // proceso 1
        long interval1 = 5 * 1000; //fichero-configuracion.properties proceso1.timer.interval=5 seg
        timerService.createTimer(0, interval1, "Timer - proceso1 - " + System.currentTimeMillis());
        
        //proceso2
        long interval2 = 10 * 1000; //fichero-configuracion.properties proceso2.timer.interval=10 seg
        timerService.createTimer(0, interval2, "Timer - proceso2 - " + System.currentTimeMillis());
    }

    @Timeout
    public void timeout() {
        System.out.println("Cada 5 segundos:" + LocalDateTime.now());
    }
}
