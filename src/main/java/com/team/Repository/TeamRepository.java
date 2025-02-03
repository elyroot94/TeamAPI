package com.team.Repository;


import com.team.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la gestion des équipes.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {


    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players ")
    Page<Team> listeOfTeamsWithTheirPlayers(Pageable pageable);

    /**
     * Récupère une liste paginée d'équipes triées par nom.
     *
     * @param pageable Informations de pagination et de tri.
     * @return Une page d'équipes triées par nom.
     */
    @Query("SELECT DISTINCT t FROM Team t  LEFT JOIN FETCH t.players ORDER BY t.name")
    Page<Team> listeOfTeamsWithTheirPlayersOrderByName(Pageable pageable);

    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players ORDER BY t.acronym")
    Page<Team> listeOfTeamsWithTheirPlayersOrderByAcronym(Pageable pageable);

    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players ORDER BY t.budget")
    Page<Team> listeOfTeamsWithTheirPlayersOrderByBudget(Pageable pageable);




}
