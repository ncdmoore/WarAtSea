package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.Airfield;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AirfieldMapper {
  AirfieldMapper INSTANCE = Mappers.getMapper(AirfieldMapper.class);

  Airfield toModel(AirfieldEntity airfieldEntity);
  AirfieldEntity toEntity(Airfield airfield);

}
