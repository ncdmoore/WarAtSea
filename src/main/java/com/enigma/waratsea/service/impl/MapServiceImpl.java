package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.entity.GameMapEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.model.map.GameMap;
import com.enigma.waratsea.model.map.Grid;
import com.enigma.waratsea.repository.MapRepository;
import com.enigma.waratsea.service.MapService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Singleton
public class MapServiceImpl implements MapService {
  private static final int ALPHABET_SIZE = 26;
  private static final int ASCII_A = 65;

  private final MapRepository mapRepository;
  private GameMap gameMap;

  @Inject
  public MapServiceImpl(final Events events,
                        final MapRepository mapRepository) {
    this.mapRepository = mapRepository;

    events.getLoadMapEvent().register(this::handleLoadMapEvent);
  }

  @Override
  public GameMap get() {
    return Optional.ofNullable(gameMap)
        .orElseGet(this::createMap);
  }

  private void handleLoadMapEvent(final LoadMapEvent event) {
    log.debug("Map service received load map event.");
    createMap();
  }

  private GameMap createMap() {
    gameMap = new GameMap();

    var mapEntity = mapRepository.get();

    buildGrids(mapEntity);
    return gameMap;
  }

  private void buildGrids(final GameMapEntity mapEntity) {
    var rows = mapEntity.getRows();
    var columns = mapEntity.getColumns();
    var defaultGridName = mapEntity.getDefaultGridName();
    var defaultGridType = mapEntity.getDefaultGridType();

    int currentNumberOfRows = rows;

    for (int col = 0; col < columns; col++) {
      for (int row = 0; row < currentNumberOfRows; row++) {
        var reference = determineReference(row, col);
        var type = mapEntity.getGrids().getOrDefault(reference, defaultGridType);
        var name = mapEntity.getLocations().getOrDefault(reference, defaultGridName);

        var grid = Grid.builder()
            .row(row)
            .column(col)
            .reference(reference)
            .name(name)
            .type(type)
            .build();

        gameMap.addGrid(grid);
      }

      currentNumberOfRows = (currentNumberOfRows == rows) ? rows - 1 : rows;
    }
  }

  private String determineReference(final int row, final int column) {
    int alphabetFactor = column / ALPHABET_SIZE;

    return (alphabetFactor == 0)
        ? getSingleLetterColumnAndRow(row, column)
        : getTwoLetterColumnAndRow(row, column, alphabetFactor);
  }

  private String getSingleLetterColumnAndRow(final int row, final int column) {
    int oneBasedRow = row + 1;
    return getSingleLetterColumn(column) + oneBasedRow;
  }

  private String getTwoLetterColumnAndRow(final int row, final int column, final int alphabetFactor) {
    int oneBasedRow = row + 1;
    return getTwoLetterColumn(column, alphabetFactor) + oneBasedRow;
  }

  private String getSingleLetterColumn(final int column) {
    return Character.toString((char) (ASCII_A + column));
  }

  private String getTwoLetterColumn(final int column, final int alphabetFactor) {
    int mod = column % ALPHABET_SIZE;
    char first = (char) (ASCII_A - 1 + alphabetFactor);
    char second = (char) (ASCII_A + mod);
    return first + Character.toString(second);
  }
}
