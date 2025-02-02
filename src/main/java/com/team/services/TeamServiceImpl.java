package com.team.services;

import com.team.Exceptions.RessourceNotFound;
import com.team.Repository.PlayerRepository;
import com.team.Repository.TeamRepository;
import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.mappers.TeamMapper;
import com.team.models.Team;
import com.team.models.Tri;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.*;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

   final private TeamRepository teamRepository;
   final private PlayerRepository playerRepository;


    @Override
    public TeamResponseDto Create(TeamRequestDto teamRequestDto) {
        Team team = TeamMapper.INSTANCE.teamRequestDtoToTeam(teamRequestDto);
        var playersFromTeamRequestDto = Optional.ofNullable(teamRequestDto.getPlayerIds()).orElse(Collections.emptySet());
        if (!playersFromTeamRequestDto.isEmpty()) {
            int count = this.playerRepository.countPlayersWithIds(playersFromTeamRequestDto);
            if (playersFromTeamRequestDto.size() != count) {
                throw new RessourceNotFound( "Les joueurs n'existe pas dans la base", "409");
            }



        }
        return TeamMapper.INSTANCE.teamToTeamResponseDto(this.teamRepository.save(team));
    }

    @Override
    public Page<TeamResponseDto> listOFTeams(int pageNo, int pageSize,Tri sort, String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection,sort.name()));
        return switch (sort) {
            case name ->
                    TeamMapper.INSTANCE.listeOfTeamsWithTheirPlayersToResponseDto(this.teamRepository.listeOfTeamsWithTheirPlayersOrderByName(pageable));
             case acronym ->
                    TeamMapper.INSTANCE.listeOfTeamsWithTheirPlayersToResponseDto(this.teamRepository.listeOfTeamsWithTheirPlayersOrderByAcronym(pageable));
            case budget ->
                    TeamMapper.INSTANCE.listeOfTeamsWithTheirPlayersToResponseDto(this.teamRepository.listeOfTeamsWithTheirPlayersOrderByBudget(pageable));

        };
    }


}
