package com.team.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams",indexes = {
        @Index(name = "index_name",columnList = "name"),
        @Index(name = "index_acronym", columnList = "acronym"),
        @Index(name = "index_budget",columnList ="budget")
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Getter @Setter

    private String name;
    @Getter @Setter
    private String acronym;
    @Getter@Setter
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Player> players=new HashSet<>();
    @Getter @Setter
    private BigDecimal budget;


}
