package com.enigma.waratsea.view.resources;

import com.enigma.waratsea.exceptions.ResourceException;
import com.enigma.waratsea.repository.impl.ResourceNames;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Singleton
@Slf4j
public class ResourceProvider {
  private final String scenarioDirectory;
  private final String cssDirectory;
  private final String imageDirectory;
  private final String commonImageDirectory;
  private final String gamePath;

  @Inject
  ResourceProvider(final ResourceNames resourceNames) {
    var commonDirectory = resourceNames.getCommonDirectory();

    this.imageDirectory = resourceNames.getImageDirectory();
    this.scenarioDirectory = resourceNames.getScenarioDirectory();
    this.cssDirectory = resourceNames.getCssDirectory();
    this.commonImageDirectory = Paths.get(imageDirectory, commonDirectory).toString();
    this.gamePath = resourceNames.getGamePath();
  }

  public String getCss(final String name) {
    var cssPath = Paths.get(cssDirectory, name).toString();
    var url = getClass().getClassLoader().getResource(cssPath);

    if (url == null) {
      throw new ResourceException("Unable to get css: " + cssPath);
    }

    return cssPath;
  }

  public Image getImage(final String scenario, final String imageName) {
    return getScenarioSpecificImage(scenario, imageName)
        .or(() -> getCommonImage(imageName))
        .orElseThrow(() -> new ResourceException("Unable to get image: " + imageName));
  }

  public Image getGameImage(final String imageName) {
    return getCommonImage(imageName)
        .orElseThrow(() -> new ResourceException(imageName));
  }

  public ImageView getGameImageView(final String imageName) {
    return new ImageView(getGameImage(imageName));
  }

  private Optional<Image> getCommonImage(final String imageName) {
    var path = Paths.get(gamePath, commonImageDirectory, imageName).toString();
    log.debug("get image: '{}'", path);
    return getImageResource(path);
  }

  private Optional<Image> getScenarioSpecificImage(final String scenario, final String imageName) {
    String path = Paths.get(gamePath, scenarioDirectory, scenario, imageDirectory, imageName).toString();
    log.debug("get image: {}", path);
    return getImageResource(path);
  }

  private Optional<Image> getImageResource(final String path) {
    try (var inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
      return Optional
          .ofNullable(inputStream)
          .map(Image::new);
    } catch (IOException e) {
      throw new ResourceException("Unable to get image resource: " + path);
    }
  }
}
