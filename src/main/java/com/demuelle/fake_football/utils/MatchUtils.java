package com.demuelle.fake_football.utils;

import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.viewmodel.MatchViewModel;

public class MatchUtils {
    public static MatchViewModel convertMatchToViewModel(Match match) {
        return MatchViewModel.builder()
                .id(match.getId())
                .rolls(match.getRolls())
                .homeTeam(match.getHomeTeam().getFullName())
                .visitingTeam(match.getVisitingTeam().getFullName())
                .neutralSite(match.isNeutralSite())
                .homeTeamScore(match.getHomeTeamScore())
                .visitingTeamScore(match.getVisitingTeamScore())
                .actualOrPredicted(match.getActualOrPredicted())
                .week(match.getWeek())
                .kickoffDateTime(match.getKickoffDateTime())
                .build();
    }
}
