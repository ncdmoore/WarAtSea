package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.mapper.PreferencesMapper;
import com.enigma.waratsea.model.preferences.Preferences;
import com.enigma.waratsea.repository.PreferencesRepository;
import com.enigma.waratsea.service.PreferencesService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PreferencesServiceImpl implements PreferencesService {
  private final PreferencesRepository preferencesRepository;
  private final PreferencesMapper preferencesMapper;

  @Override
  public Preferences get() {
    var entity = preferencesRepository.get();

    return preferencesMapper.toModel(entity);
  }

  @Override
  public void save(final Preferences preferences) {
    var entity = preferencesMapper.toEntity(preferences);

    preferencesRepository.save(entity);
  }
}
