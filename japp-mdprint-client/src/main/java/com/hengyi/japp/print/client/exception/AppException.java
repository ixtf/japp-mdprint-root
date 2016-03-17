package com.hengyi.japp.print.client.exception;

/**
 * Created by jzb on 16-3-3.
 */
public class AppException extends Exception {
    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String error) {
        super(error);
    }
}
