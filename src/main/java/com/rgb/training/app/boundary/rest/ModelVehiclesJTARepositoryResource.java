package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.common.rest.RestServiceLog;
import com.rgb.training.app.common.rest.SecurityKeyAuth;
import com.rgb.training.app.data.model.ModelVehicles;
import com.rgb.training.app.data.repository.ModelVehicleJTARepository;
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
 * REST Client: http://localhost:8081/training-app/api/jta/modelvehicle/
 *
 * @author MarcCunilleraCases
 */
@Path("jta/modelvehicles")
@Produces(MediaType.APPLICATION_JSON)
//@SecurityKeyAuth
//@RestServiceLog
public class ModelVehiclesJTARepositoryResource {

    @Inject
    private ModelVehicleJTARepository ModelVehiclesRepo;

    @HEAD
    public Response headDefault() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        ModelVehicles result = ModelVehiclesRepo.get(id);
        return Response.ok(result).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam(value = "offset") Integer offset, @QueryParam(value = "max-results") Integer maxResults) {
        List<ModelVehicles> results = ModelVehiclesRepo.getAll(offset, maxResults);
        return Response.ok(results).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(ModelVehicles newEntry) {
        ModelVehicles result = (ModelVehicles) ModelVehiclesRepo.create(newEntry);
        if (result != null) {
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("No s'ha pogut crear el registre.")
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ModelVehicles updatedEntry) {
        if (ModelVehiclesRepo.get(updatedEntry.getId()) != null) {
            ModelVehicles result = ModelVehiclesRepo.update(updatedEntry);
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
        Integer deleted = ModelVehiclesRepo.delete(id);
        if (deleted > 0) {
            return Response.ok(id).build();
        }
        return Response.notModified().build();
    }
}
