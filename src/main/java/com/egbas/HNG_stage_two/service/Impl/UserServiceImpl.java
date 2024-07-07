package com.egbas.HNG_stage_two.service.Impl;

import com.egbas.HNG_stage_two.entity.User;
import com.egbas.HNG_stage_two.exceptions.UserNotFoundException;
import com.egbas.HNG_stage_two.payload.ApiResponse;
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
    public ApiResponse<?> getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found with ID: "+userId));

        Map<String, Object> data = new HashMap<>();
        Map<String, String> userData = new HashMap<>();
        userData.put("userId", user.getUserId());
        userData.put("firstName", user.getFirstName());
        userData.put("lastName", user.getLastName());
        userData.put("email", user.getEmail());
        userData.put("phone", user.getPhone());
        data.put("data", userData);

        return ApiResponse.builder()
                .status("success")
                .message("<message>")
                .data(data)
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}
