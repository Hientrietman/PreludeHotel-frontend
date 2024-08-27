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

    @PostMapping("/{userid}/email/code")
    public ResponseEntity<ResponseApi<ApplicationUser>> createEmailVerification(@PathVariable int userid  ) {
        ResponseApi<ApplicationUser> response = userService.createVerificationCode(userid);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/{userid}/email/send")
    public ResponseEntity<ResponseApi<ApplicationUser>> sendEmailVerification(
            @PathVariable int userid
            ){

        ResponseApi<ApplicationUser> response = userService.sendEmail(userid);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PostMapping("/{userid}/email/verify")
    public ResponseEntity<ResponseApi<ApplicationUser>> verifyEmail(
            @PathVariable int userid,
            @RequestBody String verificationCode
    ){

        ResponseApi<ApplicationUser> response = userService.verifyEmail(userid, verificationCode);
        return new ResponseEntity<>(response, response.getStatus());
    }


}
