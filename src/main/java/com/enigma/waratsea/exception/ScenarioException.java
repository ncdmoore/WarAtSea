package com.enigma.waratsea.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScenarioException extends WarAtSeaException {
  public ScenarioException(final String message) {
    super(message);
    log.error(message);
  }
}
