package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.option.AllotmentModificationEntity;
import com.enigma.waratsea.entity.option.SquadronAllotmentModificationEntity;
import com.enigma.waratsea.model.option.AllotmentModification;
import com.enigma.waratsea.model.option.SquadronAllotmentModification;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AllotmentModificationMapper {
  AllotmentModificationMapper INSTANCE = Mappers.getMapper(AllotmentModificationMapper.class);

  List<AllotmentModification> toModels(List<AllotmentModificationEntity> allotmentModificationEntities);

  AllotmentModification toModel(AllotmentModificationEntity allotmentModificationEntity);

  SquadronAllotmentModification toModification(SquadronAllotmentModificationEntity squadronAllotmentModificationEntity);
}
