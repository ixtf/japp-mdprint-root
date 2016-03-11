package com.hengyi.japp.print.server.event;

/**
 * Created by Administrator on 2016/2/28.
 */
public class UnSyncSapDataEvent {
    private final String bukrs;

    public String getBukrs() {
        return bukrs;
    }

    public UnSyncSapDataEvent(String bukrs) {
        this.bukrs = bukrs;
    }
}
