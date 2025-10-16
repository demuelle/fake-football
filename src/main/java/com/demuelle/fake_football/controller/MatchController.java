package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.domain.InputActualMatch;
import com.demuelle.fake_football.service.MatchService;
import com.demuelle.fake_football.viewmodel.MatchViewModel;
import com.demuelle.fake_football.viewmodel.RandomMatchesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatchController {
    @Autowired
    private MatchService service;

    @GetMapping("/match")
    public RandomMatchesViewModel getMatchAndPredictions(
            @RequestParam String visitorNickname,
            @RequestParam String homeNickname,
            @RequestParam(required=false, defaultValue = "false") boolean neutralSite,
            @RequestParam String rolls) {
        return service.generatePredictions(visitorNickname, homeNickname, neutralSite, rolls);
    }

    @PostMapping("/match")
    public MatchViewModel addMatch(@RequestBody InputActualMatch match) {
        return service.createMatch(match);
    }

    @PostMapping("/match/batch")
    public int bulkAddMatches(@RequestBody List<List<String>> matches) {
        return service.bulkCreateMatches(matches);
    }

}
