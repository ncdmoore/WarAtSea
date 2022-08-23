package com.enigma.waratsea;

import com.enigma.waratsea.events.GameNameEvent;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.GlobalEvents;
import com.enigma.waratsea.resource.ResourceNames;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.pregame.StartView;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The main javafx application. The starting point of application execution.
 */
@Slf4j
public class WarAtSeaApplication extends Application {
  private static final String GAME_NAME = "game";

  private static final Map<String, String> GAME_PARAMETERS = new HashMap<>();
  private static final Map<String, Consumer<String>> HANDLERS = Map.of(GAME_NAME, WarAtSeaApplication::setCurrentGameNameParameter);

  static {
    GAME_PARAMETERS.put(GAME_NAME, GameName.BOMB_ALLEY.getValue());
  }

  @Override
  public void start(Stage stage) {
    Injector injector = Guice.createInjector(new BasicModule());

    initGame(injector);
    initGui(injector, stage);
  }

  public static void main(String[] args) {
    handleArguments(args);
    launch();
  }

  private static void handleArguments(final String[] args) {
    Stream.of(args).forEach(argument -> {
      String[] parameter = getGameParameter(argument);

      if (isValidParameter(parameter)) {
        processParameter(parameter);
      }
    });
  }

  private static void setCurrentGameNameParameter(final String gameName) {
    isGameValid(gameName);
    GAME_PARAMETERS.put(GAME_NAME, gameName);
  }

  private static void unknownParameter(final String unknownParameterValue) {
    log.warn("Found unknown parameter with value '{}'", unknownParameterValue);
  }

  private static void isGameValid(final String game) {
    var isValidGame = GameName.isValid(game);
    var gameResourceUrl = WarAtSeaApplication.class.getClassLoader().getResource(game);

    if (!isValidGame || gameResourceUrl == null) {
      throw new GameException(game);
    }
  }

  private static String[] getGameParameter(final String arg) {
    return arg.trim().split("\\s*=\\s*");
  }

  private static boolean isValidParameter(final String[] parameter) {
    return parameter.length == 2;
  }

  private static void processParameter(final String[] parameter) {
    var name = parameter[0];
    var value = parameter[1];

    HANDLERS
        .getOrDefault(name, WarAtSeaApplication::unknownParameter)
        .accept(value);
  }

  private void initGame(final Injector injector) {
    setGameName();
    bootstrap(injector);
    fireGameNameEvent(injector);
  }

  private void initGui(final Injector injector, final Stage stage) {
    var startView = injector.getInstance(StartView.class);
    startView.display(stage);
  }

  private void setGameName() {
    GameName currentName = GameName.convert(GAME_PARAMETERS.get(GAME_NAME));
    log.info("Game set to '{}'", currentName);
  }

  private void bootstrap(final Injector injector) {
    // The classes injected here need to receive GameNameEvents.
    // Thus, they must be created or bootstrapped here before the GameNameEvent is fired.
    injector.getInstance(GameService.class);
    injector.getInstance(ResourceNames.class);
    log.debug("Bootstrap classes created.");
  }

  private void fireGameNameEvent(final Injector injector) {
    GameName currentName = GameName.convert(GAME_PARAMETERS.get(GAME_NAME));
    var globalEvents = injector.getInstance(GlobalEvents.class);
    globalEvents.getGameNameEvents().fire(new GameNameEvent(currentName));
  }
}