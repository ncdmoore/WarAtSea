package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.aircraft.AttackRatingEntity;
import com.enigma.waratsea.model.aircraft.AttackRating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttackMapper {
  AttackMapper INSTANCE = Mappers.getMapper(AttackMapper.class);

  AttackRating toModel(AttackRatingEntity attackRatingEntity);
}
