package com.enigma.waratsea.property;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.function.Function;

import static com.enigma.waratsea.Globals.APP_PROPS;

@Singleton
public class AppProps implements Props {
  private final PropsWrapperFactory propsWrapperFactory;
  private PropsWrapper propsWrapper;

  private Function<String, String> getStringFunc = this::loadAndGetStringValue;
  private Function<String, Integer> getIntFunc = this::loadAndGetIntValue;
  private Function<String, Double> getDoubleFunc = this::loadAndGetDoubleValue;
  @Inject
  public AppProps(final PropsWrapperFactory propsWrapperFactory) {
    this.propsWrapperFactory = propsWrapperFactory;
  }

  public String getString(final String key) {
    return getStringFunc.apply(key);
  }

  @Override
  public int getInt(String key) {
    return getIntFunc.apply(key);
  }

  @Override
  public double getDouble(String key) {
    return getDoubleFunc.apply(key);
  }

  private String loadAndGetStringValue(final String key) {
    loadProps();

    return propsWrapper.getString(key);
  }

  private int loadAndGetIntValue(final String key) {
    loadProps();

    return propsWrapper.getInt(key);
  }

  private double loadAndGetDoubleValue(final String key) {
    loadProps();

    return propsWrapper.getDouble(key);
  }

  private void loadProps() {
    if (propsWrapper == null) {
      propsWrapper = propsWrapperFactory.create(APP_PROPS);
      getStringFunc = this::getStringValue;
      getIntFunc = this::getIntValue;
      getDoubleFunc = this::getDoubleValue;
    }
  }

  private String getStringValue(final String key) {
    return propsWrapper.getString(key);
  }

  private int getIntValue(final String key) {
    return propsWrapper.getInt(key);
  }

  private double getDoubleValue(final String key) {
    return propsWrapper.getDouble(key);
  }
}
