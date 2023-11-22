package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.preferences.PreferencesEntity;

public interface PreferencesRepository {
  PreferencesEntity get();
  void save(PreferencesEntity preferences);
}
