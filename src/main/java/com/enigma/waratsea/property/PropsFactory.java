package com.enigma.waratsea.property;

/**
 * Factory for creating property wrappers.
 */
public interface PropsFactory {
  PropsImpl create(String name);
}
