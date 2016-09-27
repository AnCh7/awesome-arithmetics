package org.anch.arithmetics.service.errorhandling;

import javax.ws.rs.core.Response;

public class ArithmeticsServiceException extends Exception {

    private static final long serialVersionUID = -8999932578270387947L;
    private final Response.StatusType httpStatus;
    private final int httpCode;

    public ArithmeticsServiceException(Response.StatusType httpStatus, int httpCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.httpCode = httpCode;
    }

    Response.StatusType getHttpStatus() {
        return httpStatus;
    }

    int getHttpCode() {
        return httpCode;
    }
}
