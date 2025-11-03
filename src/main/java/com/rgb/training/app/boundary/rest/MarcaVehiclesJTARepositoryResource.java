package com.rgb.training.app.boundary.rest;

import com.rgb.training.app.data.model.MarcaVehicle;
import com.rgb.training.app.data.repository.MarcaVehicleJTARepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Client: http://localhost:8081/training-app/api/jta/marcavehicle/
 *
 * @author MarcCunilleraCases
 */
@Path("jta/marcavehicle")
@Produces(MediaType.APPLICATION_JSON)
public class MarcaVehiclesJTARepositoryResource {

    @Inject
    private MarcaVehicleJTARepository marcaRepo;

    @HEAD
    public Response headDefault() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        MarcaVehicle result = marcaRepo.get(id);
        return Response.ok(result).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam(value = "offset") Integer offset,
            @QueryParam(value = "max-results") Integer maxResults) {
        List<MarcaVehicle> results = marcaRepo.getAll(offset, maxResults);
        return Response.ok(results).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(MarcaVehicle newEntry) {
        MarcaVehicle result = marcaRepo.create(newEntry);
        if (result != null) {
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        return Response.notModified().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(MarcaVehicle updatedEntry) {
        if (marcaRepo.get(updatedEntry.getId()) != null) {
            MarcaVehicle result = marcaRepo.update(updatedEntry);
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
        Integer deleted = marcaRepo.delete(id);
        if (deleted > 0) {
            return Response.ok(id).build();
        }
        return Response.notModified().build();
    }
}
