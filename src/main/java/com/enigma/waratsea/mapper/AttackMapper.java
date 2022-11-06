package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.aircraft.Attack;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface AttackMapper {
  AttackMapper INSTANCE = Mappers.getMapper(AttackMapper.class);

  Attack toModel(AirfieldEntity airfieldEntity);
}
