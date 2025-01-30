package com.team.services;

import com.team.Exceptions.RessourceNotFound;
import com.team.Repository.PlayerRepository;
import com.team.Repository.TeamRepository;
import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.mappers.TeamMapper;
import com.team.models.Player;
import com.team.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

   final private TeamRepository teamRepository;
   final private PlayerRepository playerRepository;



    public TeamServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;

    }


    @Override
    public TeamResponseDto Create(TeamRequestDto teamRequestDto) {
        Team team = TeamMapper.INSTANCE.teamRequestDtoToTeam(teamRequestDto);
        var playersFromTeamRequestDto = Optional.ofNullable(teamRequestDto.getPlayerIds()).orElse(Collections.emptySet());
        if (!playersFromTeamRequestDto.isEmpty()) {
            Set<Player> players = this.playerRepository.findPlayersByIds(playersFromTeamRequestDto);
            int count = this.playerRepository.countPlayersWithIds(playersFromTeamRequestDto);
            if (playersFromTeamRequestDto.size() != count) {
                throw new RessourceNotFound("Les joueurs n'existe pas dans la base", "409");
            }
            players.forEach(team::addPlayer);


        }
        this.teamRepository.save(team);
        return TeamMapper.INSTANCE.teamToTeamResponseDto(team);
    }


    /*@Override
    public Page<TeamResponseDto> listOFTeams(String[] sort, int pageNo, int pageSize) {
        List<Sort.Order> orders = new ArrayList<>();
        Arrays.stream(sort).map(sortparam -> {
            String[] parts = sortparam.split(":");
            if (parts.length == 2) {
                String field = parts[0];
                String direction = parts[1].toLowerCase();
                return new Sort.Order(Sort.Direction.fromString(direction), field);
            } else {
                throw new IllegalArgumentException("le format de trie est invalide ");
            }
        }).forEach(orders::add);
        Sort sortnow = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortnow);
        this.teamRepository.listeOfTeamsWithTheirPlayers(pageable);
        return teamMapper.listeOfTeamsWithTheirPlayersToResponseDto(this.teamRepository.listeOfTeamsWithTheirPlayers(pageable));


    }
*/

}
