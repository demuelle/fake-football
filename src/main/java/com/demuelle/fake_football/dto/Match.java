package com.demuelle.fake_football.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Match {
    @Id
    @GeneratedValue
    private Integer id;
    private int rolls;
    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "visiting_team_id")
    private Team visitingTeam;
    private boolean neutralSite;
    private Double homeTeamScore;
    private Double visitingTeamScore;
    private String actualOrPredicted;
    private Integer week;
    private LocalDateTime kickoffDateTime;


    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", rolls=" + rolls +
                ", homeTeam=" + homeTeam +
                ", visitingTeam=" + visitingTeam +
                ", neutralSite=" + neutralSite +
                ", homeTeamScore=" + String.format("%.2f", homeTeamScore) +
                ", visitingTeamScore=" + String.format("%.2f", visitingTeamScore) +
                ", actualOrPredicted=" + actualOrPredicted +
                ", week=" + week +
                ", kickoffDateTime=" + formatter.format(kickoffDateTime) +
                '}';
    }
}
