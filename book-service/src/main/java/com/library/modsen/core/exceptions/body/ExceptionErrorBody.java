package com.library.modsen.core.exceptions.body;

import java.io.Serializable;

public class ExceptionErrorBody implements Serializable {
    private String logRef;
    private String message;

    public ExceptionErrorBody() {
    }

    public ExceptionErrorBody(String logRef, String message) {
        this.logRef = logRef;
        this.message = message;
    }

    public String getLogRef() {
        return logRef;
    }

    public String getMessage() {
        return message;
    }
}
