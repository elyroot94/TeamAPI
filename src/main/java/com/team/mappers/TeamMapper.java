package com.team.mappers;

import com.team.dto.PlayerDto;
import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.models.Player;
import com.team.models.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamMapper INSTANCE= Mappers.getMapper(TeamMapper.class);

    @Mapping(target = "players",source = "playerIds",qualifiedByName ="mapPlayerIdsToPlayers" )
     Team teamRequestDtoToTeam(TeamRequestDto teamRequestDto);
    @Mapping(target = "players",source = "players",qualifiedByName = "mapPlayersToPlayesDto")
     TeamResponseDto teamToTeamResponseDto(Team team);
     Iterable<TeamResponseDto> teamlisteToTeamResponseDto(List<Team> teams);
    // Page<TeamResponseDto> listeOfTeamsWithTheirPlayersToResponseDto(Page<Team> teams);
    @Named("mapPlayerIdsToPlayers")
    default Set<Player> mapPlayerIdsToPlayers(Set<Long> playerIds) {
        return  playerIds.stream().map(id->{
            Player player=new Player();
            player.setId(id);
            return player;
        }).collect(Collectors.toSet());
    }


    @Named("mapPlayersToPlayesDto")
    default Set<PlayerDto> mapPlayersToPlayerIds(Set<Player> players) {
        return players.stream().map(player -> {
            PlayerDto playerDto=new PlayerDto();
            playerDto.setName(player.getName());
            playerDto.setPosition(player.getPosition());
            return playerDto;
        }).collect(Collectors.toSet());
    }


}
