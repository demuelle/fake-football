package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.service.RandomMatchService;
import com.demuelle.fake_football.utils.ParseUtils;
import com.demuelle.fake_football.viewmodel.MatchViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
    @Autowired
    private RandomMatchService service;

    @GetMapping("/match")
    public MatchViewModel getMatchAndPredictions(
            @RequestParam String visitorId,
            @RequestParam String homeId,
            @RequestParam(required=false, defaultValue = "false") boolean neutralSite,
            @RequestParam String rolls) {
        return service.generatePredictions(visitorId, homeId, neutralSite, rolls);
    }

}
