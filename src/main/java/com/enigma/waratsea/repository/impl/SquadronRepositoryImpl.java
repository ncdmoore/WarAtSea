package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.SquadronRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

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
    return readSquadron(squadronId);
  }

  @Override
  public void save(final String gameId, final SquadronEntity squadron) {
    var id = squadron.getId();
    var directory = dataProvider.getSavedEntityDirectory(gameId, id, squadronDirectory);
    writeSquadron(directory, squadron);
  }

  private SquadronEntity readSquadron(final Id squadronId) {
    try (var in = getInputStream(squadronId);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read squadron: '{}'", squadronId);
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("unable to create squadron: " + squadronId);
    }
  }

  private void writeSquadron(final Path directory, final SquadronEntity squadron) {
    var id = squadron.getId();
    var filePath = dataProvider.getSaveFile(directory, id);

    try (var out = new FileOutputStream(filePath.toString());
    var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save squadron: '{}' to path: '{}'", id, directory);
      var json = toJson(squadron);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save " + id + " to path: " + filePath, e);
    }
  }

  private InputStream getInputStream(final Id squadronId) {
    return dataProvider.getDataInputStream(squadronId, squadronDirectory);
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
}
