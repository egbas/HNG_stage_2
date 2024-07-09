package com.egbas.HNG_stage_two.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "organisation_tbl")
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId;

    @NotBlank(message = "Organisation name must not be null")
    private String name;

    //@NotBlank(message = "Description must not be null")
    private String description;

    @ManyToMany(mappedBy = "organisations", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();
}
