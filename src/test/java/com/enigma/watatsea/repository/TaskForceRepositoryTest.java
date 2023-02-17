package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.taskforce.TaskForceEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.DataProvider;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.TaskForceRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TaskForceRepositoryTest {
  @InjectMocks
  private TaskForceRepositoryImpl taskForceRepository;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String TASK_FORCE_DIRECTORY = Paths.get("taskForces", "data").toString();

  private static final List<Id> TASK_FORCE_IDS = List.of(
      new Id(AXIS, "TF1"),
      new Id(AXIS, "TF2"),
      new Id(AXIS, "TF3"),
      new Id(AXIS, "TF4")
  );

  private static final List<String> TASK_FORCE_TITLES = List.of(
      "Tobruk Force", "Messina Force", "Reaction Force", "Emergency Force");

  private static final String TASK_FORCE_ONE_LOCATION = "Tobruk";
  private static final List<String> TASK_FORCE_ONE_SHIP_NAMES = List.of(
      "San_Giorgio", "Cesare_Battisti", "Daniele_Manin", "Francesco_Nullo", "Sauro");

  @Test
  void shouldGetTaskForce() {
    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = taskForceRepository.get(AXIS);

    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals(TASK_FORCE_IDS, result.stream().map(TaskForceEntity::getId).toList());
    assertEquals(TASK_FORCE_TITLES, result.stream().map(TaskForceEntity::getTitle).toList());

    var taskForce1 = result.get(0);

    assertEquals(5, taskForce1.getShips().size());
    assertEquals(TASK_FORCE_ONE_LOCATION, taskForce1.getLocation());
    assertEquals(TASK_FORCE_ONE_SHIP_NAMES, taskForce1.getShips().stream().map(Id::getName).toList());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", TASK_FORCE_DIRECTORY, gamePaths.getTaskForceFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
