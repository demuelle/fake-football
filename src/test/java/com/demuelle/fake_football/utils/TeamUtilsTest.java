package com.demuelle.fake_football.utils;

import com.demuelle.fake_football.domain.OutputDivision;
import com.demuelle.fake_football.domain.OutputTeam;
import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.viewmodel.TeamWithoutMatches;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamUtilsTest {

    @Test
    public void shouldGenerateRecordString() throws Exception {
        String expected = "10-3";
        String actualResult = TeamUtils.generateRecordString(10,3, 0);
        assertEquals(expected, actualResult);

        expected = "1-0-1";
        actualResult = TeamUtils.generateRecordString(1,0,1);
        assertEquals(expected, actualResult);
    }


    @Test
    public void shouldConvertTeamToTeamWithoutMatches() throws Exception {
        OutputTeam outputTeam1 = new OutputTeam(111, "city1", "nickname1");
        OutputTeam outputTeam2 = new OutputTeam(222, "city2", "nickname2");
        OutputTeam outputTeam3 = new OutputTeam(333, "city3", "nickname3");

        OutputDivision outputDivision1 = new OutputDivision(11, "division11", "FFC", Arrays.asList(outputTeam1, outputTeam2, outputTeam3));

        Team team1 = new Team("city1", "nickname1", null);
        team1.setId(111);
        Team team2 = new Team("city2", "nickname2", null);
        team2.setId(222);
        Team team3 = new Team("city3", "nickname3", null);
        team3.setId(333);
        Team team4 = new Team("city4", "nickname4", null);
        team4.setId(444);

        Conference exampleConference = Conference.builder().id(7).name("FFC").build();
        Division exampleDivision = new Division(11, "division11", exampleConference, Arrays.asList(team1, team2, team3));


        //home
        Match match1 = new Match(1, 0, team1, team2, false, 10., 3., "A", 1, null);
        Match match2 = new Match(2, 0, team1, team3, false, 20., 25., "A", 2, null);
        Match match3 = new Match(3, 0, team1, team4, false, 18., 18., "A", 3, null);

        //away
        Match match4 = new Match(4, 0, team2, team1, false, 50., 10., "A", 4, null);
        Match match5 = new Match(5, 0, team3, team1, false, 22., 33., "A", 5, null);

        team1.setHomeMatches(Arrays.asList(match1, match2, match3));
        team1.setVisitingMatches(Arrays.asList(match4, match5));
        team1.set_division(exampleDivision);

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

        TeamWithoutMatches actual = TeamUtils.convertTeamToTeamWithoutMatches(team1);
        assertEquals(expected, actual);
    }
}
