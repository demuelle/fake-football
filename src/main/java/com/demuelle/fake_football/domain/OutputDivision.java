package com.demuelle.fake_football.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OutputDivision {
    private Integer id;
    private String name;
    private String conference;
    private List<OutputTeam> teams;
}
