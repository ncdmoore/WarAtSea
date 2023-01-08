package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.squadron.SquadronDeploymentEntity;
import com.enigma.waratsea.model.squadron.SquadronDeployment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SquadronDeploymentMapper {
  SquadronDeploymentMapper INSTANCE = Mappers.getMapper(SquadronDeploymentMapper.class);

  List<SquadronDeployment> entitiesToModels(List<SquadronDeploymentEntity> entities);
  SquadronDeployment toModel(SquadronDeploymentEntity entity);
  SquadronDeploymentEntity toEntity(SquadronDeployment model);

}
