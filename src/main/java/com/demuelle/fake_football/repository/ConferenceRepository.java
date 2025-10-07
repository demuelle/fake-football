package com.demuelle.fake_football.repository;

import com.demuelle.fake_football.dto.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
    Conference findByNameIgnoreCase(String name);
}
