package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionEntity {
  private String name;
  private Nation nation;
  private String min; // in steps.
  private String max; // in steps.
  private List<Id> airfields;
  private List<Id> ports;
  private String gridReference;
}
