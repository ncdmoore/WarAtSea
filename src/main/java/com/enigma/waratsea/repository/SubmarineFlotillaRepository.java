package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;

public interface SubmarineFlotillaRepository {
  List<SubmarineFlotillaEntity> get(Side side);
}
