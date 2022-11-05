package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.PortSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortEntity {
  private Id id;
  private String title;
  private PortSize size;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
}
