package com.hengyi.japp.print.client.service;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hengyi.japp.print.client.domain.Md;
import com.hengyi.japp.print.client.domain.SapMara;
import com.hengyi.japp.print.client.domain.SapT001;
import com.hengyi.japp.print.client.domain.Xd;
import javafx.beans.binding.ObjectBinding;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

import static com.hengyi.japp.print.client.Constant.*;
import static com.hengyi.japp.print.client.MainApp.sapT001;
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
    }

    public List<SapMara> autoCompleteSapMara(String q) throws Exception {
        String json = destination.path("sapT001s").path(sapT001.getBukrs()).path("sapMaras").queryParam("q", q).request(APPLICATION_JSON_TYPE).get(String.class);
        return objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, SapT001.class));
    }

    public void autoXd(Md md) {
        //TODO bind
        md.getXds().forEach(xd -> {
            if (md.getZdzflg()) {
                xd.zsnwghtProperty().bind(md.zcnwghtProperty());
                xd.zsgwghtProperty().bind(new ObjectBinding<BigDecimal>() {
                    {
                        bind(xd.zsnwghtProperty(), xd.zrolmgeProperty(), md.sapYmmzhixProperty(), md.sapYmmtonggProperty());
                    }

                    @Override
                    protected BigDecimal computeValue() {
                        return xd.zsnwghtProperty().get().add(md.sapYmmzhixProperty().get().getYzxwght())
                                .add(md.sapYmmtonggProperty().get().getZtgwght().multiply(BigDecimal.valueOf(xd.zrolmgeProperty().get())));
                    }
                });
            } else {
            }
        });
        md.zsgwghtProperty().bind(new ObjectBinding<BigDecimal>() {
            {
                bind(md.getXds());
            }

            @Override
            protected BigDecimal computeValue() {
                BigDecimal result = BigDecimal.ZERO;
                for (Xd xd : md.getXds()) {
                    result = result.add(xd.getZsgwght());
                }
                return result;
            }
        });
        md.zsnwghtProperty().bind(new ObjectBinding<BigDecimal>() {
            {
                bind(md.getXds());
            }

            @Override
            protected BigDecimal computeValue() {
                BigDecimal result = BigDecimal.ZERO;
                for (Xd xd : md.getXds()) {
                    result = result.add(xd.getZsnwght());
                }
                return result;
            }
        });
    }
}
