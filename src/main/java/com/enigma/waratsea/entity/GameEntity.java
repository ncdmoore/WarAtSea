package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.turn.Turn;
import com.enigma.waratsea.model.weather.Weather;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameEntity {
  private GameName gameName;
  private String id;
  private int scenario;
  private Side humanSide;
  private Turn turn;
  private Weather weather;
}
