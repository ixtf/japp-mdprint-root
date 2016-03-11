package com.hengyi.japp.print.client.Event;

import com.hengyi.japp.print.client.domain.SapT001;

/**
 * Created by jzb on 16-3-3.
 */
public class SapT001LoginEvent {
    private final SapT001 sapT001;

    public SapT001LoginEvent(SapT001 sapT001) {
        this.sapT001 = sapT001;
    }

    public SapT001 getSapT001() {
        return sapT001;
    }
}
