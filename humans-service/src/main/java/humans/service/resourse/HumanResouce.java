package humans.service.resourse;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import humans.service.service.HumanService;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import soa.models.DTO.HumanDTO;
import soa.models.enums.WeaponType;

@Component
@Path("/humans")
public class HumanResouce {

    @Autowired
    HumanService humanService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(HumanDTO dto) {
        return Response.ok().entity(humanService.save(dto)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readSome(
            @QueryParam("from") Integer from,
            @QueryParam("page-size") Integer pageSize,
            @QueryParam("filter") String filter,
            @QueryParam("sort-by") String sortBy) {
        return Response.ok().entity(new ArrayList<>()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readOne(@PathParam("id") Integer id) {
        return Response.ok().entity(new HumanDTO()).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id) {
        return Response.ok().entity(new HumanDTO()).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        return Response.ok().entity(new HumanDTO()).build();
    }

    @DELETE
    @Path("/by-weapon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByWeapon(@QueryParam("weapon-type") WeaponType type) {
        return Response.ok().entity(new HumanDTO()).build();
    }

    @DELETE
    @Path("/less-cool-car")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByWeaponAll(@QueryParam("max-coolness") Integer coolness) {
        return Response.ok().entity(new HumanDTO()).build();
    }
}
