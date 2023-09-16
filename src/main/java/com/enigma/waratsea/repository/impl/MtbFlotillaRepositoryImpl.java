package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.MtbFlotillaEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.MtbFlotillaRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class MtbFlotillaRepositoryImpl implements MtbFlotillaRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Override
  public List<MtbFlotillaEntity> get(final Side side) {
    var filePath = getFilePath(side);

    return readMtbFlotillas(filePath);
  }

  @Override
  public void save(final String gameId, final Side side, final Set<MtbFlotillaEntity> flotillas) {
    var filePath = getFilePath(side);

    writeMtbFlotillas(gameId, filePath, flotillas);
  }

  private List<MtbFlotillaEntity> readMtbFlotillas(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read MTB flotillas: '{}'", filePath);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read MTB flotilla: '{}' {} '{}'", filePath, e.getClass(), e.getMessage());
      return Collections.emptyList();
    }
  }

  private void writeMtbFlotillas(final String gameId, final FilePath filePath, final Set<MtbFlotillaEntity> flotillas) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save MTB flotillas to path: '{}'", path);
      var json = toJson(flotillas);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save MTB flotilla to path: " + path, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
  }

  private List<MtbFlotillaEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<MtbFlotillaEntity>>() {
    }.getType();

    var gson = new Gson();
    List<MtbFlotillaEntity> mtbFlotillas = gson.fromJson(bufferedReader, collectionType);

    log.debug("load MTB flotillas: '{}',", mtbFlotillas.stream()
        .map(MtbFlotillaEntity::getId)
        .map(Id::toString)
        .collect(Collectors.joining(",")));

    return mtbFlotillas;
  }

  private String toJson(final Set<MtbFlotillaEntity> flotillas) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(flotillas);
  }

  private FilePath getFilePath(final Side side) {
    var flotillaDirectory = gamePaths.getFlotillaDirectory();
    var mtbFileName = gamePaths.getMtbFileName();

    return FilePath.builder()
        .baseDirectory(flotillaDirectory)
        .side(side)
        .fileName(mtbFileName)
        .build();
  }
}
