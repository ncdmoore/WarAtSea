package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.TimeRange;
import com.enigma.waratsea.model.Visibility;
import com.google.inject.Singleton;

import java.util.Map;

import static com.enigma.waratsea.model.TimeRange.*;
import static com.enigma.waratsea.model.Visibility.GOOD;
import static com.enigma.waratsea.model.Visibility.BAD;

@Singleton
public class DefaultVisibilityStrategy implements VisibilityStrategy {
  private final Map<TimeRange, Visibility> turnTypeToVisibilityType = Map.of(
      DAY_1, GOOD,
      DAY_2, GOOD,
      DAY_3, GOOD,
      TWILIGHT, BAD,
      NIGHT_1, BAD,
      NIGHT_2, BAD
  );

  @Override
  public Visibility determine(final Turn turn) {
    var timeRange = turn.getTimeRange();
    return turnTypeToVisibilityType.get(timeRange);
  }
}
