package com.enigma.waratsea.service;

import com.enigma.waratsea.model.preferences.Preferences;

public interface PreferencesService {
  Preferences get();
  void save(Preferences preferences);
}
