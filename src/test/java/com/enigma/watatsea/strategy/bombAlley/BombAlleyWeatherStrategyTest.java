package com.enigma.watatsea.strategy.bombAlley;

import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Weather;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.dto.WeatherDto;
import com.enigma.waratsea.strategy.bombAlley.BombAlleyWeatherStrategy;
import com.enigma.watatsea.mock.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.enigma.waratsea.model.turn.TimeRange.DAY_1;
import static com.enigma.waratsea.model.weather.WeatherType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BombAlleyWeatherStrategyTest {
  @InjectMocks
  private BombAlleyWeatherStrategy bombAlleyWeatherStrategy;

  @SuppressWarnings("unused")
  @Mock
  private DiceService diceService;

  private final String summerDay = "1940-06-10";
  private final String winterDay = "1940-01-01";

  @ParameterizedTest
  @ValueSource(strings = {summerDay, winterDay})
  void testDieResultOne(String day) {
    var date = LocalDate.parse(day);
    var currentWeather = Weather.builder().weatherType(CLEAR).build();
    var turn = Turn.builder().timeRange(DAY_1).date(date).build();
    var input = WeatherDto.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die1());

    var newWeather = bombAlleyWeatherStrategy.determine(input);

    assertEquals(CLEAR, newWeather);
  }

  @ParameterizedTest
  @ValueSource(strings = {summerDay, winterDay})
  void testDieResultTwo(String day) {
    var date = LocalDate.parse(day);
    var currentWeather = Weather.builder().weatherType(GALE).build();
    var turn = Turn.builder().timeRange(DAY_1).date(date).build();
    var input = WeatherDto.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die2());

    var newWeather = bombAlleyWeatherStrategy.determine(input);

    assertEquals(STORM, newWeather);
  }

  @ParameterizedTest
  @ValueSource(strings = {summerDay, winterDay})
  void testDieResultThree(String day) {
    var date = LocalDate.parse(day);
    var currentWeather = Weather.builder().weatherType(GALE).build();
    var turn = Turn.builder().timeRange(DAY_1).date(date).build();
    var input = WeatherDto.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die3());

    var newWeather = bombAlleyWeatherStrategy.determine(input);

    assertEquals(GALE, newWeather);
  }

  @ParameterizedTest
  @ValueSource(strings = {summerDay, winterDay})
  void testDieResultFour(String day) {
    var date = LocalDate.parse(day);
    var currentWeather = Weather.builder().weatherType(RAIN).build();
    var turn = Turn.builder().timeRange(DAY_1).date(date).build();
    var input = WeatherDto.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die4());

    var newWeather = bombAlleyWeatherStrategy.determine(input);

    assertEquals(RAIN, newWeather);
  }

  @Test
  void testSummerDieResultFive() {
    var date = LocalDate.parse(summerDay);
    var currentWeather = Weather.builder().weatherType(RAIN).build();
    var turn = Turn.builder().timeRange(DAY_1).date(date).build();
    var input = WeatherDto.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die5());

    var newWeather = bombAlleyWeatherStrategy.determine(input);

    assertEquals(RAIN, newWeather);
  }

  @Test
  void testWinterDieResultFive() {
    var date = LocalDate.parse(winterDay);
    var currentWeather = Weather.builder().weatherType(RAIN).build();
    var turn = Turn.builder().timeRange(DAY_1).date(date).build();
    var input = WeatherDto.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die5());

    var newWeather = bombAlleyWeatherStrategy.determine(input);

    assertEquals(SQUALL, newWeather);
  }

  @ParameterizedTest
  @ValueSource(strings = {summerDay, winterDay})
  void testDieResultSix(String day) {
    var date = LocalDate.parse(day);
    var currentWeather = Weather.builder().weatherType(SQUALL).build();
    var turn = Turn.builder().timeRange(DAY_1).date(date).build();
    var input = WeatherDto.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die6());

    var newWeather = bombAlleyWeatherStrategy.determine(input);

    assertEquals(STORM, newWeather);
  }
}
