package com.enigma.watatsea.strategy.arcticConvoy;

import com.enigma.waratsea.dto.WeatherDto;
import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Weather;
import com.enigma.waratsea.model.weather.WeatherType;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.strategy.arcticConvoy.ArcticConvoyWeatherStrategy;
import com.enigma.watatsea.mock.Die1;
import com.enigma.watatsea.mock.Die2;
import com.enigma.watatsea.mock.Die3;
import com.enigma.watatsea.mock.Die4;
import com.enigma.watatsea.mock.Die5;
import com.enigma.watatsea.mock.Die6;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static com.enigma.waratsea.model.weather.WeatherType.CLEAR;
import static com.enigma.waratsea.model.weather.WeatherType.CLOUDY;
import static com.enigma.waratsea.model.weather.WeatherType.GALE;
import static com.enigma.waratsea.model.weather.WeatherType.RAIN;
import static com.enigma.waratsea.model.weather.WeatherType.SQUALL;
import static com.enigma.waratsea.model.weather.WeatherType.STORM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArcticConvoyWeatherStrategyTest {
  @InjectMocks
  private ArcticConvoyWeatherStrategy arcticConvoyWeatherStrategy;

  @Mock
  private DiceService diceService;

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY", "RAIN", "SQUALL", "STORM", "GALE"})
  void shouldGetClearWhenRollOneWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die1());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLEAR, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY"})
  void shouldGetClearWhenRollTwoWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die2());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLEAR, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN", "SQUALL", "STORM", "GALE"})
  void shouldGetCloudyWhenRollTwoWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die2());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR"})
  void shouldGetClearWhenRollThreeWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die3());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLEAR, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLOUDY"})
  void shouldGetCloudyWhenRollThreeWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die3());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN", "SQUALL", "STORM", "GALE"})
  void shouldGetRainWhenRollThreeWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die3());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(RAIN, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR"})
  void shouldGetCloudyWhenRollFourWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die4());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLOUDY", "RAIN"})
  void shouldGetRainWhenRollFourWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die4());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(RAIN, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"SQUALL", "STORM", "GALE"})
  void shouldGetSquallWhenRollFourWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die4());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(SQUALL, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY"})
  void shouldGetRainWhenRollFiveWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die5());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(RAIN, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN", "SQUALL"})
  void shouldGetSquallWhenRollFiveWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die5());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(SQUALL, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"STORM", "GALE"})
  void shouldGetStormWhenRollFiveWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die5());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(STORM, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY", "RAIN"})
  void shouldGetSquallWhenRollSixWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die6());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(SQUALL, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"SQUALL"})
  void shouldGetStormWhenRollSixWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die6());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(STORM, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"STORM"})
  void shouldGetGaleWhenRollSixWinter(final WeatherType currentWeather) {
    var month = Month.JANUARY;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die6());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(GALE, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY"})
  void shouldGetClearWhenRollOneSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die1());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLEAR, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN", "SQUALL", "STORM", "GALE"})
  void shouldGetCloudyWhenRollOneSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die1());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY"})
  void shouldGetClearWhenRollTwoSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die2());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLEAR, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN"})
  void shouldGetCloudyWhenRollTwoSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die2());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"SQUALL", "STORM", "GALE"})
  void shouldGetRainWhenRollTwoSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die2());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(RAIN, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY"})
  void shouldGetCloudyWhenRollThreeSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die3());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"STORM"})
  void shouldGetSquallWhenRollThreeSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die3());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(SQUALL, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN", "SQUALL", "GALE"})
  void shouldGetRainWhenRollThreeSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die3());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(RAIN, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR", "CLOUDY"})
  void shouldGetCloudyWhenRollFourSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die4());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN"})
  void shouldGetRainWhenRollFourSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die4());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(RAIN, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"SQUALL", "STORM", "GALE"})
  void shouldGetSquallWhenRollFourSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die4());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(SQUALL, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLEAR"})
  void shouldGetCloudyWhenRollFiveSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die5());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"CLOUDY"})
  void shouldGetRainWhenRollFiveSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die5());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(RAIN, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"RAIN", "SQUALL"})
  void shouldGetSquallWhenRollFiveSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die5());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(SQUALL, result);
  }

  @ParameterizedTest
  @EnumSource(value = WeatherType.class, names = {"STORM", "GALE"})
  void shouldGetStormWhenRollFiveSummer(final WeatherType currentWeather) {
    var month = Month.JUNE;
    var dto = buildDto(month, currentWeather);

    given(diceService.get()).willReturn(new Die5());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(STORM, result);
  }

  @Test
  void shouldWorsenWhenRollSixSummer() {
    var month = Month.JUNE;
    var dto = buildDto(month, CLEAR);

    given(diceService.get()).willReturn(new Die6());

    var result = arcticConvoyWeatherStrategy.determine(dto);

    assertEquals(CLOUDY, result);
  }

  private WeatherDto buildDto(final Month month, final WeatherType weatherType) {
    var date = LocalDate.of(1941, month, 1);

    var turn = Turn.builder()
        .date(date)
        .build();

    var weather = Weather.builder()
        .weatherType(weatherType)
        .build();

    return WeatherDto.builder()
        .turn(turn)
        .weather(weather)
        .build();
  }
}
