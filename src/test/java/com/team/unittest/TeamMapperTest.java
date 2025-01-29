package team.fr.unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.fr.dto.TeamRequestDto;
import team.fr.mappers.TeamMapper;
import team.fr.models.Team;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TeamMapperTest {

    final private TeamMapper teamMapper;

    public TeamMapperTest(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @BeforeEach
      public void init(){

      }

    @Test
    public  void shouldMapTeamRequestToTeam(){
      TeamRequestDto  teamRequestDto =new TeamRequestDto();
        teamRequestDto.setName("FC BARCELONE");
        teamRequestDto.setAcronym("FCB");
        teamRequestDto.setBudge(new BigDecimal("15000000000.00"));
        teamRequestDto.setPlayers(Set.of(1L, 2L));

        Team team= teamMapper.teamRequestDtoToTeam(teamRequestDto);

        assertThat(team).isNotNull();
        assertThat(team.getAcronym()).isEqualTo("FCB");
        assertThat(team.getPlayers().size()).isGreaterThan(0);




    }
}
