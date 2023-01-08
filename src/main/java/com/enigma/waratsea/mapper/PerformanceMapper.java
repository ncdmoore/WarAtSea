package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.aircraft.PerformanceEntity;
import com.enigma.waratsea.model.aircraft.Performance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceMapper {
  PerformanceMapper INSTANCE = Mappers.getMapper(PerformanceMapper.class);

  Performance toModel(PerformanceEntity performanceEntity);
}
