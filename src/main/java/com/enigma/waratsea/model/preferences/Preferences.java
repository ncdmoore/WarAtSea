package com.enigma.waratsea.model.preferences;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Preferences {
  private AiPreferences ai;
}
