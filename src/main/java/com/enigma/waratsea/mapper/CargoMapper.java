package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.CargoEntity;
import com.enigma.waratsea.model.ship.Cargo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CargoMapper {
  CargoMapper INSTANCE = Mappers.getMapper(CargoMapper.class);

  Cargo toModel(CargoEntity cargoEntity);
  CargoEntity toEntity(Cargo cargo);
}
