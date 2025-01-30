package com.team.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto
{

    @Getter
    @Setter
    private String name;
    @Getter @Setter
    private  String position;
}
