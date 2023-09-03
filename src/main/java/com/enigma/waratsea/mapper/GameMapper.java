package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.service.ScenarioService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class GameMapper {
  public static final GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

  @Setter
  private ScenarioService scenarioService;

  public abstract GameEntity toEntity(Game game);

  public abstract Game toModel(GameEntity gameEntity);

  int mapScenarioId(final Scenario scenario) {
    return scenario.getId();
  }

  Scenario mapScenario(final int scenarioId) {
    return scenarioService.get(scenarioId);
  }
}
