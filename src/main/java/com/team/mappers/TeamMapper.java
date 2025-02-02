package com.team.mappers;

import com.team.dto.PlayerDto;
import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.models.Player;
import com.team.models.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    default Page<TeamResponseDto> listeOfTeamsWithTheirPlayersToResponseDto(Page<Team> teams) {
        return teams.map(this::teamToTeamResponseDto);
    }


    default Team teamRequestDtoToTeam(TeamRequestDto teamRequestDto) {

        return Team.builder()
                .acronym(teamRequestDto.getAcronym())
                .name(teamRequestDto.getName())
                .players(mapPlayerIdsToPlayers(teamRequestDto.getPlayerIds()))
                .build();

    }

    default TeamResponseDto teamToTeamResponseDto(Team team) {
        return TeamResponseDto.builder()
                .acronym(team.getAcronym())
                .name(team.getName())
                .Budge(team.getBudget())
                .players(mapPlayersToPlayerIds(team.getPlayers()))
                .build();
    }

    @Named("mapPlayerIdsToPlayers")
    default Set<Player> mapPlayerIdsToPlayers(Set<Long> playerIds) {
        return playerIds.stream().map(Player::new).collect(Collectors.toSet());
    }


    @Named("mapPlayersToPlayersDto")
    default Set<PlayerDto> mapPlayersToPlayerIds(Set<Player> players) {
        return players.stream().map(player -> {
            PlayerDto playerDto = new PlayerDto();
            playerDto.setName(player.getName());
            playerDto.setPosition(player.getPosition());
            return playerDto;
        }).collect(Collectors.toSet());
    }


}
