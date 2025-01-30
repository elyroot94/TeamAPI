package com.team.Repository;

import com.team.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    @Query("SELECT COUNT(p) FROM Player p WHERE p.id IN :ids")
    int countPlayersWithIds(@Param("ids") Set<Long>  ids);

    @Query("SELECT P FROM Player p WHERE p.id IN :ids")
    Set<Player> findPlayersByIds(@Param("ids") Set<Long> ids);
}
