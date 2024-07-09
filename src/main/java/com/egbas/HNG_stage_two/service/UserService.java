package com.egbas.HNG_stage_two.service;

import com.egbas.HNG_stage_two.payload.ApiResponse;

public interface UserService {
    ApiResponse<?> getUserById(Long userId);

}
