package com.demuelle.fake_football.viewmodel;

import com.demuelle.fake_football.dto.Match;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MatchViewModel {
    private String visitorName;
    private String homeName;
    private Match averageMatch;
    private List<Match> randomOutcomeMatches = new ArrayList<>();
}
