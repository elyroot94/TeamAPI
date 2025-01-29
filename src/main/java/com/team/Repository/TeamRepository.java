package team.fr.Repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.fr.models.Player;
import team.fr.models.Team;

import java.util.List;
import java.util.Set;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {


    @Query("SELECT DISTINCT t FROM Team t INNER JOIN FETCH t.players ORDER BY t.name ASC")
    Page<Team> listeOfTeamsWithTheirPlayers(Pageable pageable);




}
