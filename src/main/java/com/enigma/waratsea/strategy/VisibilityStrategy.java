package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Visibility;

public interface VisibilityStrategy {
  Visibility determine(Turn turn);
}
