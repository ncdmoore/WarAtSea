package com.enigma.waratsea;

import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.game.CurrentGameName;
import com.enigma.waratsea.model.game.GameName;
import com.enigma.waratsea.view.pregame.StartView;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
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
        URL url = WarAtSeaApplication.class.getClassLoader().getResource(game);

        if (url == null) {
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
        String name = parameter[0];
        String value = parameter[1];

        HANDLERS
                .getOrDefault(name, WarAtSeaApplication::unknownParameter)
                .accept(value);
    }

    private void initGame(final Injector injector) {
        CurrentGameName currentGameName = injector.getInstance(CurrentGameName.class);
        currentGameName.setValue(GameName.convert(GAME_PARAMETERS.get(GAME_NAME)));
        log.info("Game set to '{}'", currentGameName.getValue());
    }

    private void initGui(final Injector injector, final Stage stage) {
        StartView startView = injector.getInstance(StartView.class);
        startView.display(stage);
    }
}