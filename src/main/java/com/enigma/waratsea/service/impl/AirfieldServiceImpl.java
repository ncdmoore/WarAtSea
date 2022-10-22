package com.enigma.waratsea.service.impl;

import com.enigma.waratsea.mapper.AirfieldMapper;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.AssetId;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.enigma.waratsea.service.AirfieldService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Singleton
public class AirfieldServiceImpl implements AirfieldService {
  private final AirfieldRepository airfieldRepository;

  @Inject
  public AirfieldServiceImpl(final AirfieldRepository airfieldRepository) {
    this.airfieldRepository = airfieldRepository;
  }

  @Override
  public List<Airfield> get(List<AssetId> airfieldIds) {
    var entities = airfieldRepository.get(airfieldIds);

    return AirfieldMapper.INSTANCE.toModels(entities);
  }

  @Override
  public Airfield get(AssetId airfieldId) {
    var entity = airfieldRepository.get(airfieldId);

    return AirfieldMapper.INSTANCE.toModel(entity);
  }
}
