package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SquadronDeploymentEntity {
  private Id airbase;
  private List<Id> squadrons;
}
