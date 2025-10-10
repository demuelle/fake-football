package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.service.TeamService;
import com.demuelle.fake_football.viewmodel.TeamWithMatches;
import com.demuelle.fake_football.viewmodel.TeamWithoutMatches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService service;

    @GetMapping
    public List<TeamWithoutMatches> getAllTeams() {
        return service.retrieveAllTeams();
    }

    @GetMapping("/{id}")
    public TeamWithMatches getTeamById(@PathVariable Integer id) {
        return service.retrieveTeam(id);
    }
}
