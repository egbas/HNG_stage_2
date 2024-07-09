package com.egbas.HNG_stage_two.service.Impl;

import com.egbas.HNG_stage_two.entity.User;
import com.egbas.HNG_stage_two.exceptions.UserNotFoundException;
import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.payload.UserResponse;
import com.egbas.HNG_stage_two.repository.UserRepository;
import com.egbas.HNG_stage_two.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public ApiResponse<?> getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with ID: "+userId));

        Map<String, Object> data = new HashMap<>();
        UserResponse userResponse = UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
        data.put("data", userResponse);

        return ApiResponse.builder()
                .status("success")
                .message("<message>")
                .data(data)
                .statusCode(HttpStatus.OK)
                .build();
    }
}
