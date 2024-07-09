package com.egbas.HNG_stage_two.payload;

import com.egbas.HNG_stage_two.entity.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User register fields")
public class RegisterRequest {

    @NotBlank(message = "Firstname must not be null")
    private String firstName;

    @NotBlank(message = "Lastname must not be null")
    private String lastName;

    @Email(message = "Email must be unique and not null")
    @NotBlank(message = "Email must not be null")
    private String email;

    @NotBlank(message = "Password must not be null")
    private String password;

    @NotBlank(message = "Phonenumber must not be null")
    private String phone;

    @JsonIgnore
    private Set<Organisation> organisations;
}
