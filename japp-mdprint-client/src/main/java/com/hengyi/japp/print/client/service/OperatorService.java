package com.hengyi.japp.print.client.service;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hengyi.japp.print.client.domain.Md;
import com.hengyi.japp.print.client.domain.SapT001;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.hengyi.japp.print.client.Constant.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * Created by jzb on 16-3-3.
 */
public class OperatorService {
    public OperatorService() {
        eventBus.register(this);
    }

    public List<SapT001> listSapT001() throws Exception {
        String json = destination.path("sapT001s").request(APPLICATION_JSON_TYPE).get(String.class);
        return objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, SapT001.class));
    }

    public void handleLogin(SapT001 sapT001) throws Exception {
        Response res = destination.path("auth/login").queryParam("bukrs", sapT001.getBukrs()).request().post(null);
        res.close();
        if (200 != res.getStatus())
            throw new Exception();
        sapT001.fetchSapYmmbancis();
        sapT001.fetchSapZpackages();
    }

    public void save(Md md) {
    }

    public void printMd(Md md) {
        if (StringUtils.isBlank(md.getCharg()))
            save(md);
    }

    public void printXd(Md md) {
        if (StringUtils.isBlank(md.getCharg()))
            save(md);

    }
}
