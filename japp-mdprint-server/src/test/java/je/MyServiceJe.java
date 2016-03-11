package je;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.hengyi.japp.print.server.service.*;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

/**
 * Created by Administrator on 2016/2/27.
 */
public class MyServiceJe {
    //在服务的构造函数中要用到，必须在new service之前创建
    public static final EventBus eventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
    //全局锁,写操作需全局阻塞
    public static final String basePath = "d:/japp-mdprint";
    public static final Lock gLock = new ReentrantLock();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final MyServiceJe instance = new MyServiceJe();
    private static HttpServer httpServer;

    public static final SapService sapService = new SapService();
    public static final JobService jobService = new JobService();
    public static final OperatorService operatorService = new OperatorService();
    public static final LuceneService_SapMara luceneSapMara = new LuceneService_SapMara(basePath + "/lucene/SapMara");
    public static final LuceneService_Md luceneMd = new LuceneService_Md(basePath + "/lucene/Md");

    public static Environment myDbEnv;
    public static Database sapT001Db;
    public static Database sapT001wDb;
    public static Database sapT001lDb;
    public static Database sapZpackageBb;
    public static Database sapYmmbanciDb;
    public static Database sapMaraDb;
    public static Database mdDb;
    public static BiFunction<Database, String, ObjectNode> getByKeyF = (db, keyS) -> {
        DatabaseEntry key = new DatabaseEntry();
        DatabaseEntry data = new DatabaseEntry();
        StringBinding.stringToEntry(keyS, key);
        if (OperationStatus.SUCCESS == db.get(null, key, data, LockMode.DEFAULT)) {
            try {
                return objectMapper.readValue(StringBinding.entryToString(data), ObjectNode.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    };

    static {
        File envHome = new File(basePath + "/db");
        boolean initDbData = !envHome.exists();
        if (!envHome.exists()) {
            initDbData = true;
            envHome.mkdirs();
        }

        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        myEnvConfig.setReadOnly(false);
        myEnvConfig.setAllowCreate(true);
        myEnvConfig.setTransactional(true);
        myDbEnv = new Environment(envHome, myEnvConfig);

        DatabaseConfig myDbConfig = new DatabaseConfig();
        myDbConfig.setReadOnly(false);
        myDbConfig.setAllowCreate(true);
        myDbConfig.setTransactional(true);

        sapT001Db = myDbEnv.openDatabase(null, "SapT001Db", myDbConfig);
        sapT001wDb = myDbEnv.openDatabase(null, "SapT001wDb", myDbConfig);
        sapT001lDb = myDbEnv.openDatabase(null, "SapT001lDb", myDbConfig);
        sapZpackageBb = myDbEnv.openDatabase(null, "SapZpackageBb", myDbConfig);
        sapYmmbanciDb = myDbEnv.openDatabase(null, "SapYmmbanciDb", myDbConfig);
        sapMaraDb = myDbEnv.openDatabase(null, "sapMaraDb", myDbConfig);
        mdDb = myDbEnv.openDatabase(null, "mdDb", myDbConfig);
        if (initDbData) {
            DatabaseEntry key = new DatabaseEntry();
            StringBinding.stringToEntry("3000", key);
            DatabaseEntry data = new DatabaseEntry();
            StringBinding.stringToEntry(JsonNodeFactory.instance.objectNode().put("bukrs", "3000").put("butxt", "高新").toString(), data);
            sapT001Db.put(null, key, data);
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

                sapT001Db.close();
                sapT001wDb.close();
                sapT001lDb.close();
                sapMaraDb.close();
                sapZpackageBb.close();
                sapYmmbanciDb.close();
                myDbEnv.close();
            }
            this.notify();
        }
    }
}
