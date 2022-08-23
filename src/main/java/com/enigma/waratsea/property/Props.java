package com.enigma.waratsea.property;

/**
 * Game properties.
 */
public interface Props {
  String getString(String key);

  int getInt(String key);

  double getDouble(String key);
}
