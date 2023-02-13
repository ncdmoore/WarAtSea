package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.matcher.BaseMatcherEntity;
import com.enigma.waratsea.entity.matcher.EnemyMatcherEntity;
import com.enigma.waratsea.entity.matcher.ShipMatcherEntity;
import com.enigma.waratsea.entity.matcher.SquadronMatcherEntity;
import com.enigma.waratsea.entity.victory.*;
import com.enigma.waratsea.event.matcher.BaseMatcher;
import com.enigma.waratsea.event.matcher.EnemyMatcher;
import com.enigma.waratsea.event.matcher.ShipMatcher;
import com.enigma.waratsea.event.matcher.SquadronMatcher;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.victory.*;
import com.enigma.waratsea.service.PortService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public abstract class VictoryMapper {
  public static final VictoryMapper INSTANCE = Mappers.getMapper(VictoryMapper.class);

  @Inject
  public PortService portService;

  abstract public Set<Victory> entitiesToModels(final List<VictoryEntity> entities);
  abstract public Set<VictoryEntity> modelsToEntities(final Set<Victory> models);

  @SubclassMapping(source = ShipBombardmentVictoryEntity.class, target = ShipBombardmentVictory.class)
  @SubclassMapping(source = ShipCargoLostVictoryEntity.class, target = ShipCargoLostVictory.class)
  @SubclassMapping(source = ShipCargoUnloadedVictoryEntity.class, target = ShipCargoUnloadedVictory.class)
  @SubclassMapping(source = ShipDamagedVictoryEntity.class, target = ShipDamagedVictory.class)
  @SubclassMapping(source = ShipOutOfFuelVictoryEntity.class, target = ShipOutOfFuelVictory.class)
  @SubclassMapping(source = ShipSunkVictoryEntity.class, target = ShipSunkVictory.class)
  @SubclassMapping(source = SquadronStepDestroyedVictoryEntity.class, target = SquadronStepDestroyedVictory.class)
  abstract public Victory toModel(final VictoryEntity victoryEntity);

  abstract public ShipMatcher toShipMatcher(final ShipMatcherEntity shipMatcherEntity);
  abstract public SquadronMatcher toSquadronMatcher(final SquadronMatcherEntity squadronMatcherEntity);
  abstract public BaseMatcher toBaseMatcher(final BaseMatcherEntity baseMatcherEntity);
  abstract public EnemyMatcher toEnemyMatcher(final EnemyMatcherEntity enemyMatcherEntity);

  @SubclassMapping(source = ShipBombardmentVictory.class, target = ShipBombardmentVictoryEntity.class)
  @SubclassMapping(source = ShipCargoLostVictory.class, target = ShipCargoLostVictoryEntity.class)
  @SubclassMapping(source = ShipCargoUnloadedVictory.class, target = ShipCargoUnloadedVictoryEntity.class)
  @SubclassMapping(source = ShipDamagedVictory.class, target = ShipDamagedVictoryEntity.class)
  @SubclassMapping(source = ShipOutOfFuelVictory.class, target = ShipOutOfFuelVictoryEntity.class)
  @SubclassMapping(source = ShipSunkVictory.class, target = ShipSunkVictoryEntity.class)
  @SubclassMapping(source = SquadronStepDestroyedVictory.class, target = SquadronStepDestroyedVictoryEntity.class)
  abstract public VictoryEntity toEntity(final Victory victory);

  abstract public ShipMatcherEntity toShipMatcherEntity(final ShipMatcher combatMatcher);
  abstract public SquadronMatcherEntity toSquadronMatcherEntity(final SquadronMatcher combatMatcher);
  abstract public BaseMatcherEntity toBaseMatcherEntity(final BaseMatcher baseMatcher);
  abstract public EnemyMatcherEntity toEnemyMatcherEntity(final EnemyMatcher enemyMatcher);

  Set<Port> mapPorts(final Set<Id> portIds) {
    return Optional.ofNullable(portIds)
        .map(ids -> portService.get(ids))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<Port> ports) {
    return Optional.ofNullable(ports)
        .map(this::getPortIds)
        .orElse(Collections.emptySet());
  }

  private Set<Id> getPortIds(final Set<Port> ports) {
    return ports.stream()
        .map(Port::getId)
        .collect(Collectors.toSet());
  }
}
