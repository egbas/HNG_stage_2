package com.egbas.HNG_stage_two.payload;

import com.egbas.HNG_stage_two.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestOrg {
    //@NotBlank(message = "Organisation name must not be null")
    //private String name;

   // @NotBlank(message = "Description must not be null")
    private String description;

    private Set<User> users;

}
