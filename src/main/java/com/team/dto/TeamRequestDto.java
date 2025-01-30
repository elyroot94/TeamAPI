package com.team.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDto {

    @NotBlank(message = "nom requis")
    private String name;
    @NotBlank(message = "acronym requis")
    private String acronym;
    private Set<Long> playerIds=new HashSet<>();
    @NotBlank(message = "budge requis")
    private BigDecimal Budge;

}
