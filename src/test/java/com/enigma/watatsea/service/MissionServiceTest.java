package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.mission.BombardmentEntity;
import com.enigma.waratsea.entity.mission.EscortEntity;
import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadTaskForcesEvent;
import com.enigma.waratsea.mapper.MissionMapper;
import com.enigma.waratsea.model.mission.Bombardment;
import com.enigma.waratsea.model.mission.Escort;
import com.enigma.waratsea.model.mission.Mission;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.repository.MissionRepository;
import com.enigma.waratsea.service.impl.MissionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MissionServiceTest {
  @InjectMocks
  private MissionServiceImpl missionService;

  @Mock
  private MissionRepository missionRepository;

  @Mock
  private MissionMapper missionMapper;

  @Spy
  @SuppressWarnings("unused")
  private Events events;

  @Test
  void shouldGetMissions() {
    var taskForces = buildTaskForces();
    var entities = buildEntities();
    var missions = buildMissions(taskForces);

    given(missionRepository.get(any())).willReturn(entities);
    given(missionMapper.entitiesToModels(entities)).willReturn(missions);

    var result = missionService.get(ALLIES);

    assertNotNull(result);
    missions.forEach(mission -> assertTrue(result.contains(mission)));
  }

  @Test
  void shouldSetMissions() {
    var taskForces = buildTaskForces();
    var entities = buildEntities();
    var missions = buildMissions(taskForces);

    given(missionRepository.get(any())).willReturn(entities);
    given(missionMapper.entitiesToModels(entities)).willReturn(missions);

    events.getLoadTaskForcesEvent().fire(new LoadTaskForcesEvent());

    var result = taskForces.stream()
        .flatMap(taskForce -> taskForce.getMissions().stream())
        .toList();

    missions.forEach(mission -> assertTrue(result.contains(mission)));
  }

  private Set<TaskForce> buildTaskForces() {
    var taskForce = TaskForce.builder()
        .build();

    return Set.of(taskForce);
  }

  private List<Mission> buildMissions(final Set<TaskForce> taskForces) {
    var bombardmentMission = Bombardment.builder()
        .taskForces(taskForces)
        .build();

    var escortMission = Escort.builder()
        .taskForces(taskForces)
        .build();

    return List.of(bombardmentMission, escortMission);
  }

  private List<MissionEntity> buildEntities() {
    var bombardmentEntity = new BombardmentEntity();
    var escortEntity = new EscortEntity();
    return List.of(bombardmentEntity, escortEntity);
  }
}
