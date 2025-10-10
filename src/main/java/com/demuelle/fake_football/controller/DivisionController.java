package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.domain.OutputConference;
import com.demuelle.fake_football.domain.OutputDivision;
import com.demuelle.fake_football.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DivisionController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/conference/{name}")
    public OutputConference getConference(@PathVariable String name) {
        return teamService.getOutputConference(name);
    }

    @GetMapping("/division/{conferenceName}/{divisionName}")
    public OutputDivision getDivision(@PathVariable String conferenceName, @PathVariable String divisionName) {
        return teamService.getOutputDivision(conferenceName, divisionName);
    }
}
