package com.enigma.waratsea.resource;

import com.enigma.waratsea.model.game.CurrentGameName;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.net.URL;
import java.util.Optional;

@Singleton
public class ResourceProvider {
    private final CurrentGameName currentGameName;

    @Inject
    public ResourceProvider(final CurrentGameName currentGameName) {
        this.currentGameName  = currentGameName;
    }

    public Optional<URL> getResourceUrl(final String resourceName) {
        return Optional.ofNullable(getClass()
                .getClassLoader()
                .getResource(currentGameName.getValue() + resourceName));
    }

}
