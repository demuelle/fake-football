package com.demuelle.fake_football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"_conference"})
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference _conference;

    public Division(String name, Conference conference) {
        this.name = name;
        this._conference = conference;
    }

    public String getConference() {
        return this.get_conference().getName();
    }

    @OneToMany(mappedBy = "_division")
    private List<Team> teams;
}
