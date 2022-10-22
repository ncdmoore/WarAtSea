package com.enigma.waratsea.service;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.AssetId;

import java.util.List;

public interface AirfieldService {
  List<Airfield> get(List<AssetId> airfieldId);
  Airfield get(AssetId airfieldId);
}
