package com.enigma.waratsea.view.resources;

import com.enigma.waratsea.exceptions.ResourceException;
import com.enigma.waratsea.model.game.CurrentGameName;
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
    private static final String CSS_DIR = "css";
    private static final String IMAGE_DIR = "images";
    private static final String GAME_IMAGE_DIR = Paths.get(IMAGE_DIR,"game").toString();
    private final String currentGameName;

    @Inject
    public ResourceProvider(final CurrentGameName currentGameName) {
        this.currentGameName = currentGameName.toString();
    }

    public String getCss(final String name) {
        var cssPath = Paths.get(CSS_DIR, name).toString();
        var url = Optional.ofNullable(getClass().getClassLoader().getResource(cssPath));
        url.ifPresent(u -> log.debug("Found url: '{}'", u.getPath()));
        return url.map(dummy -> cssPath).orElseThrow(() -> new ResourceException(cssPath));
    }

    public ImageView getGameImageView(final String imageName) {
        return new ImageView(getGameImage(imageName));
    }

    public Image getGameImage(final String imageName) {
        var path = Paths.get(currentGameName, GAME_IMAGE_DIR, imageName).toString();
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
