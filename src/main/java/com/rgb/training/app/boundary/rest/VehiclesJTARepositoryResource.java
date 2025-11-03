package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.common.rest.RestServiceLog;
import com.rgb.training.app.common.rest.SecurityKeyAuth;
import com.rgb.training.app.data.model.Vehicles;
import com.rgb.training.app.data.repository.VehiclesJTARepository;
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
 * REST Client: http://localhost:8081/training-app/api/jta/vehicles/
 *
 * @author LuisCarlosGonzalez
 */
@Path("jta/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@SecurityKeyAuth
@RestServiceLog
public class VehiclesJTARepositoryResource {

    @Inject
    private VehiclesJTARepository VehiclesRepo;

    @HEAD
    public Response headDefault() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SecurityKeyAuth
    public Response get(@PathParam("id") Integer id) {
        Vehicles result = VehiclesRepo.get(id);
        return Response.ok(result).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam(value = "offset") Integer offset, @QueryParam(value = "max-results") Integer maxResults) {
        List<Vehicles> results = VehiclesRepo.getAll(offset, maxResults);
        return Response.ok(results).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Vehicles newEntry) {
        Vehicles result = VehiclesRepo.create(newEntry);
        if (result != null) {
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        return Response.notModified().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Vehicles updatedEntry) {
        if (VehiclesRepo.get(updatedEntry.getId()) != null) {
            Vehicles result = VehiclesRepo.update(updatedEntry);
            if (result != null) {
                return Response.ok(result).build();
            }
        }
        return Response.notModified().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        Integer deleted = VehiclesRepo.delete(id);
        if (deleted > 0) {
            return Response.ok(id).build();
        }
        return Response.notModified().build();
    }
}
