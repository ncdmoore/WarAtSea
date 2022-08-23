package com.enigma.waratsea.property;

import com.enigma.waratsea.resource.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Defines properties. There are general overall properties and game specific properties. Game specific
 * properties override general properties.
 */
@Slf4j
public class PropsImpl implements Props {
  private static final String PROPERTIES_DIR = "properties";
  private final Properties properties = new Properties();

  @Inject
  PropsImpl(final ResourceNames resourceNames, @Assisted final String name) {
    var generalPath = Paths.get(PROPERTIES_DIR, name).toString();
    var gameSpecificPath = Paths.get(resourceNames.getGameName(), PROPERTIES_DIR, name).toString();

    load(generalPath);
    load(gameSpecificPath);
  }

  @Override
  public String getString(final String key) {
    return properties.getProperty(key);
  }

  @Override
  public int getInt(final String key) {
    return Integer.parseInt(properties.getProperty(key));
  }

  @Override
  public double getDouble(final String key) {
    return Double.parseDouble(properties.getProperty(key));
  }

  private void load(final String name) {
    try (var inputStream = getClass().getClassLoader().getResourceAsStream(name)) {
      properties.load(inputStream);
      log.debug("Loaded properties: '{}'", name);
    } catch (IOException | RuntimeException ex) {
      log.warn("Unable to load properties file: '{}'", name);
    }
  }
}
