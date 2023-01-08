package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.MovementEntity;
import com.enigma.waratsea.model.ship.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovementMapper {
  MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

  Movement toModel(MovementEntity movementEntity);
}
