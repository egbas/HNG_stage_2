package com.egbas.HNG_stage_two.service;

import com.egbas.HNG_stage_two.entity.User;
import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.payload.RegisterRequestOrg;

public interface OrganisationService {
    ApiResponse<?> registerOrg(RegisterRequestOrg registerRequestOrg);
    ApiResponse<?> addUserToOrg(Long orgId, Long userId);
    ApiResponse<?> getOrgById(Long orgId);
    ApiResponse<?> getAllOrgByUser();
}
