package com.team.Repository;


import com.team.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {


    @Query("SELECT DISTINCT t FROM Team t INNER JOIN FETCH t.players ORDER BY t.name ASC")
    Page<Team> listeOfTeamsWithTheirPlayers(Pageable pageable);


}
