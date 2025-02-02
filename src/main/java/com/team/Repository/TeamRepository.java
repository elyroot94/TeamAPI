package com.team.Repository;


import com.team.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {


    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players ")
    Page<Team> listeOfTeamsWithTheirPlayers(Pageable pageable);
    @Query("SELECT DISTINCT t FROM Team t  LEFT JOIN FETCH t.players ORDER BY t.name")
    Page<Team> listeOfTeamsWithTheirPlayersOrderByName(Pageable pageable);

    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players ORDER BY t.acronym")
    Page<Team> listeOfTeamsWithTheirPlayersOrderByAcronym(Pageable pageable);

    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players ORDER BY t.budget")
    Page<Team> listeOfTeamsWithTheirPlayersOrderByBudget(Pageable pageable);




}
