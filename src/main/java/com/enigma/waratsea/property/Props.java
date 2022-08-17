package com.enigma.waratsea.property;

import com.enigma.waratsea.model.game.CurrentGameName;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Defines properties. There are general overall properties and game specific properties. Game specific
 * properties override general properties if the same key is used.
 */
@Singleton
@Slf4j
public class Props {
    private final Properties properties = new Properties();

    @Inject
    public Props(final CurrentGameName currentGameName, final String name) {
        load(name);
        load(currentGameName.toString() + "/" + name);
    }

    public String getString(final String key) {
        return properties.getProperty(key);
    }

    public int getInt(final String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public double getDouble(final String key) {
        return Double.parseDouble(properties.getProperty(key));
    }

    private void load(final String name) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name)) {
            properties.load(inputStream);
            log.debug("Loaded properties: '{}'", name);
        } catch (IOException | RuntimeException ex) {
            log.warn("Unable to load properties file: '{}'", name);
        }
    }
}
