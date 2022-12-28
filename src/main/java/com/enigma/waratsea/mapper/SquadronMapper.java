package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.SquadronEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.service.AircraftService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330")
public abstract class SquadronMapper {
  public static final SquadronMapper INSTANCE = Mappers.getMapper(SquadronMapper.class);

  @Inject
  public AircraftService aircraftService;

  abstract public Squadron toModel(final SquadronEntity squadronEntity);
  abstract public SquadronEntity toEntity(final Squadron squadron);

  Aircraft mapAircraft(Id aircraftId) {
    return aircraftService.get(aircraftId);
  }

  Id mapId(Aircraft aircraft) {
    return aircraft.getId();
  }
}
