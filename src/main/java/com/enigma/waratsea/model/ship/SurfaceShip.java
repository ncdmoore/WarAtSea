package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.dto.ArmourDto;
import com.enigma.waratsea.dto.WeaponsDto;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Getter
@Builder
public class SurfaceShip implements Ship {
  private Id id;
  private Id shipClassId;
  private ShipType type;
  private String title;
  private String shipClass;
  private Nation nation;
  private Gun primary;
  private Gun secondary;
  private Gun tertiary;
  private Gun antiAir;
  private Torpedo torpedo;
  private boolean asw;
  private Hull hull;
  private Fuel fuel;
  private Movement movement;
  private Cargo cargo;
  private int victoryPoints;

  @Override
  public Ship commission(final Commission commission) {
    id = commission.getId();
    shipClassId = commission.getShipClassId();
    title = commission.getTitle();

    nation = Optional.ofNullable(commission.getNation())
        .orElse(nation);

    return this;
  }

  @Override
  public Optional<Cargo> retrieveCargo() {
    return Optional.of(cargo);
  }

  @Override
  public WeaponsDto getWeapons() {
    return WeaponsDto.builder()
        .primary(primary)
        .secondary(secondary)
        .tertiary(tertiary)
        .antiAir(antiAir)
        .torpedo(torpedo)
        .asw(asw)
        .build();
  }

  @Override
  public ArmourDto getArmour() {
    return ArmourDto.builder()
        .primary(primary.getArmour())
        .secondary(secondary.getArmour())
        .tertiary(tertiary.getArmour())
        .antiAir(antiAir.getArmour())
        .hull(hull.getArmour())
        .deck(hull.isDeck())
        .build();
  }

  @Override
  public int compareTo(@NotNull final Ship o) {
    return title.compareTo(o.getTitle());
  }

  @Override
  public String toString() {
    return title;
  }
}
