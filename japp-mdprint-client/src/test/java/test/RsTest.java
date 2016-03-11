package test;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hengyi.japp.print.client.domain.SapT001;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.hengyi.japp.print.client.Constant.destination;
import static com.hengyi.japp.print.client.Constant.objectMapper;

/**
 * Created by jzb on 16-3-3.
 */
public class RsTest {
    public static void main(String[] args) throws IOException {
        Response r = destination.path("sapT001s").request(MediaType.APPLICATION_JSON_TYPE).get();
        String json = r.readEntity(String.class);
        List<SapT001> sapT001s = objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, SapT001.class));
        for (Object node : sapT001s) {
            System.out.println(node);
        }
        destination.path("sdfsfsdf").request(MediaType.APPLICATION_JSON_TYPE).get();
        System.out.println(r);
    }
}
