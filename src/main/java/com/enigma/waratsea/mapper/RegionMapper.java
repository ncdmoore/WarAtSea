package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Region;
import com.enigma.waratsea.service.AirfieldService;
import com.google.inject.Inject;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public abstract class RegionMapper {
  public static final RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

  @Inject
  @SuppressWarnings("unused")
  private AirfieldService airfieldService;

  abstract public Region toModel(RegionEntity regionEntity);

  List<Airfield> mapAirfields(List<String> airfieldIds) {
    var assetIds = airfieldIds
        .stream()
        .map(Id::new)
        .toList();

    return airfieldService.get(assetIds);
  }

}
