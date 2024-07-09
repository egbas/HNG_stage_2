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
                .users(new HashSet<>(Collections.singleton(user)))
                .build();
        Organisation savedOrg = organisationRepository.save(organisation);
        OrganisationResponse org = OrganisationResponse.builder()
                .orgId(savedOrg.getOrgId())
                .name(savedOrg.getName())
                .description(savedOrg.getDescription())
                .build();

        return ApiResponse.<OrganisationResponse>builder()
                .status("success")
                .message("Organisation created successfully")
                .data(org)
                .statusCode(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ApiResponse<?> addUserToOrg(Long orgId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        Organisation organisation = Organisation.builder()
                .users(new HashSet<>(Collections.singleton(user)))
                .build();

        organisationRepository.save(organisation);

        return ApiResponse.builder()
                .status("success")
                .message("User added to organisation successfully")
                .statusCode(HttpStatus.OK)
                .build();


    }

    @Override
    public ApiResponse<?> getOrgById(Long orgId) {
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
                .statusCode(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<?> getAllOrgByUser() {
        User user = userRepository.findByEmail(getLoginUser()).get();
        Set<Organisation> organisationList = user.getOrganisations();

        List<OrganisationResponse> organisationResponseList = convertToList(organisationList);
        return ApiResponse.builder()
                .status("success")
                .message("Organisations retrieved successfully")
                .data(organisationResponseList)
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

    private List<OrganisationResponse> convertToList(Set<Organisation> requests){
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
