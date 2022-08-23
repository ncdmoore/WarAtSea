package com.enigma.waratsea.mappers;

import com.enigma.waratsea.entity.ScenarioEntity;
import com.enigma.waratsea.model.Scenario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScenarioMapper {
  ScenarioMapper INSTANCE = Mappers.getMapper(ScenarioMapper.class);

  Scenario toModel(ScenarioEntity scenarioEntity);
}
