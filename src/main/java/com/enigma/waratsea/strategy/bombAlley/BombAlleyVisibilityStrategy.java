package com.enigma.waratsea.strategy.bombAlley;

import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.turn.TimeRange;
import com.enigma.waratsea.model.weather.Visibility;
import com.enigma.waratsea.strategy.VisibilityStrategy;
import com.google.inject.Singleton;

import java.time.Month;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.enigma.waratsea.model.turn.TimeRange.*;
import static com.enigma.waratsea.model.turn.TimeRange.NIGHT_2;
import static com.enigma.waratsea.model.weather.Visibility.GOOD;
import static com.enigma.waratsea.model.weather.Visibility.BAD;

@Singleton
public class BombAlleyVisibilityStrategy implements VisibilityStrategy {
  private final Map<TimeRange, Function<Month, Visibility>> turnTypeToVisibilityType = Map.of(
      DAY_1, month -> GOOD,
      DAY_2, month -> GOOD,
      DAY_3, month -> GOOD,
      TWILIGHT, this::determineTwilightVisibility,
      NIGHT_1, month -> BAD,
      NIGHT_2, month -> BAD
  );

  private final Set<Month> winterMonths = Set.of(
      Month.JANUARY,
      Month.FEBRUARY,
      Month.NOVEMBER,
      Month.DECEMBER
  );

  @Override
  public Visibility determine(final Turn turn) {
    var timeRange = turn.getTimeRange();
    var month = turn.getDate().getMonth();
    return turnTypeToVisibilityType.get(timeRange).apply(month);
  }

  private Visibility determineTwilightVisibility(final Month month) {
    return winterMonths.contains(month) ? BAD : GOOD;
  }
}
