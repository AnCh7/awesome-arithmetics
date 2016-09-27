package org.anch.arithmetics.service.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AppExceptionMapper implements ExceptionMapper<ArithmeticsServiceException> {

    public Response toResponse(ArithmeticsServiceException ex) {
        return Response
                .status(ex.getHttpStatus())
                .entity(new ErrorMessage(ex))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}