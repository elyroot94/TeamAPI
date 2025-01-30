package com.team.unittest;
import com.team.Repository.PlayerRepository;
import com.team.Repository.TeamRepository;
import com.team.dto.PlayerDto;
import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.mappers.TeamMapper;
import com.team.models.Player;
import com.team.models.Team;
import com.team.services.TeamService;
import com.team.services.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {


    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private final TeamMapper teamMapper= Mappers.getMapper(TeamMapper.class);

    @InjectMocks
    TeamService teamService = new TeamServiceImpl(teamRepository, playerRepository);


    private TeamRequestDto teamRequestDto;
    private Team team;
    private Set<Player> players;
    private Set<PlayerDto> playerDtos;





    @BeforeEach
    public void init(){
//        teamRepository= Mockito.mock(TeamRepository.class);
//        playerRepository= Mockito.mock(PlayerRepository.class);
//        teamService=Mockito.mock(TeamService.class,withSettings().useConstructor(teamRepository,playerRepository));
        teamRequestDto=new TeamRequestDto();
        teamRequestDto.setName("FC BARCELONE");
        teamRequestDto.setAcronym("FCB");
        teamRequestDto.setBudge(new BigDecimal("15000000000.00"));
        teamRequestDto.setPlayerIds(Set.of(1L, 2L));
        players= Set.of(new Player(1L,"MESSI","Millieu de terrain",null),
                         new Player(2L,"HENRY","Attaquant",null)
                );
        playerDtos=Set.of(new PlayerDto("MESSI","Millieu de terrain"),
                new PlayerDto("HENRY","Attaquant"));

    }


    @Test
    public void CreateTeamWithPlayersTest(){
         when(teamMapper.teamRequestDtoToTeam(teamRequestDto)).thenReturn(team);
        when(playerRepository.findPlayersByIds(teamRequestDto.getPlayerIds())).thenReturn(players);
         Team teamcreated= new Team(1L,"FC BARCELONE","FCB",players,new BigDecimal("15000000000.00"));

         when(teamRepository.save(team)).thenReturn(teamcreated);

        TeamResponseDto teamResponseDto=new TeamResponseDto(1L,"FC BARCELONE","FC",playerDtos,new BigDecimal("15000000000.00"));
        when(teamMapper.teamToTeamResponseDto(teamcreated)).thenReturn(teamResponseDto);

        TeamResponseDto responsedto=this.teamService.Create(teamRequestDto);


        assertThat(responsedto).isNotNull();
        assertThat(responsedto.getName()).isEqualTo("FC BARCELONE");
        assertThat(responsedto.getPlayers().size()).isEqualTo(2);
        verify(playerRepository,times(1)).countPlayersWithIds(teamRequestDto.getPlayerIds());

    }





}
