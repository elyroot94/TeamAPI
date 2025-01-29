package team.fr.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import team.fr.Exceptions.RessourceNotFound;
import team.fr.Repository.PlayerRepository;
import team.fr.Repository.TeamRepository;
import team.fr.dto.TeamRequestDto;
import team.fr.dto.TeamResponseDto;
import team.fr.mappers.TeamMapper;
import team.fr.models.Player;
import team.fr.models.Team;

import java.util.*;


public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamMapper teamMapper;


    public TeamServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.teamMapper = teamMapper;
    }


    @Override
    public TeamResponseDto Create(TeamRequestDto teamRequestDto) {
        Team team = teamMapper.teamRequestDtoToTeam(teamRequestDto);
        var playersFromTeamRequestDto = Optional.ofNullable(teamRequestDto.getPlayers()).orElse(Collections.emptySet());
        if (!playersFromTeamRequestDto.isEmpty()) {
            Set<Player> players = this.playerRepository.findPlayersByIds(playersFromTeamRequestDto);
            int count = this.playerRepository.countPlayersWithIds(playersFromTeamRequestDto);
            if (playersFromTeamRequestDto.size() != count) {
                throw new RessourceNotFound("Les joueurs n'existe pas dans la base", "409");
            }
            players.forEach(team::addPlayer);


        }
        this.teamRepository.save(team);
        return teamMapper.teamToTeamResponseDto(team);
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
