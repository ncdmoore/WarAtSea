package com.enigma.watatsea.repository;

import com.enigma.waratsea.repository.impl.PreferencesRepositoryImpl;
import com.enigma.waratsea.repository.provider.PreferencesProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.ai.strategy.commandStrategy.CommandStrategyType.BALANCED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PreferencesRepositoryTest {
  @InjectMocks
  private PreferencesRepositoryImpl preferencesRepository;

  @Mock
  private PreferencesProvider preferencesProvider;

  private final static String PREFERENCES_DIRECTORY = "preferences";
  private final static String PREFERENCES_FILE_NAME = "preferences";

  @Test
  void shouldGetPreferences() {
    var inputStream = getInputStream();

    given(preferencesProvider.getInputStream()).willReturn(inputStream);

    var result = preferencesRepository.get();

    assertNotNull(result);
    assertNotNull(result.getAi());
    assertEquals(BALANCED, result.getAi().getCommandStrategy());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get(  "/", PREFERENCES_DIRECTORY, PREFERENCES_FILE_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
