package com.enigma.watatsea.service;

import com.enigma.waratsea.mapper.PreferencesMapper;
import com.enigma.waratsea.model.preferences.Preferences;
import com.enigma.waratsea.repository.PreferencesRepository;
import com.enigma.waratsea.service.impl.PreferencesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PreferencesServiceTest {
  @InjectMocks
  private PreferencesServiceImpl  preferencesService;

  @Mock
  @SuppressWarnings("unused")
  private PreferencesRepository preferencesRepository;

  @Mock
  private PreferencesMapper preferencesMapper;

  @Test
  void shouldGetPreferences() {

    given(preferencesMapper.toModel(any())).willReturn(mockPreferences());

    var result = preferencesService.get();

    assertNotNull(result);
  }

  private Preferences mockPreferences() {
    return Preferences.builder().build();
  }
}
