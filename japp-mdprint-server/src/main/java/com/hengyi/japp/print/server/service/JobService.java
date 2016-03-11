package com.hengyi.japp.print.server.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.hengyi.japp.common.JappDateTimeUtil;
import com.hengyi.japp.print.server.event.SyncSapDataEvent;
import com.hengyi.japp.print.server.event.UnSyncSapDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.hengyi.japp.print.server.Constant.getByKeyF;
import static com.hengyi.japp.print.server.Constant.keySapT001F;
import static com.hengyi.japp.print.server.MyService.sapService;

/**
 * Created by Administrator on 2016/2/28.
 */
public class JobService {
    private Logger log = LoggerFactory.getLogger(getClass());
    private static final Lock lock = new ReentrantLock();
    //已经加入周期同步的公司
    private static final Set<String> syncBukrsSet = Sets.newConcurrentHashSet();
    private ScheduledExecutorService ses;

    private final class SyncTask implements Runnable {
        private final String bukrs;

        public SyncTask(String bukrs) {
            this.bukrs = bukrs;
        }

        @Override
        public void run() {
            if (syncBukrsSet.contains(bukrs))
                try {
                    sapService.syncData(bukrs);
                } catch (Exception e) {
                    log.error("公司[" + bukrs + "],数据同步出错!", e);
                    syncBukrsSet.remove(bukrs);
                }
        }
    }

    @Subscribe
    public void onEvent(SyncSapDataEvent ev) {
        if (syncBukrsSet.contains(ev.getBukrs())) return;
        lock.lock();
        try {
            if (syncBukrsSet.contains(ev.getBukrs())) return;
            ObjectNode sapT001 = getByKeyF.compose(keySapT001F).apply(ev.getBukrs());
            if (sapT001 == null) return;
            syncBukrsSet.add(ev.getBukrs());
            SyncTask syncTask = new SyncTask(ev.getBukrs());
            if (null == sapT001.get("syncDate")) {
                //第一次登入，需要等待同步完成，阻塞
                sapService.syncData(ev.getBukrs());
                ses.scheduleWithFixedDelay(syncTask, 1, 1, TimeUnit.DAYS);
            } else {
                LocalDate lastSyncDate = JappDateTimeUtil.toLocalDate(JappDateTimeUtil.fromDateTimeString(sapT001.get("syncDate").asText()));
                LocalDate current = LocalDate.now();
                ses.scheduleWithFixedDelay(syncTask, (current.isAfter(lastSyncDate)) ? 0 : 1, 1, TimeUnit.DAYS);
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            lock.unlock();
        }
    }

    @Subscribe
    public void onEvent(UnSyncSapDataEvent ev) {
        if (syncBukrsSet.size() < 2) return;
        syncBukrsSet.remove(ev.getBukrs());
    }

    public void start() {
        if (ses != null && !ses.isShutdown())
            ses.shutdownNow();
        ses = Executors.newSingleThreadScheduledExecutor();
    }

    public void stop() {
        syncBukrsSet.clear();
        if (ses != null && !ses.isShutdown())
            ses.shutdownNow();
        ses = null;
    }
}
