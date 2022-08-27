package com.enigma.watatsea.strategy;

import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.TimeRange;
import com.enigma.waratsea.strategy.DefaultVisibilityStrategy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.enigma.waratsea.model.Visibility.GOOD;
import static com.enigma.waratsea.model.Visibility.BAD;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DefaultVisibilityStrategyTest {
  @InjectMocks
  private DefaultVisibilityStrategy defaultVisibilityStrategy;

  @ParameterizedTest
  @EnumSource(value = TimeRange.class, names = {"DAY_1", "DAY_2", "DAY_3"})
  void testDetermineVisibilityDay(TimeRange timeRange) {
    var turn = Turn.builder().timeRange(timeRange).build();
    var visibility = defaultVisibilityStrategy.determine(turn);

    assertEquals(GOOD, visibility);
  }

  @ParameterizedTest
  @EnumSource(value = TimeRange.class, names = {"TWILIGHT", "NIGHT_1", "NIGHT_2"})
  void testDetermineVisibilityNight(TimeRange timeRange) {
    var turn = Turn.builder().timeRange(timeRange).build();
    var visibility = defaultVisibilityStrategy.determine(turn);

    assertEquals(BAD, visibility);
  }
}
