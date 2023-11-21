package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.GameMapEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.repository.MapRepository;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.provider.ResourceProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class MapRepositoryImpl implements MapRepository {
  private final GamePaths gamePaths;
  private final ResourceProvider resourceProvider;

  @Override
  public GameMapEntity get() {
    return createGameMap();
  }

  private GameMapEntity createGameMap() {
    try (var in = getMapInputStream();
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      return readGameMap(br);
    } catch (IOException e) {
      throw new GameException("Unable to create game map");
    }
  }

  private InputStream getMapInputStream() {
    var mapDirectory = gamePaths.getMapDirectory();
    var mapFileName = gamePaths.getGameMapFileName();
    var resourceName = Paths.get(mapDirectory, mapFileName).toString();
    return resourceProvider.getDefaultInputStream(resourceName);
  }

  private GameMapEntity readGameMap(final BufferedReader bufferedReader) {
    var gson = new Gson();

    var gameMap = gson.fromJson(bufferedReader, GameMapEntity.class);

    log.debug("load game map");

    return gameMap;
  }
}
