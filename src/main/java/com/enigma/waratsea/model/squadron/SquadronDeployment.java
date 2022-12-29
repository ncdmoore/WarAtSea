package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SquadronDeployment {
  private List<Id> airbases;
  private List<Id> squadrons;
}
