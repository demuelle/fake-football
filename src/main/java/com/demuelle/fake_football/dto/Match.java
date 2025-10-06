package com.demuelle.fake_football.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Match {
    private Integer id;
    private int rolls;
    private Integer homeTeamId;
    private Integer visitingTeamId;
    private boolean neutralSite;
    private Double homeTeamScore;
    private Double visitingTeamScore;

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", rolls=" + rolls +
                ", homeTeamId=" + homeTeamId +
                ", visitingTeamId=" + visitingTeamId +
                ", neutralSite=" + neutralSite +
                ", homeTeamScore=" + String.format("%.2f", homeTeamScore) +
                ", visitingTeamScore=" + String.format("%.2f", visitingTeamScore) +
                '}';
    }
}
