package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.mission.BombardmentEntity;
import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.mission.Bombardment;
import com.enigma.waratsea.model.mission.Mission;
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
  public TaskForceService taskForceService;

  abstract public List<Mission> entitiesToModels(final List<MissionEntity> entities);
  abstract public Set<MissionEntity> modelsToEntities(final Set<Mission> models);

  @SubclassMapping(source = BombardmentEntity.class, target = Bombardment.class)
  abstract public Mission toModel(final MissionEntity missionEntity);

  abstract public Bombardment toBombardment(final BombardmentEntity bombardmentEntity);

  @SubclassMapping(source = Bombardment.class, target = BombardmentEntity.class)
  abstract public MissionEntity toEntity(final Mission ship);

  abstract public BombardmentEntity toBombardmentEntity(final Bombardment bombardment);

  Set<TaskForce> mapTaskForces(final Set<Id> taskForceIds) {
    return Optional.ofNullable(taskForceIds)
        .map(ids -> taskForceService.get(taskForceIds))
        .orElse(Collections.emptySet());
  }

  Set<Id> mapIds(final Set<TaskForce> taskForces) {
    if (taskForces == null) {
      return Collections.emptySet();
    }

    return taskForces.stream()
        .map(TaskForce::getId)
        .collect(Collectors.toSet());
  }
}
