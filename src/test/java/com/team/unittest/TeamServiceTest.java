package team.fr.unittest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import team.fr.Repository.PlayerRepository;
import team.fr.Repository.TeamRepository;
import team.fr.dto.TeamRequestDto;
import team.fr.dto.TeamResponseDto;
import team.fr.mappers.TeamMapper;
import team.fr.models.Player;
import team.fr.models.Team;
import team.fr.services.TeamService;
import team.fr.services.TeamServiceImpl;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {


    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamMapper teamMapper= Mappers.getMapper(TeamMapper.class);

    @InjectMocks
    TeamService teamService;


    private TeamRequestDto teamRequestDto;
    private Team team;
    private Set<Player> players;





    @BeforeEach
    public void init(){
        teamRepository= Mockito.mock(TeamRepository.class);
        playerRepository= Mockito.mock(PlayerRepository.class);
        teamService=Mockito.mock(TeamService.class,withSettings().useConstructor(teamRepository,playerRepository));
        teamRequestDto=new TeamRequestDto();
        teamRequestDto.setName("FC BARCELONE");
        teamRequestDto.setAcronym("FCB");
        teamRequestDto.setBudge(new BigDecimal("15000000000.00"));
        teamRequestDto.setPlayers(Set.of(1L, 2L));
        players= Set.of(new Player(1L,"MESSI","Leo","Millieu de terrain",null),
                         new Player(2L,"HENRY","Thierry","Attaquant",null)
                );
    }


    @Test
    public void CreateTeamWithPlayersTest(){
        when(teamMapper.teamRequestDtoToTeam(teamRequestDto)).thenReturn(team);
        when(playerRepository.findPlayersByIds(teamRequestDto.getPlayers())).thenReturn(players);
         Team teamcreated= new Team(1L,"FC BARCELONE","FCB",players,new BigDecimal("15000000000.00"));

         when(teamRepository.save(team)).thenReturn(teamcreated);

        TeamResponseDto teamResponseDto=new TeamResponseDto(1L,"FC BARCELONE","FC",Set.of(1L,2L),new BigDecimal("15000000000.00"));
        when(teamMapper.teamToTeamResponseDto(teamcreated)).thenReturn(teamResponseDto);

        TeamResponseDto responsedto=this.teamService.Create(teamRequestDto);


        assertThat(responsedto).isNotNull();
        assertThat(responsedto.getName()).isEqualTo("FC BARCELONE");
        assertThat(responsedto.getPlayers().size()).isEqualTo(2);
        verify(playerRepository,times(1)).countPlayersWithIds(teamRequestDto.getPlayers());

    }



}
