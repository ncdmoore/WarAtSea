package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.preferences.PreferencesEntity;
import com.enigma.waratsea.model.preferences.Preferences;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PreferencesMapper {
  PreferencesMapper INSTANCE = Mappers.getMapper(PreferencesMapper.class);

  Preferences toModel(PreferencesEntity preferencesEntity);
  PreferencesEntity toEntity(PreferencesEntity port);
}
