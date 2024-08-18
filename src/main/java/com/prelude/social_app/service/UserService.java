package com.prelude.social_app.service;

import com.prelude.social_app.dto.response.ResponseApi;
import com.prelude.social_app.dto.resquest.RegisterReq;
import com.prelude.social_app.exception.customError.TakenEmailException;
import com.prelude.social_app.exception.customError.TakenUserNameException;
import com.prelude.social_app.model.ApplicationUser;
import com.prelude.social_app.repository.RoleRepository;
import com.prelude.social_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // Phương thức đăng ký người dùng
    public ResponseApi<ApplicationUser> register(RegisterReq user) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setEmail(user.getEmail());
        applicationUser.setPassword(user.getPassword());
        applicationUser.setDob(user.getBirthdayDate());
        applicationUser.setUsername(generateUserName());

        // Thêm role trực tiếp vào authorities
        applicationUser.getAuthorities().add(roleRepository.findByAuthority("USER").get());
        if (userRepository.existsByEmail(applicationUser.getEmail())) {
            throw new TakenEmailException("Email already taken");
        }
        if (userRepository.existsByUsername(applicationUser.getUsername())) {
            throw new TakenUserNameException("Username already taken");
        }
        try {
            userRepository.save(applicationUser);
            return new ResponseApi<>(HttpStatus.CREATED,"User registered successfully", applicationUser, true);
        } catch (TakenEmailException e) {
            return new ResponseApi<>(HttpStatus.BAD_REQUEST,"Email already taken", null, false);
        } catch (TakenUserNameException e){
            return new ResponseApi<>(HttpStatus.BAD_REQUEST,"Username already taken", null, false);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred during registration", null, false);
        }
    }

    // Phương thức tạo tên người dùng ngẫu nhiên
    private static String generateUserName() {
        // Tạo UUID và lấy phần đầu của nó để làm username
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
