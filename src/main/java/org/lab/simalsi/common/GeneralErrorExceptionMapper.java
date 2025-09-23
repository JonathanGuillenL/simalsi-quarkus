package org.lab.simalsi.common;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;

@Provider
public class GeneralErrorExceptionMapper implements ExceptionMapper<GeneralErrorException> {
    @Override
    public Response toResponse(GeneralErrorException exception) {

        return Response.status(Response.Status.BAD_REQUEST)
            .type(MediaType.APPLICATION_JSON)
            .entity(new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), LocalDateTime.now(), exception.getMessage()))
            .build();
    }
}

