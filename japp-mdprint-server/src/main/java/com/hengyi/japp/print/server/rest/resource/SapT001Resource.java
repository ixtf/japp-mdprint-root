package com.hengyi.japp.print.server.rest.resource;

import com.hengyi.japp.print.server.query.SapMaraQuery;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.hengyi.japp.print.server.MyService.operatorService;

/**
 * Created by Administrator on 2016/2/28.
 */
@Path("sapT001s")
public class SapT001Resource {
    @GET
    public Response list() throws Exception {
        return Response.ok(operatorService.listSapT001()).build();
    }

    @Path("{bukrs}/sapYmmbancis")
    @GET
    public Response listSapYmmbancis(@Valid @NotBlank @PathParam("bukrs") String bukrs) throws Exception {
        return Response.ok(operatorService.listSapYmmbancis(bukrs)).build();
    }

    @Path("{bukrs}/sapZpackages")
    @GET
    public Response listSapZpackages(@Valid @NotBlank @PathParam("bukrs") String bukrs) throws Exception {
        return Response.ok(operatorService.listSapZpackages(bukrs)).build();
    }

    @Path("{bukrs}/sapMaras")
    @GET
    public Response listSapMara(@Valid @NotBlank @PathParam("bukrs") String bukrs,
                                @QueryParam("q") String q,
                                @QueryParam("first") @DefaultValue("0") int first,
                                @QueryParam("pageSize") @DefaultValue("50") int pageSize) throws Exception {
        SapMaraQuery query = new SapMaraQuery(bukrs);
        return Response.ok(query.q(q).first(first).pageSize(pageSize).result()).build();
    }
}
