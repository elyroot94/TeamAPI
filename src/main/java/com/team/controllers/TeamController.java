package team.fr.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.fr.dto.TeamRequestDto;
import team.fr.dto.TeamResponseDto;
import team.fr.models.Player;
import team.fr.models.Team;
import team.fr.services.TeamService;

import java.util.ArrayList;
import java.util.List;

// les joueurs doivent exister dans la base
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDto> CreateTeam(@Valid @RequestBody TeamRequestDto team)
    {




   /*     if (!team.getPlayers().isEmpty())
        {
         if(teamService.arePLayersExisteIndatabase(team.getPlayers())){
            List<Player> players=new ArrayList<>();
             players= teamService.getPlayersWithTeam(team.getPlayers());
             if(!players.isEmpty()){

                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("les players sont deja ratachee a une quipe "+players.toString());
             }else{

               return  new ResponseEntity<>(this.teamService.Create(team),HttpStatus.CREATED);
             }
         }else{
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("les players n'existe pas dans la base de donnees ");
         }

        }else {
            return new  ResponseEntity<>(this.teamService.Create(team),HttpStatus.CREATED);
        }*/


       return new ResponseEntity<>(this.teamService.Create(team),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Iterable<TeamResponseDto>> ListTeams(@RequestParam(defaultValue = "name:asc,acronym:asc,Budge:desc") String[] sort,
                                                               @RequestParam(defaultValue = "0") int pageNo,
                                                               @RequestParam(defaultValue = "10") int pageSize){
        return null;


    }



}
