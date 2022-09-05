package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Weather;
import lombok.Data;

@Data
public class GameEntity {
  private final GameName gameName;
  private final String id;
  private final int scenario;
  private final Side humanSide;
  private final Turn turn;
  private final Weather weather;
}