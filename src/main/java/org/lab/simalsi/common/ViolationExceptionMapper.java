package org.lab.simalsi.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String field = null;
            String error = violation.getMessage();

            for (Path.Node node : violation.getPropertyPath()) {
                field = node.getName();
            }

            errors.put(field, error);
        }

        return Response.status(Response.Status.BAD_REQUEST)
            .type(MediaType.APPLICATION_JSON)
            .entity(new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), LocalDateTime.now(), errors))
            .build();
    }

    public static class ErrorResponse {
        public int status;
        public LocalDateTime timestamp;
        public Map<String, String> errors;

        public ErrorResponse(int status, LocalDateTime timestamp, Map<String, String> errors) {
            this.status = status;
            this.timestamp = timestamp;
            this.errors = errors;
        }
    }
}
