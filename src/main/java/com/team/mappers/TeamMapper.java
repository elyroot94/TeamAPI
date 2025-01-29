package team.fr.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import team.fr.dto.TeamRequestDto;
import team.fr.dto.TeamResponseDto;
import team.fr.models.Team;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamMapper INSTANCE= Mappers.getMapper(TeamMapper.class);
     Team teamRequestDtoToTeam(TeamRequestDto teamRequestDto);
     TeamResponseDto teamToTeamResponseDto(Team team);
     Iterable<TeamResponseDto> teamlisteToTeamResponseDto(List<Team> teams);
     Page<TeamResponseDto> listeOfTeamsWithTheirPlayersToResponseDto(Page<Team> teams);



}
