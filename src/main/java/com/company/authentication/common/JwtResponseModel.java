package com.company.authentication.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class JwtResponseModel implements Serializable {

    private final String token;
}
