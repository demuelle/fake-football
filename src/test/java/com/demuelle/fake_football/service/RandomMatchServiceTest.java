package com.demuelle.fake_football.service;

import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.exception.BadNicknameException;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.MatchRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RandomMatchServiceTest {
    @MockitoBean
    ConferenceRepository conferenceRepository;

    @MockitoBean
    DivisionRepository divisionRepository;

    @MockitoBean
    MatchRepository matchRepository;

    @MockitoBean
    TeamRepository teamRepository;

    private Team team1;

    private RandomMatchService randomMatchService;

    @BeforeEach
    public void setup() {
        setupData();

        setupConferenceRepository();
        setupDivisionRepository();
        setupMatchRepository();
        setupTeamRepository();

        randomMatchService = new RandomMatchService(teamRepository, conferenceRepository, divisionRepository, matchRepository);
    }

    @Test
    public void shouldFindTeamByNickname() {
        Team expectedTeam = team1;
        Team actualTeam = randomMatchService.findByNickname("goodnickname");

        assertEquals(expectedTeam, actualTeam);
    }

    @Test
    public void shouldThrowExceptionWithBadNickname() {
        assertThrows(BadNicknameException.class, () -> {
            randomMatchService.findByNickname("badnicknamee");
        });
    }

    private void setupData() {
        Team team1 = new Team(124, "City1", "Nickname1", 100, 200, 11, null, null, null);
    }

    private void setupConferenceRepository() {}

    private void setupDivisionRepository() {}

    private void setupMatchRepository() {}

    private void setupTeamRepository() {
        when(teamRepository.findByNickname("goodnickname")).thenReturn(Arrays.asList(team1));
        when(teamRepository.findByNickname("badnickname")).thenReturn(new ArrayList<>());
    }

}