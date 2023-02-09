package com.enigma.waratsea.entity.ship;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.ship.ShipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotorTorpedoBoatEntity implements ShipEntity {
  private Id id;
  private ShipType type;
  private String shipClass;
  private Nation nation;
  private TorpedoEntity torpedo;
  private MovementEntity movement;
  private FuelEntity fuel;
  private int victoryPoints;
}
