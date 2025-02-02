package com.team.models;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter

@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
public class Player {


    public Player(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String position;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


}
