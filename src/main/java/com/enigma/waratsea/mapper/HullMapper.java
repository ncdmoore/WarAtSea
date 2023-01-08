package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.HullEntity;
import com.enigma.waratsea.model.ship.Hull;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HullMapper {
  HullMapper INSTANCE = Mappers.getMapper(HullMapper.class);

  Hull toModel(HullEntity hullEntity);
}
