package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.mission.BombardmentEntity;
import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.model.mission.Bombardment;
import com.enigma.waratsea.model.mission.Mission;
import com.enigma.waratsea.service.TaskForceService;
import com.google.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

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
}
