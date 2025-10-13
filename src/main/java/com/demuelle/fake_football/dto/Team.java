package com.demuelle.fake_football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"_division"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String city;
    private String nickname;
    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division _division;
    @OneToMany(mappedBy = "homeTeam")
    private List<Match> homeMatches;

    @OneToMany(mappedBy = "visitingTeam")
    private List<Match> visitingMatches;

    public Team(String city, String nickname, Division division) {
        this.city = city;
        this.nickname = nickname;
        this._division = division;
    }

    public String getFullName() {
        return this.getCity() + " " + this.getNickname();
    }

    public String getDivision() {
        return this.get_division().getName();
    }

    public String getConference() {
        return this.get_division().get_conference().getName();
    }
    public double getAveragePointsScored() { return (double) this.getPointsScored()/this.getGamesPlayed(); }
    public double getAveragePointsAllowed() { return (double) this.getPointsAllowed()/this.getGamesPlayed(); }
    public int getPointsScored() {
        return (int) (this.getHomeMatches()
                .stream()
                .mapToDouble(Match::getHomeTeamScore)
                .sum() +
                this.getVisitingMatches()
                        .stream()
                        .mapToDouble(Match::getVisitingTeamScore)
                        .sum());
    }
    public int getPointsAllowed() {
        return (int) (this.getHomeMatches()
                .stream()
                .mapToDouble(Match::getVisitingTeamScore)
                .sum() +
                this.getVisitingMatches()
                        .stream()
                        .mapToDouble(Match::getHomeTeamScore)
                        .sum());
    }
    public int getGamesPlayed() {
        return this.getHomeMatches().size() + this.getVisitingMatches().size();
    }
}
