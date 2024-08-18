package com.prelude.social_app.dto.resquest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
public class RegisterReq {
    private String email;
    private String password;
    private Date birthdayDate;


}
