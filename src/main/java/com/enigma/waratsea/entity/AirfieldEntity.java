package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.LandingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirfieldEntity {
  private String name;
  private List<LandingType> landingTypes;
  private int maxCapacity;
  private int antiAir;
  private String gridReference;
}
