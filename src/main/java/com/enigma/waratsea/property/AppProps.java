package com.enigma.waratsea.property;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.function.Function;

import static com.enigma.waratsea.Globals.APP_PROPS;

@Singleton
public class AppProps {
  private final PropsFactory propsFactory;
  private Props props;

  private Function<String, String> getStringFunc = this::loadAndGetStringValue;

  @Inject
  public AppProps(final PropsFactory propsFactory) {
    this.propsFactory = propsFactory;
  }

  public String getString(final String key) {
    return getStringFunc.apply(key);
  }

  private String loadAndGetStringValue(final String key) {
    loadProps();

    return props.getString(key);
  }

  private void loadProps() {
    if (props == null) {
      props = propsFactory.create(APP_PROPS);
      getStringFunc = this::getStringValue;
    }
  }

  private String getStringValue(final String key) {
    return props.getString(key);
  }
}
