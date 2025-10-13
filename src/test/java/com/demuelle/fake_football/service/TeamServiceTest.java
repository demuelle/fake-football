package com.demuelle.fake_football.service;

import com.demuelle.fake_football.domain.OutputConference;
import com.demuelle.fake_football.domain.OutputDivision;
import com.demuelle.fake_football.domain.OutputTeam;
import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.exception.BadNicknameException;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.viewmodel.TeamWithoutMatches;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
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
    private Conference exampleConference;
    private Division exampleDivision;
    private Team team1;
    private Team team2;
    private Team team3;
    private Team team4;
    private OutputTeam outputTeam1;
    private OutputTeam outputTeam2;
    private OutputTeam outputTeam3;
    private OutputDivision outputDivision1;

    @BeforeEach
    public void setup() {
        setupDivisionRepository();
        setupConferenceRepository();
        setupTeamRepository();
        setupSomeOutputs();

        teamService = new TeamService(divisionRepository, conferenceRepository, teamRepository);
    }

    public void setupSomeOutputs() {
        outputTeam1 = new OutputTeam(111, "city1", "nickname1");
        outputTeam2 = new OutputTeam(222, "city2", "nickname2");
        outputTeam3 = new OutputTeam(333, "city3", "nickname3");

        outputDivision1 = new OutputDivision(11, "division11", "FFC", Arrays.asList(outputTeam1, outputTeam2, outputTeam3));
    }

    public void setupDivisionRepository() {
        return;
    }

    public void setupConferenceRepository() {
        this.exampleConference = Conference.builder()
                .id(7)
                .name("FFC")
                .build();

        team1 = new Team("city1", "nickname1", null);
        team1.setId(111);
        team2 = new Team("city2", "nickname2", null);
        team2.setId(222);
        team3 = new Team("city3", "nickname3", null);
        team3.setId(333);
        team4 = new Team("city4", "nickname4", null);
        team4.setId(444);
        Team team5 = new Team("city5", "nickname5", null);
        team5.setId(555);

        exampleDivision = new Division(11, "division11", exampleConference, Arrays.asList(team1, team2, team3));
        Division division2 = new Division(22, "division22", exampleConference, Arrays.asList(team4, team5));
        team1.set_division(exampleDivision);
        team2.set_division(exampleDivision);
        team3.set_division(exampleDivision);
        team4.set_division(division2);
        team5.set_division(division2);
        exampleConference.set_divisions(Arrays.asList(exampleDivision, division2));

        when(conferenceRepository.findByNameIgnoreCase("FFC")).thenReturn(exampleConference);
    }

    private void setupTeamRepository() {
        when(teamRepository.findByNickname("goodnickname")).thenReturn(Arrays.asList(team1));
        when(teamRepository.findByNickname("badnickname")).thenReturn(new ArrayList<>());
    }


    @Test
    public void shouldBuildOutputConferenceAndOutputDivisions() throws Exception {

        OutputTeam team4 = new OutputTeam(444, "city4", "nickname4");
        OutputTeam team5 = new OutputTeam(555, "city5", "nickname5");

        OutputDivision outputDivision2 = new OutputDivision(22, "division22", "FFC", Arrays.asList(team4, team5));

        OutputConference expectedValue = new OutputConference(7, "FFC", Arrays.asList(outputDivision1, outputDivision2));

        OutputConference actualValue = teamService.getOutputConference("FFC");

        assertEquals(expectedValue, actualValue);
    }


    @Test
    public void shouldFindTeamByNickname() {
        Team expectedTeam = team1;
        Team actualTeam = teamService.findByNickname("goodnickname");

        assertEquals(expectedTeam, actualTeam);
    }


    @Test
    public void shouldThrowExceptionWithBadNickname() {
        assertThrows(BadNicknameException.class, () -> {
            teamService.findByNickname("badnicknamee");
        });
    }


}