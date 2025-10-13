package com.demuelle.fake_football.service;

import com.demuelle.fake_football.domain.OutputConference;
import com.demuelle.fake_football.domain.OutputDivision;
import com.demuelle.fake_football.domain.OutputTeam;
import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.viewmodel.TeamWithoutMatches;
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

    public void setupTeamRepository() {
        return;
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
    public void shouldGenerateRecordString() throws Exception {
        String expected = "10-3";
        String actualResult = TeamService.generateRecordString(10,3, 0);
        assertEquals(expected, actualResult);

        expected = "1-0-1";
        actualResult = TeamService.generateRecordString(1,0,1);
        assertEquals(expected, actualResult);
    }

    @Test
    public void shouldConvertTeamToTeamWithoutMatches() throws Exception {
        //home
        Match match1 = new Match(1, 0, team1, team2, false, 10., 3., "A", 1, null);
        Match match2 = new Match(2, 0, team1, team3, false, 20., 25., "A", 2, null);
        Match match3 = new Match(3, 0, team1, team4, false, 18., 18., "A", 3, null);

        //away
        Match match4 = new Match(4, 0, team2, team1, false, 50., 10., "A", 4, null);
        Match match5 = new Match(5, 0, team3, team1, false, 22., 33., "A", 5, null);

        team1.setHomeMatches(Arrays.asList(match1, match2, match3));
        team1.setVisitingMatches(Arrays.asList(match4, match5));

        TeamWithoutMatches expected = TeamWithoutMatches.builder()
                .id(111)
                .city("city1")
                .nickname("nickname1")
                .pointsScored(91)
                .pointsAllowed(118)
                .division(outputDivision1)
                .homeRecord("1-1-1")
                .roadRecord("1-1")
                .overallRecord("2-2-1")
                .build();

        TeamWithoutMatches actual = TeamService.convertTeamToTeamWithoutMatches(team1);
        assertEquals(expected, actual);
    }

}