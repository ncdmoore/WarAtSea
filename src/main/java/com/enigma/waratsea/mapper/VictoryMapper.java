package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.victory.ShipBombardmentVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipCargoLostVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipCargoUnloadedVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipDamagedVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipOutOfFuelVictoryEntity;
import com.enigma.waratsea.entity.victory.ShipSunkVictoryEntity;
import com.enigma.waratsea.entity.victory.SquadronStepDestroyedVictoryEntity;
import com.enigma.waratsea.entity.victory.VictoryEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.model.victory.ShipBombardmentVictory;
import com.enigma.waratsea.model.victory.ShipCargoLostVictory;
import com.enigma.waratsea.model.victory.ShipCargoUnloadedVictory;
import com.enigma.waratsea.model.victory.ShipDamagedVictory;
import com.enigma.waratsea.model.victory.ShipOutOfFuelVictory;
import com.enigma.waratsea.model.victory.ShipSunkVictory;
import com.enigma.waratsea.model.victory.SquadronStepDestroyedVictory;
import com.enigma.waratsea.model.victory.Victory;
import com.enigma.waratsea.service.PortService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public abstract class VictoryMapper {
  public static final VictoryMapper INSTANCE = Mappers.getMapper(VictoryMapper.class);

  @Setter
  private PortService portService;

  public abstract Set<Victory> entitiesToModels(List<VictoryEntity> entities);
  public abstract Set<VictoryEntity> modelsToEntities(Set<Victory> models);

  @SubclassMapping(source = ShipBombardmentVictoryEntity.class, target = ShipBombardmentVictory.class)
  @SubclassMapping(source = ShipCargoLostVictoryEntity.class, target = ShipCargoLostVictory.class)
  @SubclassMapping(source = ShipCargoUnloadedVictoryEntity.class, target = ShipCargoUnloadedVictory.class)
  @SubclassMapping(source = ShipDamagedVictoryEntity.class, target = ShipDamagedVictory.class)
  @SubclassMapping(source = ShipOutOfFuelVictoryEntity.class, target = ShipOutOfFuelVictory.class)
  @SubclassMapping(source = ShipSunkVictoryEntity.class, target = ShipSunkVictory.class)
  @SubclassMapping(source = SquadronStepDestroyedVictoryEntity.class, target = SquadronStepDestroyedVictory.class)
  public abstract Victory toModel(VictoryEntity victoryEntity);


  @SubclassMapping(source = ShipBombardmentVictory.class, target = ShipBombardmentVictoryEntity.class)
  @SubclassMapping(source = ShipCargoLostVictory.class, target = ShipCargoLostVictoryEntity.class)
  @SubclassMapping(source = ShipCargoUnloadedVictory.class, target = ShipCargoUnloadedVictoryEntity.class)
  @SubclassMapping(source = ShipDamagedVictory.class, target = ShipDamagedVictoryEntity.class)
  @SubclassMapping(source = ShipOutOfFuelVictory.class, target = ShipOutOfFuelVictoryEntity.class)
  @SubclassMapping(source = ShipSunkVictory.class, target = ShipSunkVictoryEntity.class)
  @SubclassMapping(source = SquadronStepDestroyedVictory.class, target = SquadronStepDestroyedVictoryEntity.class)
  public abstract VictoryEntity toEntity(Victory victory);


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
