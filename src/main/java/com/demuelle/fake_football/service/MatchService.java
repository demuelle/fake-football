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
import com.demuelle.fake_football.viewmodel.TeamWithMatches;
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
    private final MatchRepository matchRepository;
    private final TeamService teamService;

    @Autowired
    public MatchService(MatchRepository matchRepository, TeamService teamService) {
        this.matchRepository = matchRepository;
        this.teamService = teamService;
//        seedData();
    }


    public RandomMatchesViewModel generatePredictions(String visitorId, String homeId, boolean neutralSite, String rolls) {
        int[] rollValues = ParseUtils.parseSeriesOrRange(rolls);
        Team visitorTeam = teamService.findByNickname(visitorId);
        Team homeTeam = teamService.findByNickname(homeId);

        List<MatchViewModel> randomOutcomes = new ArrayList<>();
        for (int i: rollValues) {
            randomOutcomes.add(this.getRandomMatch(visitorTeam, homeTeam, neutralSite, i));
        }
        return RandomMatchesViewModel.builder()
                .visitorName(visitorTeam.getFullName())
                .homeName(homeTeam.getFullName())
                .averageMatch(this.getRandomMatch(visitorTeam, homeTeam, neutralSite))
                .randomOutcomeMatches(randomOutcomes)
                .build();
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
        Team homeTeam = teamService.findByNickname(inputMatch.getHomeTeamNickname());
        Team visitingTeam = teamService.findByNickname(inputMatch.getVisitingTeamNickname());
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
                .visitingTeamScore(Double.parseDouble(matchStrings.get(3)))
                .homeTeamScore(Double.parseDouble(matchStrings.get(4)))
                .week(Integer.parseInt(matchStrings.get(5)))
                .kickoffDateTime(LocalDateTime.parse(matchStrings.get(6), formatter))
                .build();
    }
}
