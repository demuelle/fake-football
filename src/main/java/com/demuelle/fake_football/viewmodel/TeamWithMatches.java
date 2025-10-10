package com.demuelle.fake_football.viewmodel;

import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamWithMatches {
    private Integer id;
    private String city;
    private String nickname;
    private Integer pointsScored;
    private Integer pointsAllowed;
    private Division _division;
    private String homeRecord;
    private String roadRecord;
    private String overallRecord;
    private List<MatchViewModel> homeMatches;
    private List<MatchViewModel> visitingMatches;
}