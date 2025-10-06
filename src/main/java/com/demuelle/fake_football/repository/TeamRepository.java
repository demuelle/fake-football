package com.demuelle.fake_football.repository;

import com.demuelle.fake_football.dto.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    List<Team> findByNickname(String nickname);
}
