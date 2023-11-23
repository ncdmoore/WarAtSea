package com.enigma.waratsea.ai.strategy.commandStrategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CommandStrategyType {
  AGGRESSIVE("Aggressive"),
  BALANCED("Balanced"),
  CAUTIOUS("Cautious");

  private final String value;

  public static Stream<CommandStrategyType> stream() {
    return Stream.of(CommandStrategyType.values());
  }
}
