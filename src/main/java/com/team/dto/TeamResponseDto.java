package com.team.dto;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamResponseDto {
    private Long id;
    private String name;
    private String acronym;
    private Set<PlayerDto> players=new HashSet<>();
    private BigDecimal Budge;



}
