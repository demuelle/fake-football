package com.demuelle.fake_football.viewmodel;

import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamWithoutMatches {
    private Integer id;
    private String city;
    private String nickname;
    private Integer pointsScored;
    private Integer pointsAllowed;
    private Division _division;
    private String homeRecord;
    private String roadRecord;
    private String overallRecord;
}
