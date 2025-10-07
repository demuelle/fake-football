package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.repository.TeamRepository;
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
    private TeamRepository repo;

    @GetMapping
    public List<Team> getAllTeams() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Integer id) {
        return repo.findById(id).orElse(null);
    }
}
