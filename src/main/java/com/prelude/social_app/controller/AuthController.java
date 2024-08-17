package com.prelude.social_app.controller;

import com.prelude.social_app.model.ApplicationUser;
import com.prelude.social_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody ApplicationUser user) {
        return userService.register(user);
    }
}
