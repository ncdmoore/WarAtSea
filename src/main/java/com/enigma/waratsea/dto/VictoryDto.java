package com.enigma.waratsea.dto;

import com.enigma.waratsea.event.airfield.AirfieldCombatEvent;
import com.enigma.waratsea.event.airfield.AirfieldEvent;
import com.enigma.waratsea.event.port.PortCombatEvent;
import com.enigma.waratsea.event.ship.*;
import com.enigma.waratsea.event.squadron.SquadronCombatEvent;
import com.enigma.waratsea.event.squadron.SquadronMovementEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VictoryDto {
  private final AirfieldCombatEvent airfieldCombatEvent;
  private final AirfieldEvent airfieldEvent;
  private final PortCombatEvent portCombatEvent;
  private final ShipCargoEvent shipCargoEvent;
  private final ShipCombatEvent shipCombatEvent;
  private final ShipContactEvent shipContactEvent;
  private final ShipFuelEvent shipFuelEvent;
  private final ShipMinefieldEvent shipMinefieldEvent;
  private final ShipMovementEvent shipMovementEvent;
  private final SquadronCombatEvent squadronCombatEvent;
  private final SquadronMovementEvent squadronMovementEvent;
}
