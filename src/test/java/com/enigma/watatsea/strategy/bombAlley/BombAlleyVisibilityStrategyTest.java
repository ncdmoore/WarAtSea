package com.enigma.watatsea.strategy.bombAlley;

import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.TimeRange;
import com.enigma.waratsea.strategy.bombAlley.BombAlleyVisibilityStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.enigma.waratsea.model.TimeRange.TWILIGHT;
import static com.enigma.waratsea.model.Visibility.BAD;
import static com.enigma.waratsea.model.Visibility.GOOD;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BombAlleyVisibilityStrategyTest {
  @InjectMocks
  private BombAlleyVisibilityStrategy bombAlleyVisibilityStrategy;

  private final String summerDay = "1940-06-10";
  private final String winterDay = "1940-01-01";

  @ParameterizedTest
  @EnumSource(value = TimeRange.class, names = {"DAY_1", "DAY_2", "DAY_3"})
  void testDetermineDayVisibilitySummerDay(TimeRange timeRange) {
    var date = LocalDate.parse(summerDay);
    var turn = Turn.builder().timeRange(timeRange).date(date).build();

    var visibility = bombAlleyVisibilityStrategy.determine(turn);

    assertEquals(GOOD, visibility);
  }

  @ParameterizedTest
  @EnumSource(value = TimeRange.class, names = {"DAY_1", "DAY_2", "DAY_3"})
  void testDetermineDayVisibilityWinterDay(TimeRange timeRange) {
    var date = LocalDate.parse(winterDay);
    var turn = Turn.builder().timeRange(timeRange).date(date).build();

    var visibility = bombAlleyVisibilityStrategy.determine(turn);

    assertEquals(GOOD, visibility);
  }

  @Test
  void testDetermineVisibilityTwilightSummerDay() {
    var date = LocalDate.parse(summerDay);
    var turn = Turn.builder().timeRange(TWILIGHT).date(date).build();

    var visibility = bombAlleyVisibilityStrategy.determine(turn);

    assertEquals(GOOD, visibility);
  }

  @Test
  void testDetermineVisibilityTwilightWinterDay() {
    var date = LocalDate.parse(winterDay);
    var turn = Turn.builder().timeRange(TWILIGHT).date(date).build();

    var visibility = bombAlleyVisibilityStrategy.determine(turn);

    assertEquals(BAD, visibility);
  }

  @ParameterizedTest
  @EnumSource(value = TimeRange.class, names = {"NIGHT_1", "NIGHT_2"})
  void testDetermineVisibilityNight(TimeRange timeRange) {
    var date = LocalDate.parse(summerDay);
    var turn = Turn.builder().timeRange(timeRange).date(date).build();

    var visibility = bombAlleyVisibilityStrategy.determine(turn);

    assertEquals(BAD, visibility);
  }
}
