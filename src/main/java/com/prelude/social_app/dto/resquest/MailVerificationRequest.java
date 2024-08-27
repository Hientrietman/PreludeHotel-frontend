package com.prelude.social_app.dto.resquest;

import lombok.Data;

@Data
public class MailVerificationRequest {
    private String to;
    private String subject;
    private String body;

}
