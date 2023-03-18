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
public class SurfaceShipEntity implements ShipEntity {
  private Id id;
  private Id shipClassId;
  private ShipType type;
  private String title;
  private String shipClass;
  private Nation nation;

  @Builder.Default
  private GunEntity primary = GunEntity.builder().build();

  @Builder.Default
  private GunEntity secondary = GunEntity.builder().build();

  @Builder.Default
  private GunEntity tertiary = GunEntity.builder().build();

  @Builder.Default
  private GunEntity antiAir = GunEntity.builder().build();

  private TorpedoEntity torpedo;
  private boolean asw;
  private HullEntity hull;
  private FuelEntity fuel;
  private MovementEntity movement;
  private CargoEntity cargo;
  private int victoryPoints;
}
