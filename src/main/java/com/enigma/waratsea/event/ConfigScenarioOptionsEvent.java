package com.enigma.waratsea.event;

import com.enigma.waratsea.model.NationId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ConfigScenarioOptionsEvent implements Event {
  private final Map<NationId, Integer> allotmentModifications;

}
