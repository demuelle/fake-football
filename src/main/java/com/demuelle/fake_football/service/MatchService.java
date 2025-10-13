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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MatchService {
    private final Random random = new Random();
//    @Autowired
    private final TeamRepository teamRepository;
    private final ConferenceRepository conferenceRepository;
    private final DivisionRepository divisionRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(TeamRepository teamRepository, ConferenceRepository conferenceRepository, DivisionRepository divisionRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.conferenceRepository = conferenceRepository;
        this.divisionRepository = divisionRepository;
        this.matchRepository = matchRepository;

//        seedData();
    }

    @Transactional
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
        
        teamRepository.save(new Team("Buffalo", "Bills", afcEast));
        teamRepository.save(new Team("New England", "Patriots",afcEast));
        teamRepository.save(new Team("Miami", "Dolphins", afcEast));
        teamRepository.save(new Team("New York", "Jets", afcEast));
        teamRepository.save(new Team("Los Angeles", "Chargers", afcWest));
        teamRepository.save(new Team("Kansas City", "Chiefs", afcWest));
        teamRepository.save(new Team("Denver", "Broncos", afcWest));
        teamRepository.save(new Team("Las Vegas", "Raiders", afcWest));
        teamRepository.save(new Team("Pittsburgh", "Steelers", afcNorth));
        teamRepository.save(new Team("Cincinnati", "Bengals", afcNorth));
        teamRepository.save(new Team("Baltimore", "Ravens", afcNorth));
        teamRepository.save(new Team("Cleveland", "Browns", afcNorth));
        teamRepository.save(new Team("Indianapolis", "Colts", afcSouth));
        teamRepository.save(new Team("Jacksonville", "Jaguars", afcSouth));
        teamRepository.save(new Team("Houston", "Texans", afcSouth));
        teamRepository.save(new Team("Tennessee", "Titans", afcSouth));
        teamRepository.save(new Team("Philadelphia", "Eagles", nfcEast));
        teamRepository.save(new Team("Washington", "Commanders", nfcEast));
        teamRepository.save(new Team("Dallas", "Cowboys", nfcEast));
        teamRepository.save(new Team("New York", "Giants", nfcEast));
        teamRepository.save(new Team("San Francisco", "49ers", nfcWest));
        teamRepository.save(new Team("Seattle", "Seahawks", nfcWest));
        teamRepository.save(new Team("Los Angeles", "Rams", nfcWest));
        teamRepository.save(new Team("Arizona", "Cardinals", nfcWest));
        teamRepository.save(new Team("Detroit", "Lions", nfcNorth));
        teamRepository.save(new Team("Green Bay", "Packers", nfcNorth));
        teamRepository.save(new Team("Minnesota", "Vikings", nfcNorth));
        teamRepository.save(new Team("Chicago", "Bears", nfcNorth));
        teamRepository.save(new Team("Tampa Bay", "Buccaneers", nfcSouth));
        teamRepository.save(new Team("Atlanta", "Falcons", nfcSouth));
        teamRepository.save(new Team("Carolina", "Panthers", nfcSouth));
        teamRepository.save(new Team("New Orleans", "Saints", nfcSouth));

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

    Team findByNickname(String nickname) {
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

    MatchViewModel getAverageMatch(Team visitorTeam, Team homeTeam, boolean neutralSite) {
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
        double homeScore  = generateRandomNormalScore(returnVal.getHomeTeamScore(), rolls, this.random);
        double visitorScore = generateRandomNormalScore(returnVal.getVisitingTeamScore(), rolls, this.random);
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

    double generateRandomNormalScore(double median, int tosses, Random random) {
        double maxRandom = median * 2/tosses; // 30 * 2/4 = 15
        double returnVal = 0;
        for (int i = 0; i < tosses; i++) {
            double thisDouble = random.nextDouble();
            returnVal += random.nextDouble() * maxRandom; // 2.8 *4 * 15
        }
        return returnVal;
    }

    public MatchViewModel createMatch(InputActualMatch inputMatch) {
        Team homeTeam = this.findByNickname(inputMatch.getHomeTeamNickname());
        Team visitingTeam = this.findByNickname(inputMatch.getVisitingTeamNickname());
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
        match = matchRepository.save(match);
        return MatchUtils.convertMatchToViewModel(match);
    }

    @Transactional
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
