package com.enigma.waratsea.property;

import com.enigma.waratsea.repository.impl.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AppProps implements Props {
  public static final String APP_PROPS = "app.properties";
  private final PropsWrapper propsWrapper;

  @Inject
  public AppProps(final ResourceNames resourceNames) {
    this.propsWrapper = new PropsWrapper(resourceNames, APP_PROPS);
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
