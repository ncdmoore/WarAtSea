package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.SubmarineFlotillaRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class SubmarineFlotillaRepositoryImpl implements SubmarineFlotillaRepository {
  private final DataProvider dataProvider;
  private final String flotillaDirectory;
  private final String submarineFileName;

  @Inject
  public SubmarineFlotillaRepositoryImpl(final GamePaths gamePaths,
                                         final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.flotillaDirectory = gamePaths.getFlotillaDirectory();
    this.submarineFileName = gamePaths.getSubmarineFileName();
  }

  @Override
  public List<SubmarineFlotillaEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readSubmarineFlotillas(filePath);
  }

  @Override
  public void save(String gameId, Side side, Set<SubmarineFlotillaEntity> flotillas) {
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
      log.warn("Unable to submarine flotilla: '{}'", filePath);
      return Collections.emptyList();
    }
  }

  private void writeSubmarineFlotillas(final String gameId, final FilePath filePath, final Set<SubmarineFlotillaEntity> flotillas) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save submarine flotillas to path: '{}'", path);
      var json = toJson(flotillas);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save submarine flotilla to path: " + path, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
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
    return FilePath.builder()
        .baseDirectory(flotillaDirectory)
        .side(side)
        .fileName(submarineFileName)
        .build();
  }
}