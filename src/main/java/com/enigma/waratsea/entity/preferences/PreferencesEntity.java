package com.enigma.waratsea.entity.preferences;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PreferencesEntity {
  private AiPreferencesEntity ai;
}
