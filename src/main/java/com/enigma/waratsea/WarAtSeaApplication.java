package com.enigma.waratsea;

import com.enigma.waratsea.event.ConfigApplicationEvent;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.view.pregame.StartView;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The main javafx application. The starting point of application execution.
 */
@Slf4j
public class WarAtSeaApplication extends Application {
  private static final String GAME_RESOURCE_DIRECTORY = "game";
  private static final String GAME_NAME = "game";

  private static final Map<String, String> GAME_PARAMETERS = new HashMap<>();
  private static final Map<String, Consumer<String>> HANDLERS = Map.of(GAME_NAME, WarAtSeaApplication::setCurrentGameNameParameter);

  static {
    GAME_PARAMETERS.put(GAME_NAME, GameName.BOMB_ALLEY.getValue());
  }

  @Override
  public void start(final Stage stage) {
    Injector injector = Guice.createInjector(new BasicModule());

    initGame(injector);
    initGui(injector, stage);
  }

  public static void main(final String[] args) {
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
    var path = Paths.get(GAME_RESOURCE_DIRECTORY, game).toString();
    var gameResourceUrl = WarAtSeaApplication.class.getClassLoader().getResource(path);

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
    bootstrapGame(injector);
    fireConfigApplicationEvent(injector);
  }

  private void initGui(final Injector injector, final Stage stage) {
    var startView = injector.getInstance(StartView.class);
    startView.display(stage);
  }

  private void setGameName() {
    GameName currentName = GameName.convert(GAME_PARAMETERS.get(GAME_NAME));
    log.info("Game set to '{}'", currentName);
  }

  private void bootstrapGame(final Injector injector) {
    injector.getInstance(BootStrap.class);
    log.debug("Bootstrap game class created.");
  }

  private void fireConfigApplicationEvent(final Injector injector) {
    GameName currentName = GameName.convert(GAME_PARAMETERS.get(GAME_NAME));
    var events = injector.getInstance(Events.class);
    events.getConfigApplicationEvent().fire(new ConfigApplicationEvent(currentName));
  }
}
