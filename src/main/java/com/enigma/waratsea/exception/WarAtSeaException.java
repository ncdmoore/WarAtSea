package com.enigma.waratsea.exception;

public abstract class WarAtSeaException extends RuntimeException {
  public WarAtSeaException(final String message) {
    super(message);
  }

  public WarAtSeaException(final String message, final Exception e) {
    super(message, e);
  }
}
