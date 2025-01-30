package com.team.unittest;

import com.team.dto.PlayerDto;
import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.mappers.TeamMapper;
import com.team.models.Player;
import com.team.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TeamMapperTest {

    private TeamMapper teamMapper = Mappers.getMapper(TeamMapper.class);


    @BeforeEach
      public void init(){

      }

    @Test
    void MapTeamRequestToTeam(){
      TeamRequestDto teamRequestDto =new TeamRequestDto();
        teamRequestDto.setName("FC BARCELONE");
        teamRequestDto.setAcronym("FCB");
        teamRequestDto.setBudge(new BigDecimal("15000000000.00"));
        teamRequestDto.setPlayerIds(Set.of(1L, 2L));

        Team team= teamMapper.teamRequestDtoToTeam(teamRequestDto);

        assertThat(team).isNotNull();
        assertThat(team.getAcronym()).isEqualTo("FCB");
        assertThat(team.getPlayers().size()).isEqualTo(2);
        assertThat(team.getPlayers().stream().anyMatch(player -> player.getId() == 1L)).isTrue();
        assertThat(team.getPlayers().stream().anyMatch(player -> player.getId() == 2L )).isTrue();


    }


    @Test
     void MapTeamToTeamResponseDto(){
        Team team=new Team();
        team.setName("FC BARCELONE");
        team.setAcronym("FCB");
        team.setBudget(new BigDecimal("15000000000.00"));
        Player player1 =new Player();
        player1.setId(1L);
        player1.setName("Messi");
        player1.setPosition("Attaquant");
        Player player2 =new Player();
        player2.setId(2L);
        player2.setName("Ronaldo");
        player2.setPosition("Attaquant");

        team.setPlayers(Set.of(player1,player2));

       TeamResponseDto teamResponseDto=teamMapper.teamToTeamResponseDto(team);
       assertThat(teamResponseDto).isNotNull();
       assertThat(teamResponseDto.getName()).isEqualTo("FC BARCELONE");
       assertThat(teamResponseDto.getAcronym()).isEqualTo("FCB");
       assertThat(teamResponseDto.getPlayers().size()).isEqualTo(2);

       Set<String>playerDtos=teamResponseDto.getPlayers().stream().map(PlayerDto::getName).collect(Collectors.toSet());
       assertThat(playerDtos.contains(player1.getName())).isTrue();
       assertThat(playerDtos.contains(player2.getName())).isTrue();



    }
}
