package com.egbas.HNG_stage_two.repository;

import com.egbas.HNG_stage_two.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    List<Organisation> findByUsers_UserId(Long userId);

}
