package com.prelude.social_app.service.interfaces;

import com.prelude.social_app.dto.response.LoginResponse;
import com.prelude.social_app.dto.response.ResponseApi;
import com.prelude.social_app.dto.resquest.LoginRequest;
import com.prelude.social_app.dto.resquest.RegisterReq;
import com.prelude.social_app.model.ApplicationUser;

public interface IUserService {
    public ResponseApi<ApplicationUser> register(RegisterReq user);
    public ResponseApi<ApplicationUser> updatePhoneNumber(int userId, String phone);
    public ResponseApi<ApplicationUser> createVerificationCode(int userId);
    public ResponseApi<ApplicationUser> sendEmail(int userid);
    public ResponseApi<ApplicationUser> verifyEmail(int userid, String verificationCode);
    public ResponseApi<ApplicationUser> updatePassword(int userid, String password);
    public ResponseApi<LoginResponse> login(LoginRequest req);
}
