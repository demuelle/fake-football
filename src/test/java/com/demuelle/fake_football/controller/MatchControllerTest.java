package com.demuelle.fake_football.controller;

import com.demuelle.fake_football.exception.BadNicknameException;
import com.demuelle.fake_football.service.MatchService;
import com.demuelle.fake_football.viewmodel.RandomMatchesViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MatchController.class)
public class MatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockitoBean
    private MatchService matchService;

    @Test
    public void shouldReturn422OnBadNickname() throws Exception {
        String badNickname = "Chefs";
        String goodNickname = "Chiefs";
        when(matchService.generatePredictions(badNickname, goodNickname, false, "0")).thenThrow(
                new BadNicknameException(badNickname));

        mockMvc.perform(get("/match?visitorNickname=" + badNickname + "&homeNickname=" + goodNickname + "&rolls=0"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnMatchesViewModel() throws Exception {
        String visitorNickname = "nick";
        String homeNickname = "name";

        RandomMatchesViewModel vm = RandomMatchesViewModel.builder().visitorName(visitorNickname).homeName(homeNickname).build();
        String expectedOutput = mapper.writeValueAsString(vm);

        when(matchService.generatePredictions(visitorNickname, homeNickname, false, "0")).thenReturn(
                vm);

        mockMvc.perform(get("/match?visitorNickname=" + visitorNickname + "&homeNickname=" + homeNickname + "&rolls=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutput));
    }
}


/*
@Builder
public class RandomMatchesViewModel {
    private String visitorName;
    private String homeName;
    private MatchViewModel averageMatch;
    private List<MatchViewModel> randomOutcomeMatches = new ArrayList<>();
}

 */