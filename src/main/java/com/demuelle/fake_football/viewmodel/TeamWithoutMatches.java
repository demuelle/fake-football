package com.demuelle.fake_football.viewmodel;

import lombok.*;

@Data
@NoArgsConstructor
public class TeamWithoutMatches {
    private Integer id;
    private String city;
    private String nickname;
    private Integer pointsScored;
    private Integer pointsAllowed;
    private OutputDivision division;
    private String homeRecord;
    private String roadRecord;
    private String overallRecord;

    @Builder
    public TeamWithoutMatches(Integer id, String city, String nickname, Integer pointsScored, Integer pointsAllowed, OutputDivision division, String homeRecord, String roadRecord, String overallRecord) {
        this.id = id;
        this.city = city;
        this.nickname = nickname;
        this.pointsScored = pointsScored;
        this.pointsAllowed = pointsAllowed;
        this.division = division;
        this.homeRecord = homeRecord;
        this.roadRecord = roadRecord;
        this.overallRecord = overallRecord;
    }
}
