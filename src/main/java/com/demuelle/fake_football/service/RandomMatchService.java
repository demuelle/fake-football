package com.demuelle.fake_football.service;

import com.demuelle.fake_football.domain.InputActualMatch;
import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.exception.BadNicknameException;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.MatchRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.utils.MatchUtils;
import com.demuelle.fake_football.utils.ParseUtils;
import com.demuelle.fake_football.viewmodel.MatchViewModel;
import com.demuelle.fake_football.viewmodel.RandomMatchesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomMatchService {
    private final Random random = new Random();

//    @Autowired
    private final TeamRepository teamRepository;
    private final ConferenceRepository conferenceRepository;
    private final DivisionRepository divisionRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public RandomMatchService(TeamRepository teamRepository, ConferenceRepository conferenceRepository, DivisionRepository divisionRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.conferenceRepository = conferenceRepository;
        this.divisionRepository = divisionRepository;
        this.matchRepository = matchRepository;
        
//        seedData();
    }

    public void seedData() {
        Conference nfc = conferenceRepository.save(new Conference("NFC"));
        Conference afc = conferenceRepository.save(new Conference("AFC"));

        Division nfcNorth = divisionRepository.save(new Division("North", nfc));
        Division nfcSouth = divisionRepository.save(new Division("South", nfc));
        Division nfcEast = divisionRepository.save(new Division("East", nfc));
        Division nfcWest = divisionRepository.save(new Division("West", nfc));
        Division afcNorth = divisionRepository.save(new Division("North", afc));
        Division afcSouth = divisionRepository.save(new Division("South", afc));
        Division afcEast = divisionRepository.save(new Division("East", afc));
        Division afcWest = divisionRepository.save(new Division("West", afc));
        
        teamRepository.save(new Team("Buffalo", "Bills", 133, 90, 4, afcEast));
        teamRepository.save(new Team("New England", "Patriots", 102, 81, 4, afcEast));
        teamRepository.save(new Team("Miami", "Dolphins", 83, 118, 4, afcEast));
        teamRepository.save(new Team("New York", "Jets", 90, 120, 4, afcEast));
        teamRepository.save(new Team("Los Angeles", "Chargers", 88, 71, 4, afcWest));
        teamRepository.save(new Team("Kansas City", "Chiefs", 97, 76, 4, afcWest));
        teamRepository.save(new Team("Denver", "Broncos", 96, 67, 4, afcWest));
        teamRepository.save(new Team("Las Vegas", "Raiders", 77, 99, 4, afcWest));
        teamRepository.save(new Team("Pittsburgh", "Steelers", 96, 98, 4, afcNorth));
        teamRepository.save(new Team("Cincinnati", "Bengals", 61, 119, 4, afcNorth));
        teamRepository.save(new Team("Baltimore", "Ravens", 131, 133, 4, afcNorth));
        teamRepository.save(new Team("Cleveland", "Browns", 56, 102, 4, afcNorth));
        teamRepository.save(new Team("Indianapolis", "Colts", 123, 83, 4, afcSouth));
        teamRepository.save(new Team("Jacksonville", "Jaguars", 96, 72, 4, afcSouth));
        teamRepository.save(new Team("Houston", "Texans", 64, 51, 4, afcSouth));
        teamRepository.save(new Team("Tennessee", "Titans", 51, 120, 4, afcSouth));
        teamRepository.save(new Team("Philadelphia", "Eagles", 108, 88, 4, nfcEast));
        teamRepository.save(new Team("Washington", "Commanders", 107, 91, 4, nfcEast));
        teamRepository.save(new Team("Dallas", "Cowboys", 114, 132, 4, nfcEast));
        teamRepository.save(new Team("New York", "Giants", 73, 101, 4, nfcEast));
        teamRepository.save(new Team("San Francisco", "49ers", 80, 75, 4, nfcWest));
        teamRepository.save(new Team("Seattle", "Seahawks", 111, 67, 4, nfcWest));
        teamRepository.save(new Team("Los Angeles", "Rams", 100, 81, 4, nfcWest));
        teamRepository.save(new Team("Arizona", "Cardinals", 82, 74, 4, nfcWest));
        teamRepository.save(new Team("Detroit", "Lions", 137, 88, 4, nfcNorth));
        teamRepository.save(new Team("Green Bay", "Packers", 104, 84, 4, nfcNorth));
        teamRepository.save(new Team("Minnesota", "Vikings", 102, 80, 4, nfcNorth));
        teamRepository.save(new Team("Chicago", "Bears", 101, 117, 4, nfcNorth));
        teamRepository.save(new Team("Tampa Bay", "Buccaneers", 97, 97, 4, nfcSouth));
        teamRepository.save(new Team("Atlanta", "Falcons", 76, 86, 4, nfcSouth));
        teamRepository.save(new Team("Carolina", "Panthers", 75, 95, 4, nfcSouth));
        teamRepository.save(new Team("New Orleans", "Saints", 66, 121, 4, nfcSouth));

//        Match match = Match.builder()
//                .homeTeam(eagles)
//                .visitingTeam(cowboys)
//                .homeTeamScore(24.)
//                .visitingTeamScore(20.)
//                .actualOrPredicted("A")
//                .week(1)
//                .kickoffDateTime(LocalDateTime.of(2025, 9, 4, 19, 20))
//                .build();
//        matchRepository.save(match);
//        List<Team> teamList = retrieveAllTeams();
//        for (Team t: teamList) System.out.println(t);

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

    public RandomMatchesViewModel generatePredictions(String visitorId, String homeId, boolean neutralSite, String rolls) {
        int[] rollValues = ParseUtils.parseSeriesOrRange(rolls);
        Team visitorTeam = this.findByNickname(visitorId);
        Team homeTeam = this.findByNickname(homeId);

        List<MatchViewModel> randomOutcomes = new ArrayList<>();
        for (int i: rollValues) {
            randomOutcomes.add(this.getRandomMatch(visitorTeam, visitorTeam, neutralSite, i));
        }
        return RandomMatchesViewModel.builder()
                .visitorName(visitorTeam.getFullName())
                .homeName(homeTeam.getFullName())
                .averageMatch(this.getRandomMatch(visitorTeam, homeTeam, neutralSite))
                .randomOutcomeMatches(randomOutcomes)
                .build();
    }

    public List<Team> retrieveAllTeams() {
        return teamRepository.findAll();
    }

    private MatchViewModel getAverageMatch(Team visitorTeam, Team homeTeam, boolean neutralSite) {
        double homeTeamScore = (homeTeam.getAveragePointsScored() + visitorTeam.getAveragePointsAllowed()) / 2;
        double visitorTeamScore = (visitorTeam.getAveragePointsScored() + homeTeam.getAveragePointsAllowed()) / 2;
        if (!neutralSite) {
            homeTeamScore += 1.5;
            visitorTeamScore -= 1.5;
        }
        return MatchViewModel.builder()
                .homeTeam(homeTeam.getFullName())
                .visitingTeam(visitorTeam.getFullName())
                .neutralSite(neutralSite)
                .homeTeamScore(homeTeamScore)
                .visitingTeamScore(visitorTeamScore)
                .actualOrPredicted("P")
                .rolls(0)
                .build();

    }

    private MatchViewModel getRandomMatch(Team visitorTeam, Team homeTeam, boolean neutralSite) {
        return this.getRandomMatch(visitorTeam, homeTeam, neutralSite, 0);
    }

    private MatchViewModel getRandomMatch(Team visitorTeam, Team homeTeam, int rolls) {
        return this.getRandomMatch(visitorTeam, homeTeam, false, rolls);
    }

    private MatchViewModel getRandomMatch(Team visitorTeam, Team homeTeam) {
        return this.getRandomMatch(visitorTeam, homeTeam, false, 0);
    }


    private MatchViewModel getRandomMatch(Team visitorTeam, Team homeTeam, boolean neutralSite, int rolls) {
        if (rolls == 0) return getAverageMatch(visitorTeam, homeTeam, neutralSite);
        MatchViewModel returnVal = getAverageMatch(visitorTeam, homeTeam,true);
        double homeScore  = generateRandomNormalScore(returnVal.getHomeTeamScore(), rolls);
        double visitorScore = generateRandomNormalScore(returnVal.getVisitingTeamScore(), rolls);
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
        for (int i = 0; i < tosses; i++) {
            double thisDouble = random.nextDouble();
            returnVal += random.nextDouble() * maxRandom;
        }
        return returnVal;
    }

    public MatchViewModel createMatch(InputActualMatch inputMatch) {
        Team homeTeam = teamRepository.findByNickname(inputMatch.getHomeTeamNickname()).get(0);
        Team visitingTeam = teamRepository.findByNickname(inputMatch.getVisitingTeamNickname()).get(0);
        Match match = Match.builder()
                .homeTeam(homeTeam)
                .visitingTeam(visitingTeam)
                .neutralSite(inputMatch.isNeutralSite())
                .homeTeamScore(inputMatch.getHomeTeamScore())
                .visitingTeamScore(inputMatch.getVisitingTeamScore())
                .actualOrPredicted("A")
                .week(inputMatch.getWeek())
                .kickoffDateTime(inputMatch.getKickoffDateTime())
                .build();
        matchRepository.save(match);
        return MatchUtils.convertMatchToViewModel(match);
    }

    public int bulkCreateMatches(List<List<String>> matches) {
        for (List<String> matchList : matches) {
            this.createMatch(parseMatchFromStringList(matchList));
        }
        return matches.size();
    }

    private static InputActualMatch parseMatchFromStringList(List<String> matchStrings) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
        return InputActualMatch.builder()
                .visitingTeamNickname(matchStrings.get(0))
                .homeTeamNickname(matchStrings.get(1))
                .neutralSite(Boolean.parseBoolean(matchStrings.get(2)))
                .homeTeamScore(Double.parseDouble(matchStrings.get(3)))
                .visitingTeamScore(Double.parseDouble(matchStrings.get(4)))
                .week(Integer.parseInt(matchStrings.get(5)))
                .kickoffDateTime(LocalDateTime.parse(matchStrings.get(6), formatter))
                .build();
    }
}
