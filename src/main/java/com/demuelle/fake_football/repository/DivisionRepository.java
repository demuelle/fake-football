package com.demuelle.fake_football.repository;

import com.demuelle.fake_football.dto.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
    @Query("select d from Division d where lower(d.name) = ?2 and lower(_conference.name) = ?1")
    Division findByCustomQueryConferenceAndDivision(String conferenceName, String divisionName);
}
