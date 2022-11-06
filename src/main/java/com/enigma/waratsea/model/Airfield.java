package com.enigma.waratsea.model;

import com.enigma.waratsea.model.aircraft.LandingType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Airfield {
  private Id id;
  private String title;
  private List<LandingType> landingTypes;
  private int maxCapacity;
  private int capacity;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
}
