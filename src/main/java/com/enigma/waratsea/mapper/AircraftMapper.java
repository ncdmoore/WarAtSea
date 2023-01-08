package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.aircraft.AircraftEntity;
import com.enigma.waratsea.model.aircraft.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AircraftMapper {
  AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);

  Aircraft toModel(AircraftEntity aircraftEntity);
}
