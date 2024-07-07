package com.egbas.HNG_stage_two.service;

import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.payload.LoginRequest;
import com.egbas.HNG_stage_two.payload.RegisterRequest;

public interface AuthService {

    ApiResponse<?> register(RegisterRequest registerRequest);
    ApiResponse<?> login(LoginRequest loginRequest);
}
