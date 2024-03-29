package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.taskforce.TaskForceEntity;
import com.enigma.waratsea.model.Side;

import java.util.List;
import java.util.Set;

public interface TaskForceRepository {
  List<TaskForceEntity> get(Side side);
  void save(String gameId, Side side, Set<TaskForceEntity> taskForces);
}
