package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
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
  private AircraftService aircraftService;

  public abstract Squadron toModel(SquadronEntity squadronEntity);

  public abstract SquadronEntity toEntity(Squadron squadron);

  Aircraft mapAircraft(final Id aircraftId) {
    return aircraftService.get(aircraftId);
  }

  Id mapId(final Aircraft aircraft) {
    return aircraft.getId();
  }
}
