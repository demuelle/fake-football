package com.demuelle.fake_football.viewmodel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamWithMatches extends TeamWithoutMatches {
    private List<MatchViewModel> homeMatches;
    private List<MatchViewModel> visitingMatches;

    @Builder(builderMethodName = "teamWithMatchesBuilder")
    public TeamWithMatches(Integer id, String city, String nickname, Integer pointsScored, Integer pointsAllowed, OutputDivision division, String homeRecord, String roadRecord, String overallRecord, List<MatchViewModel> homeMatches, List<MatchViewModel> visitingMatches) {
        super(id, city, nickname, pointsScored, pointsAllowed, division, homeRecord, roadRecord, overallRecord);
        this.homeMatches = homeMatches;
        this.visitingMatches = visitingMatches;
    }
}