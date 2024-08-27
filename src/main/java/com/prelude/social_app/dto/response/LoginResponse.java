package com.prelude.social_app.dto.response;

import com.prelude.social_app.model.ApplicationUser;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private ApplicationUser user;

}
