package com.team.unittest;

import com.team.Repository.PlayerRepository;
import com.team.Repository.TeamRepository;
import com.team.dto.PlayerDto;
import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.models.Player;
import com.team.models.Team;
import com.team.models.Tri;
import com.team.services.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {


    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;


    @InjectMocks
    TeamServiceImpl teamService;


    private TeamRequestDto teamRequestDto;
    private Team team;
    private Set<Player> players;
    private Set<PlayerDto> playerDtos;


    @BeforeEach
    void init() {

        teamRepository = Mockito.mock(TeamRepository.class);
        playerRepository = Mockito.mock(PlayerRepository.class);
        teamService = Mockito.mock(TeamServiceImpl.class, withSettings().useConstructor(teamRepository, playerRepository));
        doCallRealMethod().when(teamService).Create(any(TeamRequestDto.class));
        teamRequestDto = new TeamRequestDto();
        teamRequestDto.setName("FC BARCELONE");
        teamRequestDto.setAcronym("FCB");
        teamRequestDto.setBudge(new BigDecimal("15000000000.00"));
        teamRequestDto.setPlayerIds(Set.of(1L, 2L));
        players = Set.of(new Player(1L, "MESSI", "Millieu de terrain", team),
                new Player(2L, "HENRY", "Attaquant", team)
        );
        playerDtos = Set.of(new PlayerDto("MESSI", "Millieu de terrain"),
                new PlayerDto("HENRY", "Attaquant"));
        team = new Team(1L, "FC BARCELONE", "FCB", players, new BigDecimal("15000000000.00"));
    }


    @Test
    void CreateTeamWithPlayersTest() {
        when(playerRepository.countPlayersWithIds(teamRequestDto.getPlayerIds())).thenReturn(players.size());
        Team teamcreated = new Team(1L, "FC BARCELONE", "FCB", players, new BigDecimal("15000000000.00"));
        when(teamRepository.save(any(Team.class))).thenReturn(teamcreated);
        TeamResponseDto responsedto = this.teamService.Create(teamRequestDto);
        assertThat(responsedto).isNotNull();
        assertThat(responsedto.getName()).isEqualTo("FC BARCELONE");
        assertThat(responsedto.getPlayers().size()).isEqualTo(2);
        verify(playerRepository, times(1)).countPlayersWithIds(teamRequestDto.getPlayerIds());
        verify(teamRepository, times(1)).save(any(Team.class));
    }


    @ParameterizedTest
    @ValueSource(strings = {"name", "budget", "acronym"})
    void listOfTeamsBYTriChamps(String tri) {
        Set<Player> players1 = Set.of(
                new Player(1L, "MESSI", "attaquant", null),
                new Player(2L, "RONALDO", "attaquant", null)

        );
        Page<Team> page = new PageImpl<>(Collections.singletonList(new Team(1L, "FC BARCELONE", "FCB", players1, new BigDecimal("15000000000.00"))), PageRequest.of(0, 10), 1);

        switch (tri) {
            case "name":
                when(teamRepository.listeOfTeamsWithTheirPlayersOrderByName(any(Pageable.class))).thenReturn(page);
            case "acronym":
                when(teamRepository.listeOfTeamsWithTheirPlayersOrderByAcronym(any(Pageable.class))).thenReturn(page);
            case "budget":
                when(teamRepository.listeOfTeamsWithTheirPlayersOrderByBudget(any(Pageable.class))).thenReturn(page);
        }
        Page<TeamResponseDto> resultat = teamService.listOFTeams(0, 10, Tri.valueOf(tri), Sort.Direction.ASC.name());
        assertThat(resultat).isNotNull();
        assertThat(resultat.getTotalElements()).isEqualTo(1);
        assertThat(resultat.getTotalPages()).isEqualTo(1);
        assertThat(resultat.getContent().get(0).getName()).isEqualTo("FC BARCELONE");


    }


}
