package com.enigma.waratsea.entity.squadron;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SquadronDeploymentEntity {
  private List<Id> airbases;
  private List<Id> squadrons;
}
