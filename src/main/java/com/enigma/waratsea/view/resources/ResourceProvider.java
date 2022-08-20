package com.enigma.waratsea.view.resources;

import com.enigma.waratsea.exceptions.ResourceException;
import com.enigma.waratsea.resource.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.Optional;

/**
 * This is a utility class that provides methods for accessing game resources such as css, images and media.
 */
@Singleton
@Slf4j
public class ResourceProvider {
    private final String cssDirectory;
    private final String gameImageDirectory;
    private final String gameName;

    @Inject
    public ResourceProvider(final ResourceNames resourceNames) {
        var imageDirectory = resourceNames.getImageDirectory();
        var gameDirectory = resourceNames.getGameDirectory();

        this.cssDirectory = resourceNames.getCssDirectory();
        this.gameImageDirectory = Paths.get(imageDirectory, gameDirectory).toString();
        this.gameName = resourceNames.getGameName();
    }

    public String getCss(final String name) {
        var cssPath = Paths.get(cssDirectory, name).toString();
        var url = getClass().getClassLoader().getResource(cssPath);

        if (url == null) {
            throw new ResourceException(cssPath);
        }

        return cssPath;
    }

    public ImageView getGameImageView(final String imageName) {
        return new ImageView(getGameImage(imageName));
    }

    public Image getGameImage(final String imageName) {
        var path = Paths.get(gameName, gameImageDirectory, imageName).toString();
        log.debug("get image: '{}'", path);
        return getImageResource(path);
    }

    private Image getImageResource(final String path) {
        return Optional.ofNullable(getClass()
                .getClassLoader()
                .getResourceAsStream(path))
                .map(Image::new)
                .orElseThrow(() -> new ResourceException(path));
    }
}
