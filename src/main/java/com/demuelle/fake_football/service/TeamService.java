package com.demuelle.fake_football.service;

import com.demuelle.fake_football.domain.OutputConference;
import com.demuelle.fake_football.domain.OutputDivision;
import com.demuelle.fake_football.domain.OutputTeam;
import com.demuelle.fake_football.dto.Conference;
import com.demuelle.fake_football.dto.Division;
import com.demuelle.fake_football.dto.Match;
import com.demuelle.fake_football.dto.Team;
import com.demuelle.fake_football.exception.BadNicknameException;
import com.demuelle.fake_football.exception.NoSuchTeamException;
import com.demuelle.fake_football.repository.ConferenceRepository;
import com.demuelle.fake_football.repository.DivisionRepository;
import com.demuelle.fake_football.repository.TeamRepository;
import com.demuelle.fake_football.utils.MatchUtils;
import com.demuelle.fake_football.utils.TeamUtils;
import com.demuelle.fake_football.viewmodel.TeamWithMatches;
import com.demuelle.fake_football.viewmodel.TeamWithoutMatches;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final DivisionRepository divisionRepository;
    private final ConferenceRepository conferenceRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(DivisionRepository divisionRepository, ConferenceRepository conferenceRepository, TeamRepository teamRepository) {
        this.divisionRepository = divisionRepository;
        this.conferenceRepository = conferenceRepository;
        this.teamRepository = teamRepository;

        // seedData();
    }


    @Transactional
    public void seedData() {
        Conference nfc = conferenceRepository.save(new Conference("NFC"));
        Conference afc = conferenceRepository.save(new Conference("AFC"));

        Division nfcNorth = divisionRepository.save(new Division("North", nfc));
        Division nfcSouth = divisionRepository.save(new Division("South", nfc));
        Division nfcEast = divisionRepository.save(new Division("East", nfc));
        Division nfcWest = divisionRepository.save(new Division("West", nfc));
        Division afcNorth = divisionRepository.save(new Division("North", afc));
        Division afcSouth = divisionRepository.save(new Division("South", afc));
        Division afcEast = divisionRepository.save(new Division("East", afc));
        Division afcWest = divisionRepository.save(new Division("West", afc));

        teamRepository.save(new Team("Buffalo", "Bills", afcEast));
        teamRepository.save(new Team("New England", "Patriots",afcEast));
        teamRepository.save(new Team("Miami", "Dolphins", afcEast));
        teamRepository.save(new Team("New York", "Jets", afcEast));
        teamRepository.save(new Team("Los Angeles", "Chargers", afcWest));
        teamRepository.save(new Team("Kansas City", "Chiefs", afcWest));
        teamRepository.save(new Team("Denver", "Broncos", afcWest));
        teamRepository.save(new Team("Las Vegas", "Raiders", afcWest));
        teamRepository.save(new Team("Pittsburgh", "Steelers", afcNorth));
        teamRepository.save(new Team("Cincinnati", "Bengals", afcNorth));
        teamRepository.save(new Team("Baltimore", "Ravens", afcNorth));
        teamRepository.save(new Team("Cleveland", "Browns", afcNorth));
        teamRepository.save(new Team("Indianapolis", "Colts", afcSouth));
        teamRepository.save(new Team("Jacksonville", "Jaguars", afcSouth));
        teamRepository.save(new Team("Houston", "Texans", afcSouth));
        teamRepository.save(new Team("Tennessee", "Titans", afcSouth));
        teamRepository.save(new Team("Philadelphia", "Eagles", nfcEast));
        teamRepository.save(new Team("Washington", "Commanders", nfcEast));
        teamRepository.save(new Team("Dallas", "Cowboys", nfcEast));
        teamRepository.save(new Team("New York", "Giants", nfcEast));
        teamRepository.save(new Team("San Francisco", "49ers", nfcWest));
        teamRepository.save(new Team("Seattle", "Seahawks", nfcWest));
        teamRepository.save(new Team("Los Angeles", "Rams", nfcWest));
        teamRepository.save(new Team("Arizona", "Cardinals", nfcWest));
        teamRepository.save(new Team("Detroit", "Lions", nfcNorth));
        teamRepository.save(new Team("Green Bay", "Packers", nfcNorth));
        teamRepository.save(new Team("Minnesota", "Vikings", nfcNorth));
        teamRepository.save(new Team("Chicago", "Bears", nfcNorth));
        teamRepository.save(new Team("Tampa Bay", "Buccaneers", nfcSouth));
        teamRepository.save(new Team("Atlanta", "Falcons", nfcSouth));
        teamRepository.save(new Team("Carolina", "Panthers", nfcSouth));
        teamRepository.save(new Team("New Orleans", "Saints", nfcSouth));

//        Match match = Match.builder()
//                .homeTeam(eagles)
//                .visitingTeam(cowboys)
//                .homeTeamScore(24.)
//                .visitingTeamScore(20.)
//                .actualOrPredicted("A")
//                .week(1)
//                .kickoffDateTime(LocalDateTime.of(2025, 9, 4, 19, 20))
//                .build();
//        matchRepository.save(match);
//        List<Team> teamList = retrieveAllTeams();
//        for (Team t: teamList) System.out.println(t);

//        System.out.println(this.getAverageMatch(teams.get("Chicago_Bears"), teams.get("Washington_Commanders"), false));

//        System.out.println("\n==== 3 tosses ====");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(this.getRandomMatch("Chicago_Bears", "Washington_Commanders", false, 3));
//        }
//        System.out.println("\n==== 7 tosses ====");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(this.getRandomMatch("Chicago_Bears", "Washington_Commanders", false, 7));
//        }
//        System.out.println("\n==== 100 tosses ====");
//        for (int i = 0; i < 1; i++) {
//            System.out.println(this.getRandomMatch("Chicago_Bears", "Washington_Commanders", false, 100));
//        }
//        System.out.println("\n==== 10000 tosses ====");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(this.getRandomMatch(teams.get("Chicago_Bears"), teams.get("Washington_Commanders"), false, 10000));
//        }
    }

    public Conference getConference(String name) {
        return conferenceRepository.findByNameIgnoreCase(name);
    }

    public OutputConference getOutputConference(String name) {
        Conference conference = conferenceRepository.findByNameIgnoreCase(name);
        return OutputConference.builder()
                .id(conference.getId())
                .name(conference.getName())
                .divisions(conference.getDivisions().stream()
                        .map(TeamService::buildOutputDivision)
                        .collect(Collectors.toList()))
                .build();
    }

    public static OutputDivision buildOutputDivision(Division division) {
        return OutputDivision.builder()
                .id(division.getId())
                .name(division.getName())
                .conference(division.getConference())
                .teams(division.getTeams().stream()
                        .map(t -> new OutputTeam(t.getId(), t.getCity(), t.getNickname())).toList())
                .build();
    }

    public OutputDivision getOutputDivision(String conferenceName, String divisionName) {
        Division division = divisionRepository.findByCustomQueryConferenceAndDivision(conferenceName.toLowerCase(), divisionName.toLowerCase());
        return buildOutputDivision(division);
    }

    public List<TeamWithoutMatches> retrieveAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(TeamUtils::convertTeamToTeamWithoutMatches).collect(Collectors.toList());
    }

    public TeamWithMatches retrieveTeam(Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new NoSuchTeamException(id));
        return TeamUtils.convertTeamToTeamWithMatches(team);
    }

    public TeamWithMatches findTeamWithMatchesByNickname(String nickname) {
        return TeamUtils.convertTeamToTeamWithMatches(this.findByNickname(nickname));
    }

    public Team findByNickname(String nickname) {
        try {
            return teamRepository.findByNickname(nickname).get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new BadNicknameException(nickname);
        }
    }
}
