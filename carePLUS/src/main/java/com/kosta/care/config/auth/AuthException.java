package com.kosta.care.config.auth;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthException {
    private static final String NURSE_IDENTITY = "12";
    private static final String DOCTOR_IDENTITY = "11";

    private final AuthExceptionType type;

    public static void invalidNurseAccess(String identity) {
        if(!NURSE_IDENTITY.equals(identity)) {
            throw new IllegalArgumentException(AuthExceptionType.INVALID_IDENTITY_ACCESS.getErrorMessage());
        }
    }

    public static void invalidDoctorAccess(String identity) {
        if(!DOCTOR_IDENTITY.equals(identity)) {
            throw new IllegalArgumentException(AuthExceptionType.INVALID_IDENTITY_ACCESS.getErrorMessage());
        }
    }
}
