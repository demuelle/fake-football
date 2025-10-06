package com.demuelle.fake_football.service;

import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.exception.BadNicknameException;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.utils.MapUtils;
import com.demuelle.fake_football.utils.ParseUtils;
import com.demuelle.fake_football.viewmodel.MatchViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class RandomMatchService {
    private Random random = new Random();

//    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    public RandomMatchService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        seedTeams();
    }

    public void seedTeams() {
        addTeam("Buffalo", "Bills", 133, 90, 4);
        addTeam("New England", "Patriots", 102, 81, 4);
        addTeam("Miami", "Dolphins", 83, 118, 4);
        addTeam("New York", "Jets", 90, 120, 4);
        addTeam("Los Angeles", "Chargers", 88, 71, 4);
        addTeam("Kansas City", "Chiefs", 97, 76, 4);
        addTeam("Denver", "Broncos", 96, 67, 4);
        addTeam("Las Vegas", "Raiders", 77, 99, 4);
        addTeam("Pittsburgh", "Steelers", 96, 98, 4);
        addTeam("Cincinnati", "Bengals", 61, 119, 4);
        addTeam("Baltimore", "Ravens", 131, 133, 4);
        addTeam("Cleveland", "Browns", 56, 102, 4);
        addTeam("Indianapolis", "Colts", 123, 83, 4);
        addTeam("Jacksonville", "Jaguars", 96, 72, 4);
        addTeam("Houston", "Texans", 64, 51, 4);
        addTeam("Tennessee", "Titans", 51, 120, 4);
        addTeam("Philadelphia", "Eagles", 108, 88, 4);
        addTeam("Washington", "Commanders", 107, 91, 4);
        addTeam("Dallas", "Cowboys", 114, 132, 4);
        addTeam("New York", "Giants", 73, 101, 4);
        addTeam("San Francisco", "49ers", 80, 75, 4);
        addTeam("Seattle", "Seahawks", 111, 67, 4);
        addTeam("Los Angeles", "Rams", 100, 81, 4);
        addTeam("Arizona", "Cardinals", 82, 74, 4);
        addTeam("Detroit", "Lions", 137, 88, 4);
        addTeam("Green Bay", "Packers", 104, 84, 4);
        addTeam("Minnesota", "Vikings", 102, 80, 4);
        addTeam("Chicago", "Bears", 101, 117, 4);
        addTeam("Tampa Bay", "Buccaneers", 97, 97, 4);
        addTeam("Atlanta", "Falcons", 76, 86, 4);
        addTeam("Carolina", "Panthers", 75, 95, 4);
        addTeam("New Orleans", "Saints", 66, 121, 4);

        List<Team> teamList = retrieveAllTeams();
        for (Team t: teamList) System.out.println(t);

//        System.out.println(this.getAverageMatch(teams.get("Chicago_Bears"), teams.get("Washington_Commanders"), false));

//        System.out.println("\n==== 3 tosses ====");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(this.getRandomMatch("Chicago_Bears", "Washington_Commanders", false, 3));
//        }
//        System.out.println("\n==== 7 tosses ====");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(this.getRandomMatch("Chicago_Bears", "Washington_Commanders", false, 7));
//        }
//        System.out.println("\n==== 100 tosses ====");
//        for (int i = 0; i < 1; i++) {
//            System.out.println(this.getRandomMatch("Chicago_Bears", "Washington_Commanders", false, 100));
//        }
//        System.out.println("\n==== 10000 tosses ====");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(this.getRandomMatch(teams.get("Chicago_Bears"), teams.get("Washington_Commanders"), false, 10000));
//        }
    }

    private Team findByNickname(String nickname) {
        try {
            return teamRepository.findByNickname(nickname).get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new BadNicknameException(nickname);
        }
    }

    public MatchViewModel generatePredictions(String visitorId, String homeId, boolean neutralSite, String rolls) {
        MatchViewModel returnVal = new MatchViewModel();
        int[] rollValues = ParseUtils.parseSeriesOrRange(rolls);
        Team visitorTeam = this.findByNickname(visitorId);
        Team homeTeam = this.findByNickname(homeId);
        returnVal.setHomeName(homeTeam.getCity() + " " + homeTeam.getNickname());
        returnVal.setVisitorName(visitorTeam.getCity() + " " + visitorTeam.getNickname());
        returnVal.setAverageMatch(this.getRandomMatch(visitorTeam, homeTeam, neutralSite));
        for (int i: rollValues) {
            returnVal.getRandomOutcomeMatches().add(this.getRandomMatch(visitorTeam, visitorTeam, neutralSite, i));
        }
        return returnVal;
    }

    private void addTeam(String city, String nickname, Integer offense, Integer defense, Integer matchesPlayed) {
        Team team = new Team(city, nickname, offense, defense, matchesPlayed);
        teamRepository.save(team);
    }

    private String generateKey(String part1, String part2) {
        return (part1 + " " + part2).replaceAll(" ", "_");
    }

    private static int compareTeams(Team a, Team b) {
        return a.getCity().compareTo(b.getCity()) == 0 ? a.getNickname().compareTo(b.getNickname()) : a.getCity().compareTo(b.getCity());
    }

    public List<Team> retrieveAllTeams() {
        return teamRepository.findAll();
    }

    private Match getAverageMatch(Team visitorTeam, Team homeTeam, boolean neutralSite) {
        Double homeTeamScore = (homeTeam.getAveragePointsScored() + visitorTeam.getAveragePointsAllowed()) / 2;
        Double visitorTeamScore = (visitorTeam.getAveragePointsScored() + homeTeam.getAveragePointsAllowed()) / 2;
        if (!neutralSite) {
            homeTeamScore += 1.5;
            visitorTeamScore -= 1.5;
        }
        return Match.builder()
                .homeTeamId(homeTeam.getId())
                .visitingTeamId(visitorTeam.getId())
                .neutralSite(neutralSite)
                .homeTeamScore(homeTeamScore)
                .visitingTeamScore(visitorTeamScore)
                .rolls(0)
                .build();

    }

    private Match getRandomMatch(Team visitorTeam, Team homeTeam, boolean neutralSite) {
        return this.getRandomMatch(visitorTeam, homeTeam, neutralSite, 0);
    }

    private Match getRandomMatch(Team visitorTeam, Team homeTeam, int rolls) {
        return this.getRandomMatch(visitorTeam, homeTeam, false, rolls);
    }

    private Match getRandomMatch(Team visitorTeam, Team homeTeam) {
        return this.getRandomMatch(visitorTeam, homeTeam, false, 0);
    }


    private Match getRandomMatch(Team visitorTeam, Team homeTeam, boolean neutralSite, int rolls) {
        if (rolls == 0) return getAverageMatch(visitorTeam, homeTeam, neutralSite);
        Match returnVal = getAverageMatch(visitorTeam, homeTeam,true);
        Double homeScore  = generateRandomNormalScore(returnVal.getHomeTeamScore(), rolls);
        Double visitorScore = generateRandomNormalScore(returnVal.getVisitingTeamScore(), rolls);
        if (!neutralSite) {
            homeScore += 1.5;
            visitorScore -= 1.5;
        }
        returnVal.setNeutralSite(neutralSite);
        returnVal.setHomeTeamScore(homeScore);
        returnVal.setVisitingTeamScore(visitorScore);
        returnVal.setRolls(rolls);

        return returnVal;
    }

    private double generateRandomNormalScore(double median, int tosses) {
        double maxRandom = median * 2/tosses;
        double returnVal = 0;
//        System.out.printf("\ngenerating for %.2f with %d tosses\n", median, tosses);
        for (int i = 0; i < tosses; i++) {
            double thisDouble = random.nextDouble();
            double thisThrow = thisDouble * maxRandom;
            returnVal += random.nextDouble() * maxRandom;
//            System.out.printf("median: %.2f | max_die: %.2f | this_random: %.4f | this_roll: %.2f | total: %.2f\n",
//                    median, maxRandom, thisDouble, thisThrow, returnVal);
        }
        return returnVal;
    }
}
