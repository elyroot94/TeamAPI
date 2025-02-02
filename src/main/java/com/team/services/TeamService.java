package com.team.services;


import com.team.dto.TeamRequestDto;
import com.team.dto.TeamResponseDto;
import com.team.models.Tri;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;




public interface TeamService {

    public TeamResponseDto Create(TeamRequestDto teamRequestDto);
    public  Page<TeamResponseDto> listOFTeams(int pageNo, int pageSize, Tri sort, String direction);
}
