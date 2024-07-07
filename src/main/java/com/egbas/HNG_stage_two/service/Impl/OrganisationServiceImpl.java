package com.egbas.HNG_stage_two.service.Impl;

import com.egbas.HNG_stage_two.entity.Organisation;
import com.egbas.HNG_stage_two.entity.User;
import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.payload.OrganisationResponse;
import com.egbas.HNG_stage_two.payload.RegisterRequestOrg;
import com.egbas.HNG_stage_two.repository.OrganisationRepository;
import com.egbas.HNG_stage_two.repository.UserRepository;
import com.egbas.HNG_stage_two.service.OrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrganisationServiceImpl implements OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;
    @Override
    public ApiResponse<?> registerOrg(RegisterRequestOrg registerRequestOrg) {
        User user = userRepository.findByEmail(getLoginUser()).get();

        Organisation organisation = Organisation.builder()
                .name(user.getFirstName()+"'s Organisation")
                .description(registerRequestOrg.getDescription())
                .users((Set<User>) user)
                .build();
        Organisation savedOrg = organisationRepository.save(organisation);

        return ApiResponse.<Organisation>builder()
                .status("success")
                .message("Organisation created successfully")
                .data(savedOrg)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public ApiResponse<?> addUserToOrg(String orgId, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        Organisation organisation = Organisation.builder()
                .users((Set<User>) user)
                .build();

        organisationRepository.save(organisation);

        return ApiResponse.builder()
                .status("success")
                .message("User added to organisation successfully")
                .statusCode(HttpStatus.OK.value())
                .build();


    }

    @Override
    public ApiResponse<?> getOrgById(String orgId) {
        Organisation organisation = organisationRepository.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organisation not found with ID: "+ orgId));

        OrganisationResponse organisationResponse = OrganisationResponse.builder()
                .orgId(organisation.getOrgId())
                .name(organisation.getName())
                .description(organisation.getDescription())
                .build();

        return ApiResponse.builder()
                .status("success")
                .message("<message>")
                .data(organisationResponse)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ApiResponse<?> getAllOrgByUser() {
        User user = userRepository.findByEmail(getLoginUser()).get();
        List<Organisation> organisationList = organisationRepository.findByUsers_UserId(user.getUserId());

        List<OrganisationResponse> organisationResponseList = convertToList(organisationList);
        Map<String, List<OrganisationResponse>> data = new HashMap<>();
        data.put("organisations", organisationResponseList);

        // Build the API response
        return ApiResponse.builder()
                .status("success")
                .message("Organisations retrieved successfully")
                .data(data)
                .build();


    }

    public static String getLoginUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails){
            return ((UserDetails)principal).getUsername();
        } else{
            return principal.toString();
        }
    }

    private List<OrganisationResponse> convertToList(List<Organisation> requests){
        List<OrganisationResponse> organisationResponseList = new ArrayList<>();
        for (Organisation organisation : requests){
            OrganisationResponse categories = OrganisationResponse.builder()
                    .orgId(organisation.getOrgId())
                    .name(organisation.getName())
                    .description(organisation.getDescription())
                    .build();

            organisationResponseList.add(categories);
        }

        return organisationResponseList;
    }
}
