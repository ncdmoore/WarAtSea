package com.enigma.waratsea.strategy;

import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Visibility;

public interface VisibilityStrategy {
  Visibility determine(Turn turn);
}
