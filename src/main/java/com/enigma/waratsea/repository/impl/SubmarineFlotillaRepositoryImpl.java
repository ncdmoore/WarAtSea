package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.SubmarineFlotillaRepository;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class SubmarineFlotillaRepositoryImpl implements SubmarineFlotillaRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public List<SubmarineFlotillaEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readSubmarineFlotillas(filePath);
  }

  @Override
  public void save(final String gameId, final Side side, final Set<SubmarineFlotillaEntity> flotillas) {
    var filePath = getFilePath(side);

    writeSubmarineFlotillas(gameId, filePath, flotillas);
  }

  private List<SubmarineFlotillaEntity> readSubmarineFlotillas(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read submarine flotillas: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read submarine flotilla: '{}' {} '{}'", filePath, e.getClass(), e.getMessage());
      return Collections.emptyList();
    }
  }

  private void writeSubmarineFlotillas(final String gameId, final FilePath filePath, final Set<SubmarineFlotillaEntity> flotillas) {
    try (var out = getOutputStream(gameId, filePath);
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save submarine flotillas for game: '{}' to path: '{}'", gameId, filePath);
      var json = toJson(flotillas);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save submarine flotilla for game: " + gameId + " to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getInputStream(filePath);
  }

  private OutputStream getOutputStream(final String gameId, final FilePath filePath) throws FileNotFoundException {
    return dataProvider.getOutputStream(gameId, filePath);
  }

  private List<SubmarineFlotillaEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<SubmarineFlotillaEntity>>() {
    }.getType();

    var gson = new Gson();
    List<SubmarineFlotillaEntity> submarineFlotillas = gson.fromJson(bufferedReader, collectionType);

    log.debug("load submarine flotillas: '{}',", submarineFlotillas.stream()
        .map(SubmarineFlotillaEntity::getId)
        .map(Id::toString)
        .collect(Collectors.joining(",")));

    return submarineFlotillas;
  }

  private String toJson(final Set<SubmarineFlotillaEntity> flotillas) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(flotillas);
  }

  private FilePath getFilePath(final Side side) {
    var flotillaDirectory = gamePaths.getFlotillaDirectory();
    var submarineFileName = gamePaths.getSubmarineFileName();

    return FilePath.builder()
        .baseDirectory(flotillaDirectory)
        .side(side)
        .fileName(submarineFileName)
        .build();
  }
}
