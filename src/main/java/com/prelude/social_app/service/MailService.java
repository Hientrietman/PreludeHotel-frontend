package com.prelude.social_app.service;


import com.prelude.social_app.dto.resquest.MailVerificationRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(MailVerificationRequest email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(email.getTo());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getBody(),true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }
//    public void sendMailWithAttachmentsAndCc(MailVerificationReq mail, MultipartFile[] attachments) {
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//            mimeMessageHelper.setFrom(fromEmail);
//            mimeMessageHelper.setTo(mail.getTo());
//            if(mail.getCc()!=null){
//                mimeMessageHelper.setCc(mail.getCc());
//            }
//            mimeMessageHelper.setSubject("Email Verification");
//            mimeMessageHelper.setText(mail.getVerificationCode(), true);
//
//            if (attachments != null) {
//                for (MultipartFile attachment : attachments) {
//                    mimeMessageHelper.addAttachment(attachment.getOriginalFilename(), attachment);
//                }
//            }
//
//            mailSender.send(mimeMessage);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to send email", e);
//        }
//    }
}
