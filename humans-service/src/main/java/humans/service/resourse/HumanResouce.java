package humans.service.resourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import humans.service.service.HumanService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import soa.models.DTO.HumanDTO;
import soa.models.enums.WeaponType;
import soa.models.exception.IncorrectPageSizeException;

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
            @QueryParam("sort-by") String sortBy) throws Exception {
        if (pageSize > 20)
            throw new IncorrectPageSizeException();
        return Response.ok().entity(humanService.getSome(from, pageSize, filter, sortBy)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readOne(@PathParam("id") Long id) {
        return Response.ok().entity(humanService.get(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, HumanDTO dto) {
        return Response.ok().entity(humanService.update(id, dto)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        return Response.ok().entity(humanService.delete(id)).build();
    }

    @DELETE
    @Path("/by-weapon")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByWeapon(@QueryParam("weapon-type") WeaponType type) {
        return Response.ok().entity(humanService.deleteByWeaponType(type)).build();
    }

    @DELETE
    @Path("/by-weapon-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByWeaponAll(@QueryParam("weapon-type") WeaponType type) {
        humanService.deleteAllByWeaponType(type);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/less-cool-car")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteByLessCoolCar(@QueryParam("max-coolness") Integer coolness) {
        humanService.deleteAllByLessCoolCar(coolness);
        return Response.noContent().build();
    }
}
