package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.ShipRegistryEntity;
import com.enigma.waratsea.model.ship.ShipRegistry;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShipRegistryMapper {
  public static final ShipRegistryMapper INSTANCE = Mappers.getMapper(ShipRegistryMapper.class);

  public abstract ShipRegistry toModel(ShipRegistryEntity entity);
}
