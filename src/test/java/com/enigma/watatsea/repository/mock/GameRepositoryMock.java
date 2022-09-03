package com.enigma.watatsea.repository.mock;

import com.enigma.waratsea.entity.GameEntity;
import com.enigma.waratsea.repository.GameRepository;
import lombok.Setter;

import java.util.List;

public class GameRepositoryMock implements GameRepository {
  @Setter
  private List<GameEntity> games;

  @Override
  public List<GameEntity> get() {
    return games;
  }

  @Override
  public void save(GameEntity game) {

  }
}
