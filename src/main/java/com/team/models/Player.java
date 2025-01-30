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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private  String position;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;



}
