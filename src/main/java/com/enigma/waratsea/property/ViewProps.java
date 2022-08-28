package com.enigma.waratsea.property;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.function.Function;

import static com.enigma.waratsea.Globals.VIEW_PROPS;

@Singleton
public class ViewProps {
  private final PropsFactory propsFactory;
  private Props props;

  private Function<String, String> getStringFunc = this::loadAndGetStringValue;
  private Function<String, Integer> getIntFunc = this::loadAndGetIntValue;
  private Function<String, Double> getDoubleFunc = this::loadAndGetDoubleValue;

  @Inject
  public ViewProps(final PropsFactory propsFactory) {
    this.propsFactory = propsFactory;
  }

  public String getString(final String key) {
    return getStringFunc.apply(key);
  }

  public int getInt(final String key) {
    return getIntFunc.apply(key);
  }

  public double getDouble(final String key) {
    return getDoubleFunc.apply(key);
  }

  private String loadAndGetStringValue(final String key) {
    loadProps();

    return props.getString(key);
  }

  private int loadAndGetIntValue(final String key) {
    loadProps();

    return props.getInt(key);
  }

  private double loadAndGetDoubleValue(final String key) {
    loadProps();

    return props.getDouble(key);
  }

  private void loadProps() {
    if (props == null) {
      props = propsFactory.create(VIEW_PROPS);
      getStringFunc = this::getStringValue;
      getIntFunc = this::getIntValue;
      getDoubleFunc = this::getDoubleValue;
    }
  }

  private String getStringValue(final String key) {
    return props.getString(key);
  }

  private int getIntValue(final String key) {
    return props.getInt(key);
  }

  private double getDoubleValue(final String key) {
    return props.getDouble(key);
  }
}
