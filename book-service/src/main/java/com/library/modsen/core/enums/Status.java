package com.library.modsen.core.enums;

public enum Status {
    FREE("FREE"),
    BUSY("BUSY");

    private String status;
    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
