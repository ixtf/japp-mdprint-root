package lmdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.eventbus.EventBus;
import com.hengyi.japp.print.server.service.JobService;
import com.hengyi.japp.print.server.service.LuceneService_Md;
import com.hengyi.japp.print.server.service.LuceneService_SapMara;
import com.hengyi.japp.print.server.service.OperatorService;
import org.fusesource.lmdbjni.Database;
import org.fusesource.lmdbjni.Env;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.File;
import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.fusesource.lmdbjni.Constants.bytes;

/**
 * Created by Administrator on 2016/2/27.
 */
public class MyServiceLmdb {
    public static final String basePath = "d:/japp-mdprint";
    public static final Lock LOCK = new ReentrantLock();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final EventBus eventBus = new EventBus();
    private static final MyServiceLmdb instance = new MyServiceLmdb();
    private static HttpServer httpServer;

    public static final SapServiceLmdb sapService = new SapServiceLmdb();
    public static final JobService jobService = new JobService();
    public static final OperatorService operatorService = new OperatorService();
    public static final LuceneService_SapMara luceneSapMara = new LuceneService_SapMara(basePath + "/lucene/SapMara");
    public static final LuceneService_Md luceneMd = new LuceneService_Md(basePath + "/lucene/Md");

    public static Env dbEnv;
    public static Database db;

    static {
        File envHome = new File(basePath + "/db");
        boolean initDbData = !envHome.exists();
        if (initDbData)
            envHome.mkdirs();

        dbEnv = new Env(envHome.getPath());
        db = dbEnv.openDatabase();
        if (initDbData) {
            JsonNode node = JsonNodeFactory.instance.objectNode().put("bukrs", "3000").put("butxt", "高新");
            db.put(bytes("SapT001_" + node.get("bukrs").asText()), bytes(node.toString()));
        }
    }

    public static void windowsService(String args[]) {
        String cmd = args != null && args.length > 0 ? args[0] : "start";
        switch (cmd) {
            case "start":
                instance.start();
                break;
            default:
                instance.stop();
                break;
        }
    }


    public void start() {
        if (httpServer != null) return;
        final ResourceConfig rc = new ResourceConfig().packages("com.hengyi.japp.print.server.rest");
        httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:9999/api/"), rc);

        eventBus.register(sapService);
        eventBus.register(jobService);
        eventBus.register(operatorService);
        jobService.start();
    }

    public void stop() {
        synchronized (this) {
            if (httpServer != null) {
                httpServer.shutdownNow();
                httpServer = null;

                eventBus.unregister(sapService);
                eventBus.unregister(jobService);
                eventBus.unregister(operatorService);
                jobService.stop();

                db.close();
                dbEnv.close();
            }
            this.notify();
        }
    }
}
