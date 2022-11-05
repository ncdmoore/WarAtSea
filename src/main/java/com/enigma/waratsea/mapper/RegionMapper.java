package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Port;
import com.enigma.waratsea.model.Region;
import com.enigma.waratsea.service.AirfieldService;
import com.enigma.waratsea.service.PortService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330")
public abstract class RegionMapper {
  public static final RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

  @Inject
  @SuppressWarnings("unused")
  private AirfieldService airfieldService;

  @Inject
  @SuppressWarnings("unused")
  private PortService portService;

  abstract public Region toModel(RegionEntity regionEntity);

  List<Airfield> mapAirfields(List<Id> airfieldIds) {
    return airfieldService.get(airfieldIds);
  }

  List<Port> mapPorts(List<Id> portIds) {
    return portService.get(portIds);
  }
}
