package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.GameEntity;

import java.util.List;

public interface GameRepository {
  List<GameEntity> get();

  void save(final GameEntity game);
}
