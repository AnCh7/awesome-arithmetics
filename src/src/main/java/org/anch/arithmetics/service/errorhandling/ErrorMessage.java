package org.anch.arithmetics.service.errorhandling;

public class ErrorMessage {

    private String httpStatus;
    private int httpCode;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(ArithmeticsServiceException ex) {
        this.httpStatus = ex.getHttpStatus().toString();
        this.httpCode = ex.getHttpCode();
        this.message = ex.getMessage();
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }
}
