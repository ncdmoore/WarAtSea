package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.release.ReleaseEntity;
import com.enigma.waratsea.entity.release.ShipCombatReleaseEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.release.Release;
import com.enigma.waratsea.model.release.ShipCombatRelease;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.service.TaskForceService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ReleaseMapper {
  public static final ReleaseMapper INSTANCE = Mappers.getMapper(ReleaseMapper.class);

  @Setter
  private TaskForceService taskForceService;

  public abstract Set<Release> entitiesToModels(List<ReleaseEntity> entities);

  public abstract Set<ReleaseEntity> modelsToEntities(Set<Release> models);

  @SubclassMapping(source = ShipCombatReleaseEntity.class, target = ShipCombatRelease.class)
  public abstract Release toModel(ReleaseEntity releaseEntity);

  @SubclassMapping(source = ShipCombatRelease.class, target = ShipCombatReleaseEntity.class)
  public abstract ReleaseEntity toEntity(Release victory);

  Set<TaskForce> mapTaskForces(final Set<Id> taskForceIds) {
    return Optional.ofNullable(taskForceIds)
        .map(ids -> taskForceService.get(ids))
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
