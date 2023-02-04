package com.enigma.waratsea.model.port;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Port {
  private Id id;
  private String title;
  private PortSize size;
  private int maxAntiAir;
  private int antiAir;
  private String gridReference;
}
