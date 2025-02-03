package com.kosta.care.config.auth;

import org.springframework.http.HttpStatus;

public enum AuthExceptionType {

    INVALID_IDENTITY_ACCESS(401, HttpStatus.UNAUTHORIZED, "접근 권한을 벗어났습니다.");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    AuthExceptionType(int statusCode, HttpStatus httpStatus, String errorMessage) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
