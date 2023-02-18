package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.taskforce.TaskForceEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.ship.Ship;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.service.ShipService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330")
public abstract class TaskForceMapper {
  public static final TaskForceMapper INSTANCE = Mappers.getMapper(TaskForceMapper.class);

  @Inject
  private ShipService shipService;

  public abstract List<TaskForce> entitiesToModels(List<TaskForceEntity> entities);

  public abstract Set<TaskForceEntity> modelsToEntities(Set<TaskForce> models);

  public abstract TaskForce toModel(TaskForceEntity taskForceEntity);

  public abstract TaskForceEntity toEntity(TaskForce taskForce);

  Set<Ship> mapShips(final Set<Id> shipIds) {
    return Optional.ofNullable(shipIds)
        .map(ids -> shipService.get(shipIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<Ship> ships) {
    return Optional.ofNullable(ships)
        .map(this::getShipIds)
        .orElse(Collections.emptySet());
  }

  private Set<Id> getShipIds(final Set<Ship> ships) {
    return ships.stream()
        .map(Ship::getId)
        .collect(Collectors.toSet());
  }
}
