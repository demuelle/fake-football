package com.demuelle.fake_football.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"_divisions"})
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public Conference(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "_conference")
    @Getter(AccessLevel.PRIVATE)
    private List<Division> _divisions;

    public List<Division> getDivisions() {
        return this.get_divisions().stream().map(d -> {
            d.get_conference().set_divisions(new ArrayList<>());
            return d;
        }).toList();
    }
}
