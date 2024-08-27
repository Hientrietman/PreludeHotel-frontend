package com.prelude.social_app.dto.resquest;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
