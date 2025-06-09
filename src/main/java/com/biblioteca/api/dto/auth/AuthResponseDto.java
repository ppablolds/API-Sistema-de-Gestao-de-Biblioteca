package com.biblioteca.api.dto.auth;

public class AuthResponseDto {

    private String jwt;

    public AuthResponseDto() {}

    public AuthResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
