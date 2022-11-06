package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.PortSize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortEntity {
  private Id id;
  private String title;
  private PortSize size;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
}
