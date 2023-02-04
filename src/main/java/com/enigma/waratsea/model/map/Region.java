package com.enigma.waratsea.model.map;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.port.Port;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Region {
  private String name;
  private Nation nation;
  private String min; // in steps.
  private String max; // in steps.
  private List<Airfield> airfields;
  private List<Port> ports;
  private String gridReference;
}
