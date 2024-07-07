package com.egbas.HNG_stage_two.service;

import com.egbas.HNG_stage_two.entity.User;
import com.egbas.HNG_stage_two.payload.ApiResponse;
import com.egbas.HNG_stage_two.payload.RegisterRequestOrg;

public interface OrganisationService {
    ApiResponse<?> registerOrg(RegisterRequestOrg registerRequestOrg);
    ApiResponse<?> addUserToOrg(String orgId, String userId);
    ApiResponse<?> getOrgById(String orgId);
    ApiResponse<?> getAllOrgByUser();
}
