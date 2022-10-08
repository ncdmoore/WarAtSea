package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.Turn;
import com.enigma.waratsea.model.Weather;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity {
  private GameName gameName;
  private String id;
  private int scenario;
  private Side humanSide;
  private Turn turn;
  private Weather weather;
}
