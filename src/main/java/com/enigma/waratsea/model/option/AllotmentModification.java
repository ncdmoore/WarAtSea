package com.enigma.waratsea.model.option;

import com.enigma.waratsea.model.Scenario;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AllotmentModification implements Option {
  private OptionId id;
  private String text;
  private Scenario scenario;
  private List<SquadronAllotmentModification> modifications;
}
