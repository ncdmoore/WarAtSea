package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.ShipRegistryEntity;
import com.enigma.waratsea.model.ship.ShipRegistry;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipRegistryMapper {
  ShipRegistryMapper INSTANCE = Mappers.getMapper(ShipRegistryMapper.class);

  ShipRegistry toModel(ShipRegistryEntity entity);
}
