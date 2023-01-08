package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.GunEntity;
import com.enigma.waratsea.model.ship.Gun;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GunMapper {
  GunMapper INSTANCE = Mappers.getMapper(GunMapper.class);

  Gun toModel(GunEntity gunEntity);
}
