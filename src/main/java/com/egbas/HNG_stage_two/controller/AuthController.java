package com.egbas.HNG_stage_two.controller;

import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.payload.LoginRequest;
import com.egbas.HNG_stage_two.payload.RegisterRequest;
import com.egbas.HNG_stage_two.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequest registerRequest){
        ApiResponse<?> response = authService.register(registerRequest);
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST.value()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                            .status("Bad request")
                            .message("Registration unsuccessful")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("login")
    ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest loginRequest){
        ApiResponse<?> response = authService.login(loginRequest);
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder()
                    .status("Bad request")
                    .message("Authentication failed")
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
