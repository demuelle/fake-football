package com.demuelle.fake_football.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OutputConference {
    private Integer id;
    private String name;
    private List<OutputDivision> divisions;
}
