package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.map.Region;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.service.AirfieldService;
import com.enigma.waratsea.service.PortService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public abstract class RegionMapper {
  public static final RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

  @Setter
  private AirfieldService airfieldService;

  @Setter
  private PortService portService;

  public abstract Region toModel(RegionEntity regionEntity);

  List<Airfield> mapAirfields(final List<Id> airfieldIds) {
    return Optional.ofNullable(airfieldIds)
        .map(ids -> airfieldService.get(ids))
        .orElse(Collections.emptyList());
  }

  Set<Port> mapPorts(final Set<Id> portIds) {
    return Optional.ofNullable(portIds)
        .map(ids -> portService.get(ids))
        .orElse(Collections.emptySet());
  }
}
