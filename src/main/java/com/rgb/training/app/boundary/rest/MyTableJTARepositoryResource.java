package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.common.rest.RestServiceLog;
import com.rgb.training.app.common.rest.SecurityKeyAuth;
import com.rgb.training.app.data.model.MyTable;
import com.rgb.training.app.data.repository.MyTableJTARepository;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Client: http://localhost:8081/training-app/api/jta/mytable/
 *
 * @author LuisCarlosGonzalez
 */
@Path("jta/mytable")
@Produces(MediaType.APPLICATION_JSON)
@SecurityKeyAuth
@RestServiceLog
public class MyTableJTARepositoryResource {

    @Inject
    private MyTableJTARepository myTableRepo;

    @HEAD
    public Response headDefault() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SecurityKeyAuth  
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
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(MyTable newEntry) {
        MyTable result = myTableRepo.create(newEntry);
        if (result != null) {
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("No s'ha pogut crear el registre.")
                        .build();
    }

    @PUT    
    @Path("/{id}")
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
