package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.repository.GameRepository;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.serializer.LocalDateDeserializer;
import com.enigma.waratsea.repository.serializer.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class GameRepositoryImpl implements GameRepository {
  private final Props props;
  private final GamePaths gamePaths;
  private final DataProvider dataProvider;

  @Inject
  public GameRepositoryImpl(final @Named("App") Props props,
                        final GamePaths gamePaths,
                        final DataProvider dataProvider) {
    this.props = props;
    this.gamePaths = gamePaths;
    this.dataProvider = dataProvider;
  }

  @Override
  public List<GameEntity> get() {
    return getSavedGames()
        .stream()
        .map(this::createGame)
        .collect(Collectors.toList());
  }

  @Override
  public void save(final GameEntity game) {
    var gameId = game.getId();
    var directory = dataProvider.getSavedDirectory(gameId);
    writeGame(directory, game);
  }

  private List<String> getSavedGames() {
    var savedGameDirectory = gamePaths.getSavedGameDirectory();
    return dataProvider.getSubDirectoryPaths(savedGameDirectory)
        .stream()
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
  }

  private GameEntity createGame(final String savedGameName) {
    try (var in = getSavedGameInputStream(savedGameName);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      return readGame(br);
    } catch (IOException e) {
      throw new GameException("Unable to read game: " + savedGameName, e);
    }
  }

  private InputStream getSavedGameInputStream(final String savedGameName) throws FileNotFoundException {
    var savedGameDirectory = gamePaths.getSavedGameDirectory();
    var savedGameFile = gamePaths.getGameEntityName();
    var filePath = Paths.get(savedGameDirectory, savedGameName, savedGameFile);
    return dataProvider.getSavedGameFileInputStream(filePath);
  }

  private GameEntity readGame(final BufferedReader bufferedReader) {
    var dateFormat = getDateFormat();
    var gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer(dateFormat))
        .create();
    var game = gson.fromJson(bufferedReader, GameEntity.class);

    log.debug("loaded game: {}", game.getId());

    return game;
  }

  private void writeGame(final Path directoryPath, final GameEntity game) {
    var dateFormat = getDateFormat();
    var filePath = Paths.get(directoryPath.toString(), gamePaths.getGameEntityName());

    try (var out = new FileOutputStream(filePath.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      Gson gson = new GsonBuilder()
          .setPrettyPrinting()
          .registerTypeAdapter(LocalDate.class, new LocalDateSerializer(dateFormat))
          .create();
      log.debug("Save game to path: '{}'", filePath);
      var json = gson.toJson(game);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save " + game.getId() + " to path: " + filePath, e);
    }
  }

  private String getDateFormat() {
    return props.getString("scenario.date.format");
  }
}
