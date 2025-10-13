package com.demuelle.fake_football.utils;

import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.service.TeamService;
import com.demuelle.fake_football.viewmodel.TeamWithMatches;
import com.demuelle.fake_football.viewmodel.TeamWithoutMatches;

import java.util.stream.Collectors;

import static com.demuelle.fake_football.service.TeamService.buildOutputDivision;

public class TeamUtils {
    public static TeamWithMatches convertTeamToTeamWithMatches(Team team) {
        TeamWithoutMatches twm = TeamUtils.convertTeamToTeamWithoutMatches(team);
        TeamWithMatches returnVal = TeamWithMatches.builder()
                .id(team.getId())
                .city(team.getCity())
                .nickname(team.getNickname())
                ._division(buildOutputDivision(team.get_division()))
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

    public static TeamWithoutMatches convertTeamToTeamWithoutMatches(Team team) {
        TeamWithoutMatches returnVal = TeamWithoutMatches.builder()
                .id(team.getId())
                .city(team.getCity())
                .nickname(team.getNickname())
                .division(buildOutputDivision(team.get_division()))
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

    static String generateRecordString(int wins, int losses, int ties) {
        return wins + "-" + losses + (ties > 0 ? "-" + ties : "");
    }

}
