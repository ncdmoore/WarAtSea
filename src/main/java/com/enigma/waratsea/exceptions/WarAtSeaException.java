package com.enigma.waratsea.exceptions;

public abstract class WarAtSeaException extends RuntimeException {
  public WarAtSeaException(final String message) {
    super(message);
  }

  public WarAtSeaException(final String message, final Exception e) {
    super(message, e);
  }
}
