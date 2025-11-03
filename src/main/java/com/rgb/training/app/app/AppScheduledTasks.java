package com.rgb.training.app.app;

import com.rgb.training.app.config.CustomConfig;
import com.rgb.training.app.controller.mytable.Java2OdooSyncController;
import com.rgb.training.app.controller.mytable.Odoo2JavaSyncController;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Schedules;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.time.LocalDateTime;

/**
 * Scheduler para sincronización automática bidireccional.
 */

@Startup
@Singleton
public class AppScheduledTasks {

    @EJB
    private Java2OdooSyncController java2odooSync;

    @EJB
    private Odoo2JavaSyncController odoo2JavaSync;
    
    /**
     * Sincronización automática cada 30 segons.
     * Primero Java → Odoo, luego Odoo → Java.
     */

    @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
    public void automaticTimer() {
        System.out.println("[SYNC] Inicio de sincronización: " + LocalDateTime.now());
        try {
            System.out.println("[SYNC] Java → Odoo...");
            java2odooSync.sync();
            System.out.println("[SYNC] Java → Odoo completado.\n");
            
            System.out.println("[SYNC] Odoo → Java...");
            odoo2JavaSync.sync();
            System.out.println("[SYNC] Odoo → Java completado.\n");

            System.out.println("[SYNC] Sincronización completa: " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("[SYNC] Error durante la sincronización: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Schedules({
        @Schedule(hour = "12"),
        @Schedule(hour = "20")
    })
    public void multipleSchedule() {
        System.out.println("[SYNC] Sincronización programada en horario fijo: " + LocalDateTime.now());
        automaticTimer(); // reutiliza la sincronización completa
    }
}