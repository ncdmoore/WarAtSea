package com.enigma.waratsea;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Set;

/**
 * When this class is created it creates all the classes that inherit the
 * BootStrapped interface.
 */
@Singleton
public class BootStrap {
  @Inject
  @SuppressWarnings("unused")
  public BootStrap(final Set<BootStrapped> bootStrapped) {
  }
}
