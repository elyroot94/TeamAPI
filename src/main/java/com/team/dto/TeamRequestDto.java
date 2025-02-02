package com.team.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Getter@Setter
    @JsonProperty("playerIds")
    private Set<Long> playerIds=new HashSet<>();
    @NotNull(message = "budget requis")
    private BigDecimal budge;

}
