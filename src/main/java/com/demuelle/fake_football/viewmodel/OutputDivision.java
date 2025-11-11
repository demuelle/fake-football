package com.demuelle.fake_football.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OutputDivision {
    private Integer id;
    private String name;
    private String conference;
    private List<OutputTeam> teams;
}
