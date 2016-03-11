package com.hengyi.japp.print.server.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface MdPrinter {

    Iterable<JsonNode> printMd(Iterable<JsonNode> mds) throws Exception;
}
