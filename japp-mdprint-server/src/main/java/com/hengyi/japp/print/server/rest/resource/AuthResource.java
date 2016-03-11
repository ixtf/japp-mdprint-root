package com.hengyi.japp.print.server.rest.resource;

import com.hengyi.japp.print.server.MyService;
import com.hengyi.japp.print.server.event.SyncSapDataEvent;
import com.hengyi.japp.print.server.event.UnSyncSapDataEvent;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2016/2/28.
 */
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Path("login")
    @POST
    public Response login(@Valid @NotBlank @QueryParam("bukrs") String bukrs) throws Exception {
//        MyService.sapService.syncData(bukrs);
        MyService.eventBus.post(new SyncSapDataEvent(bukrs));
        return Response.ok().build();
    }

    @Path("logout")
    @DELETE
    public Response logout(@Valid @NotBlank @QueryParam("bukrs") String bukrs) throws Exception {
        MyService.eventBus.post(new UnSyncSapDataEvent(bukrs));
        return Response.ok().build();
    }
}
