package com.enigma.waratsea.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceException extends WarAtSeaException {
  public ResourceException(final String message) {
    super(message);
    log.error(message);
  }

  public ResourceException(final String message, final Exception e) {
    super(message, e);
    log.error(message);
  }
}
