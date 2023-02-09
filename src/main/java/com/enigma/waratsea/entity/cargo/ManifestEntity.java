package com.enigma.waratsea.entity.cargo;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ManifestEntity {
  private String id;
  private String originPort;
  private Set<String> destinationPorts;
  private Set<Id> ships;
}
