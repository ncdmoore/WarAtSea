package com.enigma.waratsea.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScenarioException extends RuntimeException {
  public ScenarioException(final String text) {
    super(text);
    log.error(text);
  }
}
