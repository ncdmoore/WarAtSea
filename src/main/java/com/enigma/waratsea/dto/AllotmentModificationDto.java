package com.enigma.waratsea.dto;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.model.squadron.AllotmentType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class AllotmentModificationDto {
  private final Id allotmentId;
  private final Scenario scenario;
  private final AllotmentType type;
  private final int dice;
  private final int factor;
}
