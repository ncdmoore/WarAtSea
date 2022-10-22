package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.Airfield;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AirfieldMapper {
  AirfieldMapper INSTANCE = Mappers.getMapper(AirfieldMapper.class);

  List<Airfield> toModels(List<AirfieldEntity> airfieldEntities);

  Airfield toModel(AirfieldEntity airfieldEntity);
}
