package com.egbas.HNG_stage_two.controller;

import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.payload.RegisterRequestOrg;
import com.egbas.HNG_stage_two.service.OrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class OrganisationController {
    private final OrganisationService organisationService;

    @GetMapping("organisations")
    ResponseEntity<ApiResponse<?>> getAllUserOrg(){
        ApiResponse<?> response = organisationService.getAllOrgByUser();

        return ResponseEntity.status(HttpStatus.OK).body(response);
       // return new ResponseEntity<>(response, HttpStatus.OK); can also be done this way
    }

    @GetMapping("organisations/{orgId}")
    ResponseEntity<ApiResponse<?>> getOrgById(@PathVariable Long orgId){
        ApiResponse<?> response = organisationService.getOrgById(orgId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
        // return new ResponseEntity<>(response, HttpStatus.OK); can also be done this way
    }

    @PostMapping("organisations")
    ResponseEntity<ApiResponse<?>> createOrg(@RequestBody RegisterRequestOrg registerRequestOrg){
        ApiResponse<?> response = organisationService.registerOrg(registerRequestOrg);

        if (response.getStatusCode() == HttpStatus.CREATED){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            ApiResponse<?> failedResponse = ApiResponse.builder()
                    .status("Bad request")
                    .message("Client error")
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build();
            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);


        }
        // return new ResponseEntity<>(response, HttpStatus.OK); can also be done this way
    }

    @PostMapping("organisations/{orgId}/users")
    ResponseEntity<ApiResponse<?>> getOrgById(@PathVariable Long orgId, @RequestBody Long userId){
        ApiResponse<?> response = organisationService.addUserToOrg(orgId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
        // return new ResponseEntity<>(response, HttpStatus.OK); can also be done this way
    }


}
