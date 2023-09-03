package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.service.AircraftService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SquadronMapper {
  public static final SquadronMapper INSTANCE = Mappers.getMapper(SquadronMapper.class);

  @Setter
  private AircraftService aircraftService;

  public abstract Squadron toModel(SquadronEntity squadronEntity);

  public abstract SquadronEntity toEntity(Squadron squadron);

  Aircraft mapAircraft(final Id aircraftId) {
    return aircraftService.get(aircraftId);
  }

  Id mapAircraftId(final Aircraft aircraft) {
    return aircraft.getId();
  }
}
