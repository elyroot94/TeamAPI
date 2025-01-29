package team.fr.services;


import org.springframework.stereotype.Service;

import team.fr.dto.TeamRequestDto;
import team.fr.dto.TeamResponseDto;

@Service
public interface TeamService {

    public TeamResponseDto Create(TeamRequestDto teamRequestDto);

  //  public Iterable<TeamResponseDto> listOFTeams(String[] sort, int pageNo, int pageSize);


}
