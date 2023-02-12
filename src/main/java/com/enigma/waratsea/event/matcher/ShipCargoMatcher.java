package com.enigma.waratsea.event.matcher;

import com.enigma.waratsea.event.action.CargoAction;
import com.enigma.waratsea.event.ship.ShipCargoEvent;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.ship.Ship;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class ShipCargoMatcher {
  private ShipMatcher ship;
  private Set<CargoAction> actions;
  private Port originPort;
  private Set<Port> destinationPorts;

  public boolean match(final ShipCargoEvent event) {
    var candidateShip = event.getShip();
    var candidateAction = event.getAction();
    var candidateOriginPort = event.getOriginPort();
    var candidateDestinationPort = event.getDestinationPort();

    return matchShip(candidateShip)
        && matchAction(candidateAction)
        && matchOriginPort(candidateOriginPort)
        && matchDestinationPort(candidateDestinationPort);
  }

  private boolean matchShip(final Ship candidateShip) {
    return ship == null || ship.match(candidateShip);
  }

  private boolean matchAction(final CargoAction candidateAction) {
    return actions.isEmpty() || actions.contains(candidateAction);
  }

  private boolean matchOriginPort(final Port candidateOriginPort) {
    return originPort == null || originPort.equals(candidateOriginPort);
  }

  private boolean matchDestinationPort(final Port candidateDestinationPort) {
    return destinationPorts.isEmpty() || destinationPorts.contains(candidateDestinationPort);
  }
}
