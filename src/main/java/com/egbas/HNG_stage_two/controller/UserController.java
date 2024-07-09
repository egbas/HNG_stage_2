package com.egbas.HNG_stage_two.controller;

import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.repository.UserRepository;
import com.egbas.HNG_stage_two.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    @GetMapping("users/{userId}")
    ResponseEntity<ApiResponse<?>> getUserRecord(@PathVariable Long userId){
        ApiResponse<?> response = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
