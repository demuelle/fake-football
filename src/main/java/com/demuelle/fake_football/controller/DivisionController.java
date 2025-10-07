package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DivisionController {
    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    DivisionRepository divisionRepository;

    @GetMapping("/conference/{name}")
    public Conference getConference(@PathVariable String name) {
        return conferenceRepository.findByNameIgnoreCase(name);
    }

    @GetMapping("/division/{conferenceName}/{divisionName}")
    public Division getDivision(@PathVariable String conferenceName, @PathVariable String divisionName) {
        return divisionRepository.findByCustomQueryConferenceAndDivision(conferenceName.toLowerCase(), divisionName.toLowerCase());
    }
}
