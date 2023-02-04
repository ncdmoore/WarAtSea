package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.SubmarineFlotillaRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Singleton
public class SubmarineFlotillaRepositoryImpl implements SubmarineFlotillaRepository {
  private final DataProvider dataProvider;



  @Inject
  public SubmarineFlotillaRepositoryImpl(final GamePaths gamePaths,
                                         final DataProvider dataProvider) {
    this.dataProvider = dataProvider;
  }

  @Override
  public List<SubmarineFlotillaEntity> get(Id id) {
    return null;
  }
}
