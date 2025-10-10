package com.demuelle.fake_football.service;

import com.demuelle.fake_football.domain.OutputConference;
import com.demuelle.fake_football.domain.OutputDivision;
import com.demuelle.fake_football.domain.OutputTeam;
import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.exception.NoSuchTeamException;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.utils.MatchUtils;
import com.demuelle.fake_football.viewmodel.TeamWithMatches;
import com.demuelle.fake_football.viewmodel.TeamWithoutMatches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public OutputConference getOutputConference(String name) {
        Conference conference = conferenceRepository.findByNameIgnoreCase(name);
        return OutputConference.builder()
                .id(conference.getId())
                .name(conference.getName())
                .divisions(conference.getDivisions().stream()
                        .map(TeamService::buildOutputDivision)
                        .collect(Collectors.toList()))
                .build();
    }

    private static OutputDivision buildOutputDivision(Division division) {
        return OutputDivision.builder()
                .id(division.getId())
                .name(division.getName())
                .conference(division.getConference())
                .teams(division.getTeams().stream()
                        .map(t -> new OutputTeam(t.getId(), t.getCity(), t.getNickname())).toList())
                .build();
    }

    public OutputDivision getOutputDivision(String conferenceName, String divisionName) {
        Division division = divisionRepository.findByCustomQueryConferenceAndDivision(conferenceName.toLowerCase(), divisionName.toLowerCase());
        return buildOutputDivision(division);
    }

    public List<TeamWithoutMatches> retrieveAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(TeamService::convertTeamToTeamWithoutMatches).collect(Collectors.toList());
    }

    private static TeamWithoutMatches convertTeamToTeamWithoutMatches(Team team) {
        TeamWithoutMatches returnVal = TeamWithoutMatches.builder()
                .id(team.getId())
                .city(team.getCity())
                .nickname(team.getNickname())
                ._division(team.get_division())
                .build();

        int pointsScored = 0;
        int pointsAllowed = 0;
        int homeWins = 0;
        int roadWins = 0;
        int homeLosses = 0;
        int roadLosses = 0;
        int homeTies = 0;
        int roadTies = 0;
        for (Match m : team.getHomeMatches()) {
            pointsScored += m.getHomeTeamScore();
            pointsAllowed += m.getVisitingTeamScore();
            if (m.getHomeTeamScore() > m.getVisitingTeamScore()) {
                homeWins++;
            } else if (m.getHomeTeamScore() < m.getVisitingTeamScore()) {
                homeLosses++;
            } else {
                homeTies++;
            }
        }
        for (Match m : team.getVisitingMatches()) {
            pointsScored += m.getVisitingTeamScore();
            pointsAllowed += m.getHomeTeamScore();
            if (m.getHomeTeamScore() > m.getVisitingTeamScore()) {
                roadLosses++;
            } else if (m.getHomeTeamScore() < m.getVisitingTeamScore()) {
                roadWins++;
            } else {
                homeTies++;
            }
        }
        returnVal.setPointsScored(pointsScored);
        returnVal.setPointsAllowed(pointsAllowed);
        returnVal.setHomeRecord(generateRecordString(homeWins, homeLosses, homeTies));
        returnVal.setRoadRecord(generateRecordString(roadWins, roadLosses, roadTies));
        returnVal.setOverallRecord(generateRecordString(homeWins + roadWins, homeLosses + roadLosses, homeTies + roadTies));
        return returnVal;
    }

    private static String generateRecordString(int wins, int losses, int ties) {
        return wins + "-" + losses + (ties > 0 ? "-" + ties : "");
    }

    public TeamWithMatches retrieveTeam(Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new NoSuchTeamException(id));
        TeamWithoutMatches twm = TeamService.convertTeamToTeamWithoutMatches(team);
        TeamWithMatches returnVal = TeamWithMatches.builder()
                .id(team.getId())
                .city(team.getCity())
                .nickname(team.getNickname())
                ._division(team.get_division())
                .pointsScored(twm.getPointsScored())
                .pointsAllowed(twm.getPointsAllowed())
                .homeRecord(twm.getHomeRecord())
                .roadRecord(twm.getRoadRecord())
                .overallRecord(twm.getOverallRecord())
                .build();
        returnVal.setHomeMatches(team.getHomeMatches().stream()
                .map(MatchUtils::convertMatchToViewModel).collect(Collectors.toList()));
        returnVal.setVisitingMatches(team.getVisitingMatches().stream()
                .map(MatchUtils::convertMatchToViewModel).collect(Collectors.toList()));

        return returnVal;
    }
}
