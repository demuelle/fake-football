package com.demuelle.fake_football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"_conference"})
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference _conference;

    @OneToMany(mappedBy = "_division")
    private List<Team> teams;

    public Division(String name, Conference conference) {
        this.name = name;
        this._conference = conference;
    }

    public String getConference() {
        return this.get_conference().getName();
    }

}
