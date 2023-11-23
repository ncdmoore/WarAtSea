package com.enigma.waratsea.ai.strategy.commandStrategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CommandStrategyType {
  AGGRESSIVE("Aggressive - Computer opponent operates in an offensive manner"),
  CAUTIOUS("Cautious - Computer opponent operates in a defensive manner"),
  BALANCED("Balanced - Computer opponent operates within the principle of calculated risk");

  private final String value;

  public static Stream<CommandStrategyType> stream() {
    return Stream.of(CommandStrategyType.values());
  }
}
