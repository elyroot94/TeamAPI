package com.team.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.Repository.PlayerRepository;
import com.team.Repository.TeamRepository;
import com.team.dto.TeamRequestDto;
import com.team.models.Player;
import com.team.models.Team;
import com.team.models.Tri;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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


    @ParameterizedTest
    @ValueSource(strings = {"name", "budget", "acronym"})
    void testListeOfTeamsSortByname(String tri) throws Exception {
        var players1 = Set.of(new Player("Messi", "Attaquant"), new Player("Pique", "Deffenseur"));
        players1 = new HashSet<>(this.playerRepository.saveAll(players1));


        var team1 = Team.builder()
                .acronym("FCB")
                .name("BARCELONNE")
                .budget(BigDecimal.valueOf(12558575))
                .players(players1)
                .build();
        this.teamRepository.save(team1);


        var players2 = Set.of(new Player("Ronaldo", "Attaquant"), new Player("Rodri", "Deffenseur"));
        players2 = new HashSet<>(this.playerRepository.saveAll(players2));

        var team2 = Team.builder()
                .acronym("FCR")
                .name("Real Madrid")
                .budget(BigDecimal.valueOf(12558575))
                .players(players2)
                .build();
        this.teamRepository.save(team2);


        mockMvc.perform(get("/api/teams?sort=" + Tri.valueOf(tri)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(2));
    }


}
