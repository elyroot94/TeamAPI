package com.team.controllers;

import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.models.Tri;
import com.team.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/teams")
public class  TeamController {


    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponseDto> CreateTeam(@Valid @RequestBody TeamRequestDto team)
    {
       return new ResponseEntity<>(this.teamService.Create(team),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<TeamResponseDto>> ListTeams(@RequestParam(defaultValue= "name")  Tri sort,
                                                           @RequestParam(defaultValue = "ASC") String direction,
                                                           @RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<>(this.teamService.listOFTeams(pageNo,pageSize,sort,direction), HttpStatus.OK) ;


    }



}
