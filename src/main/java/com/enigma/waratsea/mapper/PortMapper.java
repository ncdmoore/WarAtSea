package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.PortEntity;
import com.enigma.waratsea.model.port.Port;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PortMapper {
  PortMapper INSTANCE = Mappers.getMapper(PortMapper.class);

  Port toModel(PortEntity portEntity);
  PortEntity toEntity(Port port);
}
