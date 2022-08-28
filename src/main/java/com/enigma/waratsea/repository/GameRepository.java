package com.enigma.waratsea.repository;

import com.enigma.waratsea.data.DataNames;
import com.enigma.waratsea.data.DataProvider;
import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.property.AppProps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides games.
 */
@Slf4j
@Singleton
public class GameRepository {
  private final AppProps appProps;
  private final DataNames dataNames;
  private final DataProvider dataProvider;

  @Inject
  public GameRepository(final AppProps appProps,
                        final DataNames dataNames,
                        final DataProvider dataProvider) {
    this.appProps = appProps;
    this.dataNames = dataNames;
    this.dataProvider = dataProvider;
  }

  public List<GameEntity> get() {
    return getSavedGames()
        .stream()
        .map(this::createGame)
        .collect(Collectors.toList());
  }

  public void save(final GameEntity game) {
    var savedGameDirectory = dataNames.getSavedGameDirectory();
    var gameId = game.getId();
    var directory = Paths.get(savedGameDirectory, gameId);

    log.debug("Save game to path: '{}'", directory);

    dataProvider.createDirectory(directory);
    writeGame(directory, game);
  }

  private List<String> getSavedGames() {
    var savedGameDirectory = dataNames.getSavedGameDirectory();
    return dataProvider.getSubDirectoryPaths(savedGameDirectory)
        .stream()
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
  }

  private GameEntity createGame(final String directoryName) {

    log.debug("'{}'", directoryName);

    var savedGameDirectory = dataNames.getSavedGameDirectory();
    var filePath = Paths.get(savedGameDirectory, directoryName, dataNames.getGameEntityName());

    try (var in = new FileInputStream(filePath.toString());
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
        return readGame(br);
    } catch (IOException e) {
      log.error("Unable to read game: '{}'", filePath, e);
    }

    return null;
  }

  private GameEntity readGame(final BufferedReader bufferedReader) {
    var dateFormat = getDateFormat();
    var gsonBuilder = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer(dateFormat));
    var gson = gsonBuilder.create();

    var game = gson.fromJson(bufferedReader, GameEntity.class);

    log.debug("loaded game: {}", game.getId());

    return game;
  }

  private void writeGame(final Path directoryPath, final GameEntity game) {
    var dateFormat = getDateFormat();
    var filePath = Paths.get(directoryPath.toString(), dataNames.getGameEntityName());

    try (var out = new FileOutputStream(filePath.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      Gson gson = new GsonBuilder()
          .setPrettyPrinting()
          .registerTypeAdapter(LocalDate.class, new LocalDateSerializer(dateFormat))
          .create();
      String json = gson.toJson(game);
      writer.write(json);
    } catch (IOException e) {
      log.error("Unable to save '{}' to path: '{}'", game.getId(), filePath, e);
    }
  }

  private String getDateFormat() {
    return appProps.getString("scenario.date.format");
  }
}
