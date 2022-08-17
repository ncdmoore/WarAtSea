package com.enigma.waratsea.view.resources;

import com.enigma.waratsea.model.game.CurrentGameName;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

/**
 * This is a utility class that provides methods for accessing game resources such as css, images and media.
 */
@Singleton
@Slf4j
public class ResourceProvider {
    private static final String CSS_DIR = "css/";
    private static final String IMAGE_DIR = "/images/";
    private static final String GAME_IMAGE_DIR = IMAGE_DIR + "game/";
    private final String currentGameName;

    @Inject
    public ResourceProvider(final CurrentGameName currentGameName) {
        this.currentGameName = currentGameName.toString();
    }

    public String getCss(final String name) {
        String cssPath = CSS_DIR + name;
        Optional<URL> url = Optional.ofNullable(getClass().getClassLoader().getResource(cssPath));
        log.debug("load css: '{}'", cssPath);
        return url.map(dummy -> cssPath).orElseThrow(() -> new ResourceException(cssPath));
    }

    public ImageView getGameImageView(final String imageName) {
        return new ImageView(getGameImage(imageName));
    }

    public Image getGameImage(final String imageName) {
        String path = currentGameName  + GAME_IMAGE_DIR  + imageName;
        log.debug("get image: '{}'", path);
        return loadImageResource(path).orElseThrow(() -> new ResourceException(path));
    }

    private Optional<Image> loadImageResource(final String path) {
        return Optional.ofNullable(getClass()
                        .getClassLoader()
                        .getResource(path))
                .map(this::getFileURI)
                .map(this::getImageFromURI);
    }

    private URI getFileURI(final URL url) {
        try {
            File file =  new File(url.toURI().getPath());
            log.debug("Loaded image: {}", url.toURI().getPath());
            return file.toURI();
        } catch (URISyntaxException ex) {
            log.error("Unable to get URI from URL.", ex);
            return null;
        }
    }

    private Image getImageFromURI(final URI uri) {
        return new Image(uri.toString());
    }

}
