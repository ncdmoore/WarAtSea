package com.enigma.waratsea.property;

import com.enigma.waratsea.repository.provider.GamePaths;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AppProps implements Props {
  public static final String APP_PROPS = "app.properties";
  private final PropsWrapper propsWrapper;

  @Inject
  public AppProps(final GamePaths gamePaths) {
    this.propsWrapper = new PropsWrapper(gamePaths, APP_PROPS);
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
