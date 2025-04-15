package com.example.demo.dto;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final String name;
    public AuthenticationResponse(String jwt,String name) {
        this.jwt = jwt;
        this.name=name;
    }

    public String getJwt() {
        return jwt;
    }
    
    public String getName() {
    	return name; 
    }
}
