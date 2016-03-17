package main;

import com.hengyi.japp.print.server.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/2/28.
 */
public class ServerMain {
    public static Logger log = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) throws Exception {
        MyService.windowsService(null);
        long _start = System.currentTimeMillis();
//        MyService.jobService.onEvent(new SyncSapDataEvent("3000"));
//        MyService.sapService.syncData("3000");
        long _end = System.currentTimeMillis();
        System.out.println("结束:" + ((_end - _start) / 1000));
    }
}
