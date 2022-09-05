package com.enigma.waratsea.property;

import com.enigma.waratsea.resource.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static com.enigma.waratsea.Globals.APP_PROPS;

@Singleton
public class AppProps implements Props {
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
