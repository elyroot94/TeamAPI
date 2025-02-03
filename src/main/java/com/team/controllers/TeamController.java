package com.team.controllers;

import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.models.Tri;
import com.team.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Contrôleur pour la gestion des équipes.
 */


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teams")
@Tag(name = "Team Api", description = "Gestion de l'equipe ")
public class TeamController {


    private final TeamService teamService;


    /**
     * Crée une nouvelle équipe en utilisant les données fournies dans le corps de la requête.
     *
     * @param team Un objet {@link TeamRequestDto} contenant les informations de l'équipe à créer.
     *             Les données sont validées automatiquement grâce à l'annotation {@link Valid}.
     *             Exemple de corps de requête :
     *             {
     *               "name": "FC Barcelone",
     *               "acronym": "FCB",
     *               "budget": 15000000000.00
     *             }
     *
     * @return Une réponse HTTP avec le statut {@link HttpStatus#CREATED} (201) et un corps contenant
     *         l'objet {@link TeamResponseDto} représentant l'équipe créée.
     *
     * @throws org.springframework.web.bind.MethodArgumentNotValidException Si les données de la requête
     *         ne respectent pas les contraintes de validation définies dans {@link TeamRequestDto}.
     *
     * @throws org.springframework.web.server.ResponseStatusException Si une erreur interne survient
     *         lors de la création de l'équipe.
     */
    @PostMapping
    @Operation(summary = "Ajout d'une equipe", description = "Retourne l'equipe creer")
    @ApiResponse(responseCode = "201", description = "equipe creé")
    @ApiResponse(responseCode = "404", description = "si les jouers n'existe pans dans la base")
    public ResponseEntity<TeamResponseDto> CreateTeam(@Parameter(description = "Détails de l'equipe (name,accronym,budget)", required = true) @Valid @RequestBody TeamRequestDto team) {
        return new ResponseEntity<>(this.teamService.Create(team), HttpStatus.CREATED);
    }

    /**
     * Récupère la liste des équipes avec pagination et tri.
     *
     * @param pageNo Numéro de la page (par défaut : 0).
     * @param pageSize Nombre d'éléments par page (par défaut : 10).
     * @param sort  Champ de tri (par défaut : "name").
     * @param direction Direction du tri (ASC ou DESC).
     * @return Une page d'équipes avec ou sans des jouers.
     */

    @GetMapping
    @Operation(summary = "liste d'equipes", description = "renvoie liste d'equipes avec ou sans joueurs")
    @ApiResponse(responseCode = "201", description = "Joueur créé")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Page<TeamResponseDto>> ListTeams( @Parameter(description = "tri") @RequestParam(defaultValue = "name") Tri sort,
                                                            @Parameter(description = "direction tri")  @RequestParam(defaultValue = "ASC") String direction,
                                                            @Parameter(description = "numero de page") @RequestParam(defaultValue = "0") int pageNo,
                                                            @Parameter(description = "taille")  @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(this.teamService.listOFTeams(pageNo, pageSize, sort, direction), HttpStatus.OK);


    }


}
