package com.demuelle.fake_football.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutputTeam {
    private Integer id;
    private String city;
    private String nickname;
}
