package com.enigma.waratsea.model.release;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.matcher.ShipCombatMatcher;
import com.enigma.waratsea.event.ship.ShipCombatEvent;
import com.enigma.waratsea.model.taskForce.TaskForce;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static com.enigma.waratsea.model.AssetState.ACTIVE;

@Getter
@Builder
public class ShipCombatRelease implements Release {
  private String id;
  private String description;
  private ShipCombatMatcher matcher;
  private Set<TaskForce> taskForces;

  @Override
  public void registerEvents(final Events events) {
    events.getShipCombatEvent().register(this::handleShipCombatEvent);
  }

  private void handleShipCombatEvent(final ShipCombatEvent shipCombatEvent) {
    if (matcher.match(shipCombatEvent)) {
      taskForces.forEach(this::setTaskForceActive);
    }
  }

  private void setTaskForceActive(final TaskForce taskForce) {
    taskForce.setState(ACTIVE);
  }
}
