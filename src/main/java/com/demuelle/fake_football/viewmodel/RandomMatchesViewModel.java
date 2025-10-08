package com.demuelle.fake_football.viewmodel;

import com.demuelle.fake_football.dto.Match;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RandomMatchesViewModel {
    private String visitorName;
    private String homeName;
    private MatchViewModel averageMatch;
    private List<MatchViewModel> randomOutcomeMatches = new ArrayList<>();
}
