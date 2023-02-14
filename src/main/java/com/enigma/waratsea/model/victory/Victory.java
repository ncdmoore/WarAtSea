package com.enigma.waratsea.model.victory;

import com.enigma.waratsea.dto.VictoryDto;

public interface Victory {
  void handleEvent(VictoryDto victoryDto);
}
