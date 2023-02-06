package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.option.AllotmentModificationEntity;
import com.enigma.waratsea.model.Id;

import java.util.List;

public interface SquadronAllotmentModRepository {
  List<AllotmentModificationEntity> get(Id modificationId);
}
