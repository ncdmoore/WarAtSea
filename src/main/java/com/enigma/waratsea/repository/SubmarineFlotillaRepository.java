package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.model.Id;

import java.util.List;

public interface SubmarineFlotillaRepository {
  List<SubmarineFlotillaEntity> get(Id id);
}
