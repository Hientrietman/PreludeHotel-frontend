package com.prelude.social_app.controller;

import com.prelude.social_app.dto.response.ResponseApi;
import com.prelude.social_app.model.ApplicationUser;
import com.prelude.social_app.service.UserService;
import com.prelude.social_app.util.StringFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final StringFormat stringFormat;

    @PutMapping("/{userid}/update/phone")
    public ResponseEntity<ResponseApi<ApplicationUser>> updatePhoneNumber(
            @PathVariable int userid,
            @RequestBody String phone) {
        // Làm sạch số điện thoại
        String cleanedPhone = stringFormat.cleanString(phone);

        ResponseApi<ApplicationUser> response = userService.updatePhoneNumber(userid, cleanedPhone);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PutMapping("/{userid}/update/password")
    public ResponseEntity<ResponseApi<ApplicationUser>> updatePassword(
            @PathVariable int userid,
            @RequestBody String password) {


        ResponseApi<ApplicationUser> responseApi= userService.updatePassword(userid,password);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

}
