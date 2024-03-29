package com.enigma.waratsea.view.resources;

import com.enigma.waratsea.exception.ResourceException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.provider.GamePaths;
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
  private final String aircraftImageDirectory;
  private final String shipImageDirectory;
  private final String gamePath;

  @Inject
  ResourceProvider(final GamePaths gamePaths) {
    var commonDirectory = gamePaths.getCommonDirectory();

    this.imageDirectory = gamePaths.getImageDirectory();
    this.scenarioDirectory = gamePaths.getScenarioDirectory();
    this.cssDirectory = gamePaths.getCssDirectory();
    this.commonImageDirectory = Paths.get(imageDirectory, commonDirectory).toString();
    this.aircraftImageDirectory = Paths.get(gamePaths.getAircraftBaseDirectory(), imageDirectory).toString();
    this.shipImageDirectory = Paths.get(gamePaths.getShipBaseDirectory(), imageDirectory).toString();
    this.gamePath = gamePaths.getGamePath();
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
        .or(() -> getGameCommonImage(imageName))
        .orElseThrow(() -> new ResourceException("Unable to get image: " + imageName));
  }

  public Image getImage(final String scenario, final Side side, final String imageName) {
    return getScenarioSpecificImage(scenario, side, imageName)
       .or(() -> Optional.ofNullable(getImage(side, imageName)))
       .orElseThrow(() -> new ResourceException("Unable to get image: " + imageName));
  }

  public Image getImage(final Side side, final String imageName) {
    return getSideImageResource(side, imageName)
      .or(() -> getGameCommonImage(imageName))
      .orElseThrow(() -> new ResourceException("Unable to get image: " + imageName));
  }

  public Image getImage(final Nation nation, final String imageName) {
    return getNationImageResource(nation, imageName)
       .or(() -> getGameCommonImage(imageName))
       .orElseThrow(() -> new ResourceException("Unable to get image: " + imageName));
  }

  public Image getGameImage(final String imageName) {
    return getGameCommonImage(imageName)
        .orElseThrow(() -> new ResourceException(imageName));
  }

  public Image getAppImage(final String imageName) {
    return getAppCommonImage(imageName)
        .orElseThrow(() -> new ResourceException(imageName));
  }

  public Image getAircraftProfileImage(final Id aircraftId) {
    return getGameAircraftProfileImage(aircraftId)
        .orElseGet(() -> getAppCommonImage("NotFound.png")
            .orElseThrow(() -> new ResourceException("Can't even find NotFound.png")));
  }

  public Image getShipProfileImage(final Id shipId) {
    return getGameShipProfileImage(shipId)
        .orElseGet(() -> getAppCommonImage("NotFound.png")
            .orElseThrow(() -> new ResourceException("Can't even find NotFound.png")));
  }

  public Image getShipImage(final Id shipId) {
    return getGameShipImage(shipId)
        .orElseGet(() -> getAppCommonImage("NotFound.png")
            .orElseThrow(() -> new ResourceException("Can't even find NotFound.png")));
  }

  public ImageView getGameImageView(final String imageName) {
    return new ImageView(getGameImage(imageName));
  }

  public ImageView getAppImageView(final String imageName) {
    return new ImageView(getAppImage(imageName));
  }

  private Optional<Image> getAppCommonImage(final String imageName) {
    var path = Paths.get(commonImageDirectory, imageName).toString();
    log.debug("get image: '{}'", path);
    return getImageResource(path);
  }

  private Optional<Image> getGameCommonImage(final String imageName) {
    var path = Paths.get(gamePath, commonImageDirectory, imageName).toString();
    log.debug("get image: '{}'", path);
    return getImageResource(path);
  }

  private Optional<Image> getGameAircraftProfileImage(final Id aircraftId) {
    var side = aircraftId.getSide().toLower();
    var name = aircraftId.getName() + "-profile.png";
    var path = Paths.get(gamePath, aircraftImageDirectory, side, name).toString();
    return getImageResource(path);
  }

  private Optional<Image> getGameShipProfileImage(final Id shipId) {
    var side = shipId.getSide().toLower();
    var name = shipId.getName() + "-profile.png";
    var path = Paths.get(gamePath, shipImageDirectory, side, name).toString();
    return getImageResource(path);
  }

  private Optional<Image> getGameShipImage(final Id shipId) {
    var side = shipId.getSide().toLower();
    var name = shipId.getName() + ".png";
    var path = Paths.get(gamePath, shipImageDirectory, side, name).toString();
    return getImageResource(path);
  }

  private Optional<Image> getScenarioSpecificImage(final String scenario, final String imageName) {
    var path = Paths.get(gamePath, scenarioDirectory, scenario, imageDirectory, imageName).toString();
    log.debug("get image: {}", path);
    return getImageResource(path);
  }

  private Optional<Image> getScenarioSpecificImage(final String scenario, final Side side, final String imageName) {
    var path = Paths.get(gamePath, scenarioDirectory, scenario, imageDirectory, side.toString(), imageName).toString();
    log.debug("get image: {}", path);
    return getImageResource(path);
  }

  private Optional<Image> getSideImageResource(final Side side, final String imageName) {
    var path = Paths.get(gamePath, imageDirectory, side.toString(), imageName).toString();
    log.debug("get image: '{}'", path);
    return getImageResource(path);
  }

  private Optional<Image> getNationImageResource(final Nation nation, final String imageName) {
    var path = Paths.get(gamePath, imageDirectory, nation.toLower(), imageName).toString();
    log.debug("get image: '{}'", path);
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
