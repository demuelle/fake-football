package com.demuelle.fake_football.dto;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
    

    public Team(String city, String nickname, Integer pointsScored, Integer pointsAllowed, Integer gamesPlayed) {
        this.city = city;
        this.nickname = nickname;
        this.pointsScored = pointsScored;
        this.pointsAllowed = pointsAllowed;
        this.gamesPlayed = gamesPlayed;
    }

    public double getAveragePointsScored() { return (double) this.pointsScored/this.gamesPlayed; }
    public double getAveragePointsAllowed() { return (double) this.pointsAllowed/this.gamesPlayed; }
}
