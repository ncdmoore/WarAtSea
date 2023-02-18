package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.cargo.ManifestEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;

public interface ManifestRepository {
  List<ManifestEntity> get(Side side);
}
