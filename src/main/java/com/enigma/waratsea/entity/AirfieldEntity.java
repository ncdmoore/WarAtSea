package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.LandingType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AirfieldEntity {
  private Id id;
  private String title;
  private List<LandingType> landingTypes;
  private int maxCapacity;
  private int capacity;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
}
