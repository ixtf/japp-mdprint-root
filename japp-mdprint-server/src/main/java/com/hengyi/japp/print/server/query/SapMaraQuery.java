package com.hengyi.japp.print.server.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.hengyi.japp.common.search.JappPageQuery;
import com.hengyi.japp.print.server.MyService;
import com.sun.security.auth.UserPrincipal;

import java.io.IOException;

/**
 * Created by Administrator on 2016/2/28.
 */
public class SapMaraQuery extends JappPageQuery<JsonNode> {
    private String q;

    public SapMaraQuery(String bukrs) {
        super(new UserPrincipal(bukrs));
    }

    public String q() {
        return q;
    }

    public SapMaraQuery q(String q) {
        this.q = q;
        return this;
    }

    public JsonNode result() throws IOException {
        return MyService.luceneSapMara.query(this);
    }
}
