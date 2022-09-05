package com.enigma.waratsea.property;

import com.enigma.waratsea.resource.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MapProps implements Props {
  public static final String MAP_PROPS = "map.properties";
  private final PropsWrapper propsWrapper;

  @Inject
  public MapProps(final ResourceNames resourceNames) {
    this.propsWrapper = new PropsWrapper(resourceNames, MAP_PROPS, false);
  }

  public String getString(final String key) {
    return propsWrapper.getString(key);
  }

  public int getInt(final String key) {
    return propsWrapper.getInt(key);
  }

  public double getDouble(final String key) {
    return propsWrapper.getDouble(key);
  }
}
