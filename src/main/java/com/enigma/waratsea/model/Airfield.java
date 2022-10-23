package com.enigma.waratsea.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Airfield {
  private String id;
  private String title;
  private List<LandingType> landingTypes;
  private int maxCapacity;
  private int capacity;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
}
