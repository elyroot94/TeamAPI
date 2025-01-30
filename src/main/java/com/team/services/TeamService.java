package com.team.services;


import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import org.springframework.stereotype.Service;



@Service
public interface TeamService {

    public TeamResponseDto Create(TeamRequestDto teamRequestDto);

  //  public Iterable<TeamResponseDto> listOFTeams(String[] sort, int pageNo, int pageSize);


}
