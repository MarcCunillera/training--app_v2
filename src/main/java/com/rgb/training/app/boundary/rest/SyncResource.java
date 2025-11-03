package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.controller.mytable.Odoo2JavaSyncController;
import com.rgb.training.app.controller.mytable.Java2OdooSyncController;
import jakarta.ejb.EJB;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Exposición REST para sincronización con Odoo
 */
//Link: http://localhost:8080/training-app/api/sync/odoo2Java
//Link: http://localhost:8080/training-app/api/sync/java2Odoo

@Path("/sync")
@Produces(MediaType.APPLICATION_JSON)
public class SyncResource {

    
    @EJB
    private Odoo2JavaSyncController odoo2JavaCtrl;
    
    
    @EJB
    private Java2OdooSyncController java2OdooCtrl;

    // Sincronitzacio de Odoo → Java (base de datos local)
    @POST
    @Path("/odoo2Java")
    public Response odoo2Java() {
        try {
            odoo2JavaCtrl.sync();
            return Response.ok()
                    .entity("{\"status\":\"ok\",\"message\":\"Sincronización Odoo → Java completada\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Sincronitzacio de Java → Odoo
    @POST
    @Path("/java2Odoo")
    public Response java2Odoo() {
        try {
            java2OdooCtrl.sync();
            return Response.ok()
                    .entity("{\"status\":\"ok\",\"message\":\"Sincronización Java → Odoo completada\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
