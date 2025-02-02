package com.team.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.Repository.PlayerRepository;
import com.team.Repository.TeamRepository;
import com.team.controllers.TeamController;
import com.team.dto.TeamRequestDto;
import com.team.models.Player;
import com.team.models.Team;
import com.team.models.Tri;
import com.team.services.TeamService;
import com.team.services.TeamServiceImpl;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeamControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    Long playeraId;
    Long playerbId;


    @BeforeEach
    public void setUp() {
        playerRepository.deleteAll();
        teamRepository.deleteAll();
        Player playera = new Player();
        playera.setPosition("Attaquant");
        playera.setName("Leo Messi");
        playera = playerRepository.save(playera);
        System.out.println(playera);
        Player playerb = new Player();
        playerb.setPosition("Defenseur");
        playerb.setName("Carlos Puyol");
        playerb = playerRepository.save(playerb);
        playeraId = playera.getId();
        playerbId = playerb.getId();


    }


    @Test
    void createTeamwithoutRequiredFields() throws Exception {
        TeamRequestDto teamRequestDto = new TeamRequestDto();
        teamRequestDto.setName("BARCELONE");
        teamRequestDto.setAcronym("FCB");
        teamRequestDto.setBudge(BigDecimal.valueOf(12558575));
        teamRequestDto.setPlayerIds(Set.of(playeraId, playerbId));
        System.out.println(teamRequestDto.getPlayerIds());

        mockMvc.perform(post("/api/teams").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamRequestDto)))
                .andExpect(status().isCreated());

    }

    @Test
    void createValidion() throws Exception {
        TeamRequestDto teamRequestDto = new TeamRequestDto();
        mockMvc.perform(post("/api/teams").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamRequestDto)))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.name").value("nom requis"))
                .andExpect(jsonPath("$.acronym").value("acronym requis"))
                .andExpect(jsonPath("$.budge").value("budget requis"));

    }


    @Test
    void testListeOfTeamsSortByname() throws Exception {
        var players1 = Set.of(new Player(1L
                , "Messi", "Attaquant", null), new Player(2L, "Pique", "Deffenseur", null));
        players1 = new HashSet<>(this.playerRepository.saveAll(players1));
        var team1 = Team.builder()
                .acronym("FCB")
                .name("BARCELONNE")
                .budget(BigDecimal.valueOf(12558575))
                .players(players1).build();

        this.teamRepository.save(team1);

        var players2 = Set.of(new Player(1L
                , "Ronaldo", "Attaquant", null), new Player(2L, "Rodri", "Deffenseur", null));
        players2 = new HashSet<>(this.playerRepository.saveAll(players1));
        var team2 = Team.builder()
                .acronym("FCR")
                .name("Real Madrid")
                .budget(BigDecimal.valueOf(12558575))
                .players(players2).build();
        this.teamRepository.save(team2);

         mockMvc.perform(get("/api/teams?sort=" + Tri.valueOf("name")))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content.size()").value(2));


    }


}
