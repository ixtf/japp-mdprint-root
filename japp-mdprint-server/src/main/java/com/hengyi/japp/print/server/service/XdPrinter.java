package com.hengyi.japp.print.server.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface XdPrinter {

    Iterable<JsonNode> printXd(Iterable<JsonNode> xds) throws Exception;
}
