package com.hengyi.japp.print.server.rest.resource;

import com.hengyi.japp.print.server.dto.MdUpdateDTO;
import com.hengyi.japp.print.server.query.MdQuery;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.hengyi.japp.print.server.MyService.operatorService;

/**
 * Created by Administrator on 2016/2/28.
 */
@Path("mds")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MdResource {
    @POST
    public Response create(@Valid @NotNull MdUpdateDTO dto) throws Exception {
        return Response.ok(operatorService.create(dto)).build();
    }

    @Path("{mdId}")
    @PUT
    public Response update(@Valid @NotBlank @PathParam("mdId") String mdId,
                           @Valid @NotNull MdUpdateDTO dto) throws Exception {
        return Response.ok(operatorService.update(mdId, dto)).build();
    }

    @Path("{mdId}")
    @GET
    public Response get(@Valid @NotBlank @PathParam("mdId") String mdId) throws Exception {
        return Response.ok(operatorService.getMd(mdId)).build();
    }

    @GET
    public Response get(@Valid @NotBlank @QueryParam("bukrs") String bukrs,
                        @QueryParam("q") String q,
                        @QueryParam("first") @DefaultValue("0") int first,
                        @QueryParam("pageSize") @DefaultValue("10") int pageSize) throws Exception {
        MdQuery query = new MdQuery(bukrs);
        return Response.ok(query.q(q).first(first).pageSize(pageSize).result()).build();
    }
}
