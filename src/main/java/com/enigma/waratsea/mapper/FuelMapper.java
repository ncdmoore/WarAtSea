package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.FuelEntity;
import com.enigma.waratsea.model.ship.Fuel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FuelMapper {
  FuelMapper INSTANCE = Mappers.getMapper(FuelMapper.class);

  Fuel toModel(FuelEntity fuelEntity);
}
