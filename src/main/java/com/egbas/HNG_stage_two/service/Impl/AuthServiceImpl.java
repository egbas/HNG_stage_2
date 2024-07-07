package com.egbas.HNG_stage_two.service.Impl;

import com.egbas.HNG_stage_two.config.JwtService;
import com.egbas.HNG_stage_two.entity.Organisation;
import com.egbas.HNG_stage_two.entity.User;
import com.egbas.HNG_stage_two.payload.*;
import com.egbas.HNG_stage_two.repository.OrganisationRepository;
import com.egbas.HNG_stage_two.repository.UserRepository;
import com.egbas.HNG_stage_two.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final OrganisationRepository organisationRepository;
    @Override
    public ApiResponse<?> register(RegisterRequest registerRequest) {

        List<ValidationErrorResponse.ValidationError> validationErrors = new ArrayList<>();

        if (registerRequest.getFirstName() == null || registerRequest.getFirstName().isEmpty()) {
            validationErrors.add(ValidationErrorResponse.ValidationError.builder()
                    .field("firstName")
                    .message("First name is required")
                    .build());
        }
        if (registerRequest.getLastName() == null || registerRequest.getLastName().isEmpty()) {
            validationErrors.add(ValidationErrorResponse.ValidationError.builder()
                    .field("lastName")
                    .message("Last name is required")
                    .build());
        }
        if (registerRequest.getPhone() == null || registerRequest.getPhone().isEmpty()) {
            validationErrors.add(ValidationErrorResponse.ValidationError.builder()
                    .field("phone")
                    .message("Phone number is required")
                    .build());
        }
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            validationErrors.add(ValidationErrorResponse.ValidationError.builder()
                    .field("email")
                    .message("Email is required")
                    .build());
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
            validationErrors.add(ValidationErrorResponse.ValidationError.builder()
                    .field("password")
                    .message("Password is required")
                    .build());
        }

        if (!validationErrors.isEmpty()) {
            ValidationErrorResponse validationErrorResponse = ValidationErrorResponse.builder()
                    .errors(validationErrors)
                    .build();
            return ApiResponse.<ValidationErrorResponse>builder()
                    .data(validationErrorResponse)
                    .build();
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            ValidationErrorResponse.ValidationError validationErrorResponse = ValidationErrorResponse.ValidationError.builder()
                    .field("Email")
                    .message("Email already exist")
                    .build();
            return ApiResponse.<ValidationErrorResponse.ValidationError>builder()
                    .data(validationErrorResponse)
                    .build();
        }
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .password(passwordEncoder.encode(registerRequest.getPassword())) // This is used to encrypt the password using the password encoder bean
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        Organisation organisation = Organisation.builder()
                .name(user.getFirstName()+"'s Organisation")
                .users((Set<User>) savedUser)
                .build();
        organisationRepository.save(organisation);

        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", jwtToken);  // Replace with actual token generation logic
        Map<String, String> userData = new HashMap<>();
        userData.put("userId", savedUser.getUserId());
        userData.put("firstName", savedUser.getFirstName());
        userData.put("lastName", savedUser.getLastName());
        userData.put("email", savedUser.getEmail());
        userData.put("phone", savedUser.getPhone());
        data.put("user", userData);

//Depending on the situation you can return only the access token
        return ApiResponse.<Map<String, Object>>builder()
                .status("success")
                .message("Registration successful")
                .data(data)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public ApiResponse<?> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword())
        );

        User savedUser = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(savedUser);

        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", jwtToken);  // Replace with actual token generation logic
        Map<String, String> userData = new HashMap<>();
        userData.put("userId", savedUser.getUserId());
        userData.put("firstName", savedUser.getFirstName());
        userData.put("lastName", savedUser.getLastName());
        userData.put("email", savedUser.getEmail());
        userData.put("phone", savedUser.getPhone());
        data.put("user", userData);

        return ApiResponse.<Map<String, Object>>builder()
                .status("success")
                .message("Registration successful")
                .data(data)
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
