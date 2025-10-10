package com.demuelle.fake_football.service;

import com.demuelle.fake_football.domain.OutputConference;
import com.demuelle.fake_football.domain.OutputDivision;
import com.demuelle.fake_football.domain.OutputTeam;
import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TeamServiceTest {
    @MockitoBean
    private DivisionRepository divisionRepository;

    @MockitoBean
    private ConferenceRepository conferenceRepository;

    @MockitoBean
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    public void setup() {
        setupDivisionRepository();
        setupConferenceRepository();
        setupTeamRepository();

        teamService = new TeamService(divisionRepository, conferenceRepository, teamRepository);
    }

    public void setupDivisionRepository() {
        return;
    }

    public void setupConferenceRepository() {
        Conference conference = Conference.builder()
                .id(7)
                .name("FFC")
                .build();

        Team team1 = new Team("city1", "nickname1", 101, 232, 4, null);
        team1.setId(111);
        Team team2 = new Team("city2", "nickname2", 202, 353, 4, null);
        team2.setId(222);
        Team team3 = new Team("city3", "nickname3", 303, 464, 4, null);
        team3.setId(333);
        Team team4 = new Team("city4", "nickname4", 404, 121, 4, null);
        team4.setId(444);
        Team team5 = new Team("city5", "nickname5", 505, 424, 4, null);
        team5.setId(555);

        Division division1 = new Division(11, "division11", conference, Arrays.asList(team1, team2, team3));
        Division division2 = new Division(22, "division22", conference, Arrays.asList(team4, team5));
        team1.set_division(division1);
        team2.set_division(division1);
        team3.set_division(division1);
        team4.set_division(division2);
        team5.set_division(division2);
        conference.set_divisions(Arrays.asList(division1, division2));

        when(conferenceRepository.findByNameIgnoreCase("FFC")).thenReturn(conference);
    }

    public void setupTeamRepository() {
        return;
    }

    @Test
    public void shouldBuildOutputConference() throws Exception {
        OutputTeam team1 = new OutputTeam(111, "city1", "nickname1");
        OutputTeam team2 = new OutputTeam(222, "city2", "nickname2");
        OutputTeam team3 = new OutputTeam(333, "city3", "nickname3");
        OutputTeam team4 = new OutputTeam(444, "city4", "nickname4");
        OutputTeam team5 = new OutputTeam(555, "city5", "nickname5");

        OutputDivision outputDivision1 = new OutputDivision(11, "division11", "FFC", Arrays.asList(team1, team2, team3));
        OutputDivision outputDivision2 = new OutputDivision(22, "division22", "FFC", Arrays.asList(team4, team5));

        OutputConference expectedValue = new OutputConference(7, "FFC", Arrays.asList(outputDivision1, outputDivision2));

        OutputConference actualValue = teamService.getOutputConference("FFC");

        assertEquals(expectedValue, actualValue);
    }
}