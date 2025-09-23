package org.lab.simalsi.common;

import java.time.LocalDateTime;

public class ErrorResponse {
    public int status;
    public LocalDateTime timestamp;
    public Object error;

    public ErrorResponse(int status, LocalDateTime timestamp, Object error) {
        this.status = status;
        this.timestamp = timestamp;
        this.error = error;
    }
}
