package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.common.rest.RestServiceLog;
import com.rgb.training.app.data.model.MyTable;
import com.rgb.training.app.data.repository.MyTableRLRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Client: http://localhost:8081/training-app/api/resource-local/mytable/
 *
 * @author LuisCarlosGonzalez
 */
@Path("resource-local/mytable")
@Produces(MediaType.APPLICATION_JSON)
//@SecurityKeyAuth
@RestServiceLog
public class MyTableRLRepositoryResource {

    @Inject
    private MyTableRLRepository myTableRepo;

    @HEAD
    public Response headDefault() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        MyTable result = myTableRepo.get(id);
        return Response.ok(result).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam(value = "offset") Integer offset, @QueryParam(value = "max-results") Integer maxResults) {
        List<MyTable> results = myTableRepo.getAll(offset, maxResults);
        return Response.ok(results).build();
    }

    @GET
    @Path("secured/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getWithSecurity(@Context HttpHeaders httpHeaders, @PathParam("id") Long id) {
        boolean authorized = false;
        List<String> authHeaders = httpHeaders.getRequestHeader("Authorization");
        if (authHeaders == null || authHeaders.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        for (String auth : authHeaders) {
            if (auth.equals("1234")) {
                authorized = true;
            }
        }
        if (!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        MyTable result = myTableRepo.get(id);
        return Response.ok(result).build();
    }

    @GET
    @Path("secured")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllWithSecurity(@Context HttpHeaders httpHeaders) {
        boolean authorized = false;
        List<String> authHeaders = httpHeaders.getRequestHeader("Authorization");
        if (authHeaders == null || authHeaders.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        for (String auth : authHeaders) {
            if (auth.equals("1234")) {
                authorized = true;
            }
        }
        if (!authorized) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        List<MyTable> results = myTableRepo.getAll();
        return Response.ok(results).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(MyTable newEntry) {
        MyTable result = myTableRepo.create(newEntry);
        if (result != null) {
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        return Response.notModified().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(MyTable updatedEntry) {
        if (myTableRepo.get(updatedEntry.getId()) != null) {
            MyTable result = myTableRepo.update(updatedEntry);
            if (result != null) {
                return Response.ok(result).build();
            }
        }
        return Response.notModified().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        Long deleted = myTableRepo.delete(id);
        if (deleted > 0) {
            return Response.ok(id).build();
        }
        return Response.notModified().build();
    }
}
