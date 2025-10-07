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
    private List<Division> _divisions;

    public List<String> getDivisions() {
        return this.get_divisions().stream().map(Division::getName).toList();
    }
}
