package com.prelude.social_app.controller;

import com.prelude.social_app.dto.response.ResponseApi;
import com.prelude.social_app.dto.resquest.RegisterReq;
import com.prelude.social_app.model.ApplicationUser;
import com.prelude.social_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseApi<ApplicationUser>> registerUser(@RequestBody RegisterReq user) {
        ResponseApi<ApplicationUser> response = userService.register(user);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
