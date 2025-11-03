package com.rgb.training.app.common.rest;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/**
 *
 * @author LuisCarlosGonzalez
 */
@SecurityKeyAuth
@Provider
@Priority(value = 150)
public class SecurityKeyAuthorizer implements ContainerRequestFilter {

    public static final String AUTHENTICATION_HEADER = "Authorization";
    public static final String AUTH_KEY;

    static {
        // Generar un token aleatorio seguro al iniciar la aplicación
        AUTH_KEY = java.util.UUID.randomUUID().toString();
        System.out.println("🔐 AUTH_KEY generado dinámicamente: " + AUTH_KEY);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Boolean authorized = Boolean.FALSE;
        String authToken = requestContext.getHeaderString(AUTHENTICATION_HEADER);

        if (requestContext.getRequest().getMethod().equals("OPTIONS") || requestContext.getRequest().getMethod().equals("HEAD")) {
            requestContext.abortWith(Response.status(Response.Status.OK).build());
            return;
        }

        if (authToken != null) {
            authorized = AUTH_KEY.equals(authToken);
        }

        if (!authorized) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
