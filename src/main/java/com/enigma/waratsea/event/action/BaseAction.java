package com.enigma.waratsea.event.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseAction {
  AIRFIELD_ATTACKED("Airfield attacked"),
  AIRFIELD_DAMAGED("Airfield damaged"),

  PORT_ATTACKED("Port attacked"),
  PORT_DAMAGED("Port damaged"),

  AIRFIELD_REPAIR("Airfield Repair");

  private final String value;
}
