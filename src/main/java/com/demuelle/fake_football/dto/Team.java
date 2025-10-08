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
    @Getter(AccessLevel.NONE)
    private Integer pointsScored;
    @Getter(AccessLevel.NONE)
    private Integer pointsAllowed;
    private Integer gamesPlayed;
    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division _division;
    @OneToMany(mappedBy = "homeTeam")
    List<Match> homeMatches;

    @OneToMany(mappedBy = "visitingTeam")
    List<Match> visitingMatches;

    public Team(String city, String nickname, Integer pointsScored, Integer pointsAllowed, Integer gamesPlayed, Division division) {
        this.city = city;
        this.nickname = nickname;
        this.pointsScored = pointsScored;
        this.pointsAllowed = pointsAllowed;
        this.gamesPlayed = gamesPlayed;
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
    public double getAveragePointsScored() { return (double) this.pointsScored/this.gamesPlayed; }
    public double getAveragePointsAllowed() { return (double) this.pointsAllowed/this.gamesPlayed; }
}
