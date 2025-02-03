package com.kosta.care.entity;

public enum Role {

    DOCTOR("11","DOCTOR"),
    NURSE("12","NURSE"),
    ADMIN_HOSPITAL("13","ADMIN_HOSPITAL"),
    TECHNICIAN("14","TECHNICIAN"),
    ADMIN("99","ADMIN");

    private String identity;
    private String role;

    private Role(String identity, String role) {
        this.identity = identity;
        this.role = role;
    }

    public String getIdentity() {
        return identity;
    }
    public String getRole() {
        return role;
    }

    public static String getRoleByIdentity(String identity) {

        for(Role role : Role.values()) {
            if(identity.equals(role.getIdentity())) {
                return role.getRole();
            }
        }
         throw new IllegalArgumentException("Invalid role identity: " + identity);
    }
}
