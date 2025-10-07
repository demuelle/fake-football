package com.demuelle.fake_football.service;

import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Conference getConference(String name) {
        return conferenceRepository.findByNameIgnoreCase(name);
    }

    public Division getDivision(String conferenceName, String divisionName) {
        return divisionRepository.findByCustomQueryConferenceAndDivision(conferenceName.toLowerCase(), divisionName.toLowerCase());
    }
}
