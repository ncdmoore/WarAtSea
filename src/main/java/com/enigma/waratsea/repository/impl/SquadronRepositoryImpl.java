package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.SquadronRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@Singleton
public class SquadronRepositoryImpl implements SquadronRepository {
  private final DataProvider dataProvider;
  private final String squadronDirectory;

  @Inject
  public SquadronRepositoryImpl(final GamePaths gamePaths,
                                final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.squadronDirectory = gamePaths.getSquadronDirectory();
  }

  @Override
  public SquadronEntity get(final Id squadronId) {
    var filePath = getFilePath(squadronId);

    return readSquadron(filePath);
  }

  @Override
  public void save(final String gameId, final SquadronEntity squadron) {
    var filePath = getFilePath(squadron);

    writeSquadron(gameId, filePath, squadron);
  }

  private SquadronEntity readSquadron(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read squadron: '{}'", filePath);
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("unable to create squadron: " + filePath, e);
    }
  }

  private void writeSquadron(final String gameId, final FilePath filePath, final SquadronEntity squadron) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save squadron to path: '{}'", path);
      var json = toJson(squadron);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save squadron to path: " + path, e);
    }
  }

  private InputStream getInputStream(final FilePath filePath) {
    return dataProvider.getDataInputStream(filePath);
  }

  private SquadronEntity toEntity(final BufferedReader bufferedReader) {
    var gson = new Gson();
    var squadron = gson.fromJson(bufferedReader, SquadronEntity.class);

    log.debug("load squadron: '{}'", squadron.getId());

    return squadron;
  }

  private String toJson(final SquadronEntity squadron) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(squadron);
  }

  private FilePath getFilePath(final Id squadronId) {
    return FilePath.builder()
        .baseDirectory(squadronDirectory)
        .side(squadronId.getSide())
        .fileName(squadronId.getName())
        .build();
  }

  private FilePath getFilePath(final SquadronEntity squadron) {
    return FilePath.builder()
        .baseDirectory(squadronDirectory)
        .side(squadron.getId().getSide())
        .fileName(squadron.getId().getName())
        .build();
  }
}
