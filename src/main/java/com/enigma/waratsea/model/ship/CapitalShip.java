package com.enigma.waratsea.model.ship;

import com.enigma.waratsea.dto.ArmourDto;
import com.enigma.waratsea.dto.WeaponsDto;
import com.enigma.waratsea.model.airbase.Airbase;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.aircraft.LandingType;
import com.enigma.waratsea.model.squadron.Squadron;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

import static com.enigma.waratsea.model.squadron.DeploymentState.ON_SHIP;
import static com.enigma.waratsea.model.squadron.SquadronState.READY;

@Getter
@Builder
public class CapitalShip implements Ship, Airbase {
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
  private Hull hull;
  private Fuel fuel;
  private Movement movement;
  private Set<LandingType> landingType;
  private Catapult catapult;
  private Cargo cargo;
  private int victoryPoints;
  private Set<Squadron> squadrons;

  @Override
  public Set<Nation> getNations() {
    return Set.of(nation);
  }

  @Override
  public Ship commission(final Commission commission) {
    id = commission.getId();
    shipClassId = commission.getShipClassId();
    title = commission.getTitle();

    nation = Optional.ofNullable(commission.getNation())
        .orElse(nation);

    commission.getSquadrons().forEach(this::deploySquadron);
    return this;
  }

  @Override
  public boolean isOperational() {
    return catapult != null && catapult.getCapacity() > 0;
  }

  @Override
  public void deploySquadron(final Squadron squadron) {
    squadrons.add(squadron);
    squadron.setDeploymentState(ON_SHIP);
    squadron.setState(READY);
    squadron.setAirbase(this);
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
