package com.demuelle.fake_football.service;

import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.exception.BadNicknameException;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.MatchRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.viewmodel.MatchViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class MatchServiceTest {
    @MockitoBean
    ConferenceRepository conferenceRepository;

    @MockitoBean
    DivisionRepository divisionRepository;

    @MockitoBean
    MatchRepository matchRepository;

    @MockitoBean
    TeamRepository teamRepository;

    Random testRandom;

    private Team team1;
    private Team team2;

    private MatchService matchService;

    @BeforeEach
    public void setup() {
        setupData();

        setupConferenceRepository();
        setupDivisionRepository();
        setupMatchRepository();
        setupTeamRepository();

        matchService = new MatchService(teamRepository, conferenceRepository, divisionRepository, matchRepository);
    }

    @Test
    public void shouldFindTeamByNickname() {
        Team expectedTeam = team1;
        Team actualTeam = matchService.findByNickname("goodnickname");

        assertEquals(expectedTeam, actualTeam);
    }

    @Test
    public void shouldThrowExceptionWithBadNickname() {
        assertThrows(BadNicknameException.class, () -> {
            matchService.findByNickname("badnicknamee");
        });
    }

    @Test
    public void shouldGenerateAverageMatchAtNonNeutralSite() {
        MatchViewModel expectedValue = MatchViewModel.builder()
                .homeTeam("City1 Nickname1")
                .visitingTeam("City2 Nickname2")
                .neutralSite(false)
                .homeTeamScore(26.5)
                .visitingTeamScore(49.75)
                .actualOrPredicted("P")
                .rolls(0)
                .build();

        MatchViewModel actualResult = matchService.getAverageMatch(team2, team1, false);
        assertEquals(expectedValue, actualResult);
    }

    @Test
    public void shouldGenerateAverageMatchAtNeutralSite() {
        MatchViewModel expectedValue = MatchViewModel.builder()
                .homeTeam("City1 Nickname1")
                .visitingTeam("City2 Nickname2")
                .neutralSite(true)
                .homeTeamScore(25.)
                .visitingTeamScore(51.25)
                .actualOrPredicted("P")
                .rolls(0)
                .build();

        MatchViewModel actualResult = matchService.getAverageMatch(team2, team1, true);
        assertEquals(expectedValue, actualResult);
    }

    @Test
    public void shouldGenerateRandomNormalScore() {
        Random testRandom = mock();
        when(testRandom.nextDouble()).thenReturn(2.8);

        double expectedResult = 112.;
        double actualResult = matchService.generateRandomNormalScore(20, 10, testRandom);

        assertEquals(expectedResult, actualResult, .0001);

        expectedResult = 168.;
        actualResult = matchService.generateRandomNormalScore(30, 4, testRandom);
        assertEquals(expectedResult, actualResult, .00001);

    }

    private void setupData() {
        List<Match> team1Home = Arrays.asList(
                Match.builder().homeTeamScore(10.).visitingTeamScore(20.).build(),
                Match.builder().homeTeamScore(20.).visitingTeamScore(40.).build(),
                Match.builder().homeTeamScore(25.).visitingTeamScore(30.).build()
        );
        List<Match> team1Away = Arrays.asList(
                Match.builder().homeTeamScore(54.).visitingTeamScore(27.).build(),
                Match.builder().homeTeamScore(56.).visitingTeamScore(18.).build()
        );
        List<Match> team2Home = Arrays.asList(
                Match.builder().homeTeamScore(35.).visitingTeamScore(28.).build(),
                Match.builder().homeTeamScore(52.).visitingTeamScore(22.).build()
        );
        List<Match> team2Away = Arrays.asList(
                Match.builder().homeTeamScore(31.).visitingTeamScore(80.).build(),
                Match.builder().homeTeamScore(39.).visitingTeamScore(83.).build()
        );

        team1 = new Team(111, "City1", "Nickname1", null, team1Home, team1Away); //100, 200, 5
        team2 = new Team(222, "City2", "Nickname2", null, team2Home, team2Away); //250, 120, 4
    }

    private void setupConferenceRepository() {}

    private void setupDivisionRepository() {}

    private void setupMatchRepository() {}

    private void setupTeamRepository() {
        when(teamRepository.findByNickname("goodnickname")).thenReturn(Arrays.asList(team1));
        when(teamRepository.findByNickname("badnickname")).thenReturn(new ArrayList<>());
    }

}