package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Game;

import java.util.List;

public interface GameService extends BootStrapped {
  List<Game> get();
}
