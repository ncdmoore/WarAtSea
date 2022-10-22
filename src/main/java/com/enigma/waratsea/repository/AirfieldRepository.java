package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.AssetId;

import java.util.List;

public interface AirfieldRepository {
  List<AirfieldEntity> get(List<AssetId> airfieldId);
  AirfieldEntity get(AssetId airfieldId);

}
