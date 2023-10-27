package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Weather;
import com.enigma.waratsea.property.AppProps;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.impl.GameRepositoryImpl;
import com.enigma.waratsea.repository.provider.GamePaths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.weather.Visibility.GOOD;
import static com.enigma.waratsea.model.weather.WeatherType.CLEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameRepositoryTest {
  @InjectMocks
  private GameRepositoryImpl gameRepository;

  @Mock
  private AppProps props;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String DATE_FORMAT = "MM/dd/yyyy";
  private static final String GAMES_DIRECTORY = "games";
  private static final String GAME = "game";

  @Test
  void shouldGetGameEntities() throws Exception {
    var paths = List.of(Path.of("game"));

    gamePaths.setGameDirectories(BOMB_ALLEY);
    var savedGameDirectory = gamePaths.getSavedGameDirectory();

    var inputStream = getInputStream();

    given(props.getString(anyString())).willReturn(DATE_FORMAT);
    given(dataProvider.getSubDirectoryPaths(savedGameDirectory)).willReturn(paths);
    given(dataProvider.getSavedFileInputStream(any())).willReturn(inputStream);

    var results = gameRepository.get();

    assertNotNull(results);
    assertEquals(1, results.size());

    var game = results.get(0);

    assertEquals(BOMB_ALLEY, game.getGameName());
    assertEquals("1-FirstSortie-default", game.getId());
    assertEquals(1, game.getScenario());
    assertEquals(ALLIES, game.getHumanSide());
    assertEquals(Turn.builder().number(1).date(LocalDate.of(1940, 6, 11)).build(), game.getTurn());
    assertEquals(Weather.builder().weatherType(CLEAR).visibility(GOOD).build(), game.getWeather());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", GAMES_DIRECTORY, GAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
