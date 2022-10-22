package com.enigma.waratsea.model;

import lombok.Data;

import java.util.List;

@Data
public class Region {
  private String name;
  private Nation nation;
  private String min; // in steps.
  private String max; // in steps.
  private List<Airfield> airfields;
  private List<String> ports;
  private String gridReference;
}
