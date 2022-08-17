package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.SquadronDeploymentType;
import com.enigma.waratsea.model.TurnIndex;
import com.enigma.waratsea.model.WeatherType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Scenario {
    private String name;
    private Integer id;
    private String title;
    private String image;
    private String description;
    private LocalDate date;
    private WeatherType weather;
    private int maxTurns;
    private TurnIndex turnIndex;
    private String map;
    private String objectives;
    private SquadronDeploymentType squadron;
    private boolean minefieldForHumanSide;
    private boolean flotillasForHumanSide;
}
