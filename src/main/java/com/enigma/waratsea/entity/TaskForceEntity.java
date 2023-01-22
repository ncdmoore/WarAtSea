package com.enigma.waratsea.entity;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.taskForce.TaskForceState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

import static com.enigma.waratsea.model.taskForce.TaskForceState.ACTIVE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskForceEntity {
  private Id id;
  private String title;
  private String location;

  @Builder.Default
  private TaskForceState state = ACTIVE;

  @Builder.Default
  private Set<Id> ships = Collections.emptySet();
}
