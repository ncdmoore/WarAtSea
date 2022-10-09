package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.service.ScenarioService;
import com.google.inject.Inject;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public abstract class GameMapper {
  public static final GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

  @Inject
  @SuppressWarnings("unused")
  private ScenarioService scenarioService;

  abstract public GameEntity toEntity(Game game);
  abstract public Game toModel(GameEntity gameEntity);

  int mapScenarioId(Scenario scenario) {
    return scenario.getId();
  }

  Scenario mapScenario(int scenarioId) {
    return scenarioService.get(scenarioId);
  }
}
