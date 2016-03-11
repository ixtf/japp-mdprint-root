/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.print.server.rest;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hengyi.japp.common.exception.AppException;
import com.hengyi.japp.common.exception.AppMultiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author jzb
 */
@Provider
public class JappExceptionMapper implements ExceptionMapper<Throwable> {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Response toResponse(Throwable exception) {
        log.debug("", exception);
        ArrayNode arrayErrors = JsonNodeFactory.instance.arrayNode();
        getError(arrayErrors, exception);
        return Response.status(Status.FORBIDDEN).entity(arrayErrors).build();
    }

    private void getError(ArrayNode arrayErrors, Throwable exception) {
        if (exception == null) {
            return;
        }

        if (exception instanceof AppException) {
            AppException appException = (AppException) exception;
            ObjectNode error = JsonNodeFactory.instance.objectNode().put("errorCode", appException.getErrorCode());
            arrayErrors.add(error);
        } else if (exception instanceof AppMultiException) {
            AppMultiException appMultiException = (AppMultiException) exception;
            appMultiException.getAppExceptions().forEach(o -> arrayErrors.add(JsonNodeFactory.instance.objectNode().put("errorCode", o.getErrorCode())));
        } else if (StringUtils.isNotBlank(exception.getMessage())) {
            arrayErrors.add(JsonNodeFactory.instance.objectNode().put("errorMsg", exception.getMessage()));
        }

        getError(arrayErrors, exception.getCause());
    }
}
