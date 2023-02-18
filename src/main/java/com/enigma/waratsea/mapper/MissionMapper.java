package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.mission.BombardmentEntity;
import com.enigma.waratsea.entity.mission.EscortEntity;
import com.enigma.waratsea.entity.mission.FerryShipsEntity;
import com.enigma.waratsea.entity.mission.InterceptEntity;
import com.enigma.waratsea.entity.mission.InvasionEntity;
import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.entity.mission.PatrolEntity;
import com.enigma.waratsea.entity.mission.TransportEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.mission.Bombardment;
import com.enigma.waratsea.model.mission.Escort;
import com.enigma.waratsea.model.mission.FerryShips;
import com.enigma.waratsea.model.mission.Intercept;
import com.enigma.waratsea.model.mission.Invasion;
import com.enigma.waratsea.model.mission.Mission;
import com.enigma.waratsea.model.mission.Patrol;
import com.enigma.waratsea.model.mission.Transport;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.service.TaskForceService;
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
public abstract class MissionMapper {
  public static final MissionMapper INSTANCE = Mappers.getMapper(MissionMapper.class);

  @Inject
  private TaskForceService taskForceService;

  public abstract List<Mission> entitiesToModels(List<MissionEntity> entities);

  public abstract Set<MissionEntity> modelsToEntities(Set<Mission> models);

  @SubclassMapping(source = BombardmentEntity.class, target = Bombardment.class)
  @SubclassMapping(source = EscortEntity.class, target = Escort.class)
  @SubclassMapping(source = FerryShipsEntity.class, target = FerryShips.class)
  @SubclassMapping(source = InterceptEntity.class, target = Intercept.class)
  @SubclassMapping(source = InvasionEntity.class, target = Invasion.class)
  @SubclassMapping(source = PatrolEntity.class, target = Patrol.class)
  @SubclassMapping(source = TransportEntity.class, target = Transport.class)
  public abstract Mission toModel(MissionEntity missionEntity);

  @SubclassMapping(source = Bombardment.class, target = BombardmentEntity.class)
  @SubclassMapping(source = Escort.class, target = EscortEntity.class)
  @SubclassMapping(source = FerryShips.class, target = FerryShipsEntity.class)
  @SubclassMapping(source = Intercept.class, target = InterceptEntity.class)
  @SubclassMapping(source = Invasion.class, target = InvasionEntity.class)
  @SubclassMapping(source = Patrol.class, target = PatrolEntity.class)
  @SubclassMapping(source = Transport.class, target = TransportEntity.class)
  public abstract MissionEntity toEntity(Mission mission);

  Set<TaskForce> mapTaskForces(final Set<Id> taskForceIds) {
    return Optional.ofNullable(taskForceIds)
        .map(ids -> taskForceService.get(taskForceIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<TaskForce> taskForces) {
    return Optional.ofNullable(taskForces)
        .map(this::getTaskForceIds)
        .orElse(Collections.emptySet());
  }

  private Set<Id> getTaskForceIds(final Set<TaskForce> taskForces) {
    return taskForces.stream()
        .map(TaskForce::getId)
        .collect(Collectors.toSet());
  }
}
