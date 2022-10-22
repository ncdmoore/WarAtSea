package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.Id;

import java.util.List;

public interface AirfieldRepository {
  List<AirfieldEntity> get(List<Id> airfieldId);
  AirfieldEntity get(Id airfieldId);

}
