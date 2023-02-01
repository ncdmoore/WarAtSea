package com.enigma.watatsea.strategy;

import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Weather;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.service.WeatherInput;
import com.enigma.waratsea.strategy.DefaultWeatherStrategy;
import com.enigma.watatsea.mock.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.enigma.waratsea.model.WeatherType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DefaultWeatherStrategyTest {
  @InjectMocks
  private DefaultWeatherStrategy defaultWeatherStrategy;

  @Mock
  private DiceService diceService;

  @Test
  void testDieResultOne() {
    var currentWeather = Weather.builder().weatherType(CLEAR).build();
    var turn = Turn.builder().build();
    var input = WeatherInput.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die1());

    var newWeather = defaultWeatherStrategy.determine(input);

    assertEquals(CLEAR, newWeather);
  }

  @Test
  void testDieResultTwo() {
    var currentWeather = Weather.builder().weatherType(CLOUDY).build();
    var turn = Turn.builder().build();
    var input = WeatherInput.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die2());

    var newWeather = defaultWeatherStrategy.determine(input);

    assertEquals(CLEAR, newWeather);
  }

  @Test
  void testDieResultThree() {
    var currentWeather = Weather.builder().weatherType(RAIN).build();
    var turn = Turn.builder().build();
    var input = WeatherInput.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die3());

    var newWeather = defaultWeatherStrategy.determine(input);

    assertEquals(RAIN, newWeather);
  }

  @Test
  void testDieResultFour() {
    var currentWeather = Weather.builder().weatherType(GALE).build();
    var turn = Turn.builder().build();
    var input = WeatherInput.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die4());

    var newWeather = defaultWeatherStrategy.determine(input);

    assertEquals(GALE, newWeather);
  }

  @Test
  void testDieResultFive() {
    var currentWeather = Weather.builder().weatherType(CLEAR).build();
    var turn = Turn.builder().build();
    var input = WeatherInput.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die5());

    var newWeather = defaultWeatherStrategy.determine(input);

    assertEquals(CLOUDY, newWeather);
  }

  @Test
  void testDieResultSix() {
    var currentWeather = Weather.builder().weatherType(CLOUDY).build();
    var turn = Turn.builder().build();
    var input = WeatherInput.builder().weather(currentWeather).turn(turn).build();

    given(diceService.get()).willReturn(new Die6());

    var newWeather = defaultWeatherStrategy.determine(input);

    assertEquals(RAIN, newWeather);
  }
}
