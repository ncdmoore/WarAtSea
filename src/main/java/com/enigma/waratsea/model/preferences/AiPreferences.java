package com.enigma.waratsea.model.preferences;

import com.enigma.waratsea.ai.strategy.commandStrategy.CommandStrategyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiPreferences {
  private CommandStrategyType commandStrategy;
}
