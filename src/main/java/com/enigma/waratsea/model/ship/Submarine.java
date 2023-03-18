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
public class Submarine implements Ship {
  private Id id;
  private Id shipClassId;
  private ShipType type;
  private String title;
  private String shipClass;
  private Nation nation;
  private Torpedo torpedo;
  private Movement movement;
  private Fuel fuel;
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
    return Optional.empty();
  }

  @Override
  public WeaponsDto getWeapons() {
    return WeaponsDto.builder()
        .torpedo(torpedo)
        .build();
  }

  @Override
  public ArmourDto getArmour() {
    return ArmourDto.builder().build();
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
