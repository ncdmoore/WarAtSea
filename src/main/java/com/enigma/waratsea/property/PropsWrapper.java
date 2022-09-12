package com.enigma.waratsea.property;

import com.enigma.waratsea.resource.ResourceNames;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.function.Function;

@Slf4j
public class PropsWrapper {
  private static final String PROPERTIES_DIR = "properties";
  private Properties properties;

  private final ResourceNames resourceNames;
  private final String name;

  private Function<String, String> getStringFunc = this::initAndGetStringValue;
  private Function<String, Integer> getIntFunc = this::initAndGetIntValue;
  private Function<String, Double> getDoubleFunc = this::initAndGetDoubleValue;

  public PropsWrapper(final ResourceNames resourceNames, final String name) {
    this.resourceNames = resourceNames;
    this.name = name;
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

  private String getStringValue(final String key) {
    return properties.getProperty(key);
  }

  private int getIntValue(final String key) {
    return Integer.parseInt(properties.getProperty(key));
  }

  private double getDoubleValue(final String key) {
    return Double.parseDouble(properties.getProperty(key));
  }

  private String initAndGetStringValue(final String key) {
    init();
    return properties.getProperty(key);
  }

  private int initAndGetIntValue(final String key) {
    init();
    return Integer.parseInt(properties.getProperty(key));
  }

  private double initAndGetDoubleValue(final String key) {
    init();
    return Double.parseDouble(properties.getProperty(key));
  }

  private void init() {
    if (properties == null) {
      properties = new Properties();

      var gamePath = resourceNames.getGamePath();
      var generalPath = Paths.get(PROPERTIES_DIR, name).toString();
      var gameSpecificPath = Paths.get(gamePath, PROPERTIES_DIR, name).toString();

      load(generalPath);
      load(gameSpecificPath);
      setFunctions();
    }
  }

  private void load(final String path) {
    try (var inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
      properties.load(inputStream);
      log.debug("Loaded properties: '{}'", path);
    } catch (IOException | RuntimeException ex) {
      log.warn("Unable to load properties file: '{}'", path);
    }
  }

  void setFunctions() {
    getStringFunc = this::getStringValue;
    getIntFunc = this::getIntValue;
    getDoubleFunc = this::getDoubleValue;
  }
}
