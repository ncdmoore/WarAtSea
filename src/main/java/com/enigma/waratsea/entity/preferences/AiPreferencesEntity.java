package com.enigma.waratsea.entity.preferences;

import com.enigma.waratsea.ai.strategy.commandStrategy.CommandStrategyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiPreferencesEntity {
  private CommandStrategyType commandStrategy;
}
