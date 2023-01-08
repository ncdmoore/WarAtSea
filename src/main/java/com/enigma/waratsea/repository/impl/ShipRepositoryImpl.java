package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.ship.ShipEntity;
import com.enigma.waratsea.entity.ship.ShipRegistryEntity;
import com.enigma.waratsea.exceptions.GameException;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.ship.ShipType;
import com.enigma.waratsea.repository.ShipRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Slf4j
@Singleton
public class ShipRepositoryImpl implements ShipRepository {
  private final ResourceProvider resourceProvider;
  private final DataProvider dataProvider;
  private final String shipRegistryDirectory;
  private final String shipDirectory;

  @Inject
  public ShipRepositoryImpl(final GamePaths gamePaths,
                            final ResourceProvider resourceProvider,
                            final DataProvider dataProvider) {
    this.resourceProvider = resourceProvider;
    this.dataProvider = dataProvider;
    this.shipRegistryDirectory = gamePaths.getShipRegistryDirectory();
    this.shipDirectory = gamePaths.getShipDirectory();
  }

  @Override
  public ShipEntity get(final Id shipId, final ShipType shipType) {
    return readShip(shipId, shipType);
  }

  @Override
  public List<ShipRegistryEntity> getRegistry(final Side side, final ShipType shipType) {
    var registryId = new Id(side, shipType.toLower());
    return readRegistry(registryId);
  }

  @Override
  public void save(final String gameId, final ShipEntity ship) {
    var id = ship.getId();
    var directory = dataProvider.getSavedEntityDirectory(gameId, id, shipDirectory);
    writeShip(directory, ship);
  }

  private ShipEntity readShip(final Id shipId, final ShipType shipType) {
    try (var in = getShipInputStream(shipId, shipDirectory);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read ship: '{}'", shipId);
      return toEntity(br, shipType);
    } catch (IOException e) {
     throw new GameException("Unable to load ship: " + shipId);
    }
  }

  private void writeShip(final Path directory, final ShipEntity ship) {
    var id = ship.getId();
    var filePath = dataProvider.getSaveFile(directory, id);

    try (var out = new FileOutputStream(filePath.toString());
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.info("Save ship: '{}' to path: '{}'", id, directory);
      var json = toJson(ship);
      writer.write(json);

    } catch (IOException e) {
      throw new GameException("Unable to save " + id + " to path: " + filePath, e);
    }
  }

  private List<ShipRegistryEntity> readRegistry(final Id registryId) {
    try (var in = getRegistryInputStream(registryId, shipRegistryDirectory);
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read ship registry: '{}'", registryId);
      return toEntities(br);
    } catch (Exception e) {
      log.warn("Unable to read ship registry: '{}'", registryId);
      return Collections.emptyList();
    }
  }

  private InputStream getRegistryInputStream(final Id id, final String baseDirectory) {
    return resourceProvider.getDefaultResourceInputStream(id, baseDirectory);
  }

  private InputStream getShipInputStream(final Id id, final String baseDirectory) {
    return dataProvider.getDataInputStream(id, baseDirectory);
  }

  private ShipEntity toEntity(final BufferedReader bufferedReader, final ShipType shipType) {
    var gson = new Gson();
    return gson.fromJson(bufferedReader, shipType.getClazz());
  }

  private List<ShipRegistryEntity> toEntities(final BufferedReader bufferedReader) {
    Type collectionType = new TypeToken<List<ShipRegistryEntity>>() {
    }.getType();

    var gson = new Gson();
    return gson.fromJson(bufferedReader, collectionType);
  }

  private String toJson(final ShipEntity ship) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(ship);
  }
}
