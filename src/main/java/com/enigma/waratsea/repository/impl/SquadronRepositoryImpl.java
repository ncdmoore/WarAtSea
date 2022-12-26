package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.SquadronEntity;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.SquadronRepository;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    return readSquadron(squadronId);
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

  private InputStream getInputStream(final Id squadronId) {
    return dataProvider.getDataInputStream(squadronId, squadronDirectory);
  }

  private SquadronEntity toEntity(final BufferedReader bufferedReader) {
    var gson = new Gson();
    var squadron = gson.fromJson(bufferedReader, SquadronEntity.class);

    log.debug("load squadron: '{}'", squadron.getId());

    return squadron;
  }
}
