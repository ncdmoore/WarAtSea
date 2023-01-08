package com.enigma.waratsea.service;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.taskForce.TaskForce;

import java.util.Set;

public interface TaskForceService extends BootStrapped {
  Set<TaskForce> get(Side side);
}
