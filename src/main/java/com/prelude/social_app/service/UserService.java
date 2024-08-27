package com.prelude.social_app.service;

import com.prelude.social_app.dto.response.ResponseApi;
import com.prelude.social_app.dto.resquest.MailVerificationRequest;
import com.prelude.social_app.dto.resquest.RegisterReq;
import com.prelude.social_app.exception.ApiErrorResponse;
import com.prelude.social_app.exception.customError.AccountRegistrationErrorException;
import com.prelude.social_app.exception.customError.TakenEmailException;
import com.prelude.social_app.exception.customError.TakenUserNameException;
import com.prelude.social_app.exception.customError.UserIdNotFoundException;
import com.prelude.social_app.model.ApplicationUser;
import com.prelude.social_app.repository.RoleRepository;
import com.prelude.social_app.repository.UserRepository;
import com.prelude.social_app.util.StringFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    // Repositories for interacting with the database
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final StringFormat stringFormat;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     *
     * @param user The registration request containing user details.
     * @return ResponseApi<ApplicationUser> containing the registered user and status.
     * @throws TakenEmailException    if the email is already in use.
     * @throws TakenUserNameException if the username is already taken.
     */
    public ResponseApi<ApplicationUser> register(RegisterReq user) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setEmail(user.getEmail());
        applicationUser.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUser.setDob(user.getBirthdayDate());
        applicationUser.setUsername(stringFormat.generateUserName());

        // Assign the "USER" role to the new user
        applicationUser.getAuthorities().add(roleRepository.findByAuthority("USER").get());

        // Check if email or username is already taken
        if (userRepository.existsByEmail(applicationUser.getEmail())) {
            throw new TakenEmailException("Email already taken");
        }
        if (userRepository.existsByUsername(applicationUser.getUsername())) {
            throw new TakenUserNameException("Username already taken");
        }

        try {
            // Save the new user in the database
            userRepository.save(applicationUser);
            return new ResponseApi<>(HttpStatus.CREATED, "User registered successfully", applicationUser, true);
        } catch (TakenEmailException e) {
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, "Email already taken", null, false);
        } catch (TakenUserNameException e) {
            return new ResponseApi<>(HttpStatus.BAD_REQUEST, "Username already taken", null, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during registration", null, false);
        }
    }

    /**
     * Updates the phone number of an existing user.
     *
     * @param userId The ID of the user to update.
     * @param phone  The new phone number.
     * @return ResponseApi<ApplicationUser> containing the updated user and status.
     */
    public ResponseApi<ApplicationUser> updatePhoneNumber(int userId, String phone) {
        try {
            // Find the user by ID or throw an exception if not found
            ApplicationUser applicationUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserIdNotFoundException("User not found with ID: " + userId));

            // Update the user's phone number
            applicationUser.setPhone(phone);
            userRepository.save(applicationUser);

            return new ResponseApi<>(HttpStatus.OK, "Phone number updated successfully", applicationUser, true);

        } catch (UserIdNotFoundException ex) {
            return new ResponseApi<>(HttpStatus.NOT_FOUND, ex.getMessage(), null, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while updating the phone number", null, false);
        }
    }

    /**
     * Generates a verification code for a user.
     *
     * @param userId The ID of the user to generate the verification code for.
     * @return ResponseApi<ApplicationUser> containing the user with the verification code and status.
     */
    public ResponseApi<ApplicationUser> createVerificationCode(int userId) {
        try {
            // Find the user by ID or throw an exception if not found
            ApplicationUser applicationUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserIdNotFoundException("User not found with ID: " + userId));

            // Generate and set the verification code for the user
            String verificationCode = stringFormat.generateVerificationCode();
            applicationUser.setVerificationCode(verificationCode);
            userRepository.save(applicationUser);

            return new ResponseApi<>(HttpStatus.OK, "Verification code generated successfully", applicationUser, true);

        } catch (UserIdNotFoundException ex) {
            return new ResponseApi<>(HttpStatus.NOT_FOUND, ex.getMessage(), null, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while generating the verification code", null, false);
        }
    }

    /**
     * Sends a verification email to the user.
     *
     * @param userid The ID of the user to send the email to.
     * @return ResponseApi<ApplicationUser> containing the status of the email sending process.
     */
    public ResponseApi<ApplicationUser> sendEmail(int userid) {
        try {
            ApplicationUser applicationUser = userRepository.findById(userid)
                    .orElseThrow(() -> new UserIdNotFoundException("User not found with ID: " + userid));

            if (!applicationUser.isEnabled()) {
                MailVerificationRequest mav = new MailVerificationRequest();
                mav.setTo(applicationUser.getEmail());
                mav.setSubject("Activation code for user with email: " + applicationUser.getEmail());
                mav.setBody("Here is your verifiacation code: " + applicationUser.getVerificationCode());
                mailService.sendVerificationEmail(mav);
                return new ResponseApi<>(HttpStatus.OK, "Verification code sent successfully", applicationUser, true);
            }
            throw new AccountRegistrationErrorException("Email already taken");

        } catch (UserIdNotFoundException ex) {
            return new ResponseApi<>(HttpStatus.NOT_FOUND, ex.getMessage(), null, false);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while sending the verification code", null, false);
        }
    }




    public ResponseApi<ApplicationUser> verifyEmail(int userid, String verificationCode) {
        try {
            ApplicationUser applicationUser = userRepository.findById(userid)
                    .orElseThrow(() -> new UserIdNotFoundException("User not found with ID: " + userid));

            if (stringFormat.cleanString(verificationCode).equals(applicationUser.getVerificationCode())) {
                applicationUser.setVerificationCode(null);
                applicationUser.setEnabled(true);
                userRepository.save(applicationUser);
                return new ResponseApi<>(HttpStatus.OK, "Verification code verified successfully", applicationUser, true);
            }
            throw new AccountRegistrationErrorException("Invalid verification code");
        } catch (UserIdNotFoundException ex) {
            return new ResponseApi<>(HttpStatus.NOT_FOUND, ex.getMessage(), null, false);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while sending the verification code", null, false);
        }
    }
    public ResponseApi<ApplicationUser> updatePassword(int userid, String password) {
        try {
            ApplicationUser applicationUser = userRepository.findById(userid)
                    .orElseThrow(() -> new UserIdNotFoundException("User not found with ID: " + userid));
            applicationUser.setPassword(passwordEncoder.encode(stringFormat.cleanString(password)));
            userRepository.save(applicationUser);
            return new ResponseApi<>(HttpStatus.OK, "Password updated successfully", applicationUser, true);
        } catch (UserIdNotFoundException ex) {
            return new ResponseApi<>(HttpStatus.NOT_FOUND, ex.getMessage(), null, false);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while sending the verification code", null, false);
        }
    }

}
