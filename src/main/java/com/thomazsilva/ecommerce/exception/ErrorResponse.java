package com.thomazsilva.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private int status;
    private String error;
    private LocalDateTime timestamp;
    private List<FieldErrorResponse> errors;

    public ErrorResponse(int status, String error, List<FieldErrorResponse> errors) {
        this.status = status;
        this.error = error;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<FieldErrorResponse> getErrors() {
        return errors;
    }
}
