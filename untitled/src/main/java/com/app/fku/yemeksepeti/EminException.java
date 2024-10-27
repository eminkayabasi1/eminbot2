package com.app.fku.yemeksepeti;

public class EminException extends Exception {
    private String errorMesage;
    private String errorCode;

    public EminException(String errorMessage, String errorCode) {

        this.errorMesage = errorMesage;
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
