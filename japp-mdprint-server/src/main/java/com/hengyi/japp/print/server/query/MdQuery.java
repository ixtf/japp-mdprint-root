package com.hengyi.japp.print.server.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.hengyi.japp.common.search.JappPageQuery;
import com.sun.security.auth.UserPrincipal;

import static com.hengyi.japp.print.server.MyService.luceneMd;

/**
 * Created by Administrator on 2016/2/28.
 */
public class MdQuery extends JappPageQuery<JsonNode> {
    private String q;

    public MdQuery(String bukrs) {
        super(new UserPrincipal(bukrs));
    }

    public String q() {
        return q;
    }

    public MdQuery q(String q) {
        this.q = q;
        return this;
    }

    public JsonNode result() throws Exception {
        return luceneMd.query(this);
    }
}
