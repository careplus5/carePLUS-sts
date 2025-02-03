package com.kosta.care.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
public class LoginEmployeeDto {
    @JsonProperty("username")
    //@NotNull
    private String username;
//
    @JsonProperty("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Builder
    private LoginEmployeeDto(String username, String password) {
        this.username = username;
        this.password = password;
    }



}
