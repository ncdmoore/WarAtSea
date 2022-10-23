package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.LandingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirfieldEntity {
  private String id;
  private String title;
  private List<LandingType> landingTypes;
  private int maxCapacity;
  private int capacity;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
}
