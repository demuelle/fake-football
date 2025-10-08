package com.demuelle.fake_football.domain;

import com.demuelle.fake_football.dto.Team;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputActualMatch {
    private String homeTeamNickname;
    private String visitingTeamNickname;
    private boolean neutralSite;
    private Double homeTeamScore;
    private Double visitingTeamScore;
    private Integer week;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime kickoffDateTime;
}
