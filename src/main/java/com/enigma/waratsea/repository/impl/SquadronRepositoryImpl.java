package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.SquadronRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class SquadronRepositoryImpl implements SquadronRepository {
  private final DataProvider dataProvider;
  private final GamePaths gamePaths;

  @Inject
  public SquadronRepositoryImpl(final GamePaths gamePaths,
                                final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
    this.gamePaths = gamePaths;
  }

  @Override
  public SquadronEntity get(final Id squadronId) {
    var filePath = getSquadronFilePath(squadronId);

    return readSquadron(filePath);
  }

  @Override
  public void save(final String gameId, final SquadronEntity squadron) {
    var filePath = getSquadronFilePath(squadron);

    writeSquadron(gameId, filePath, squadron);
  }

  @Override
  public List<Id> getManifest(final Side side) {
    var filePath = getSquadronManifestFilePath(side);

    return readSquadronManifest(filePath);
  }

  @Override
  public void saveManifest(String gameId, final Side side, Set<Id> squadronIds) {
    var filePath = getSquadronManifestFilePath(side);

    writeSquadronManifest(gameId, filePath, squadronIds);
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

  private List<Id> readSquadronManifest(final FilePath filePath) {
    try (var in = getInputStream(filePath);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read squadron manifest: '{}'", filePath);
      return toEntities(br);
    } catch (IOException e) {
      throw new GameException("unable to create squadron manifest: " + filePath, e);
    }
  }

  private void writeSquadronManifest(final String gameId, final FilePath filePath, final Set<Id> squadronIds) {
    var path = dataProvider.getSaveFile(gameId, filePath);

    try (var out = new FileOutputStream(path.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save squadron manifest to path: '{}'", path);
      var json = toJson(squadronIds);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save squadron manifest to path: " + path, e);
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

  private List<Id> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<Id>>() {
    }.getType();

    var gson = new Gson();
    List<Id> squadronIds = gson.fromJson(bufferedReader, collectionType);

    log.debug("load squadron manifest Ids: '{}',", squadronIds.stream()
        .map(Id::toString)
        .collect(Collectors.joining(",")));

    return squadronIds;
  }

  private String toJson(final SquadronEntity squadron) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(squadron);
  }

  private String toJson(final Set<Id> squadronIds) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(squadronIds);
  }

  private FilePath getSquadronFilePath(final Id squadronId) {
    var squadronDirectory = gamePaths.getSquadronDirectory();

    return FilePath.builder()
        .baseDirectory(squadronDirectory)
        .side(squadronId.getSide())
        .fileName(squadronId.getName())
        .build();
  }

  private FilePath getSquadronFilePath(final SquadronEntity squadron) {
    var squadronDirectory = gamePaths.getSquadronDirectory();

    return FilePath.builder()
        .baseDirectory(squadronDirectory)
        .side(squadron.getId().getSide())
        .fileName(squadron.getId().getName())
        .build();
  }
  private FilePath getSquadronManifestFilePath(final Side side) {
    var squadronDirectory = gamePaths.getSquadronDirectory();
    var squadronManifestFileName = gamePaths.getSquadronManifestFileName();

    return FilePath.builder()
        .baseDirectory(squadronDirectory)
        .side(side)
        .fileName(squadronManifestFileName)
        .build();
  }
}
