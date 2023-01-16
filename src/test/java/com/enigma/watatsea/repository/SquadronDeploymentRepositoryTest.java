package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.DataProvider;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.SquadronDeploymentRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SquadronDeploymentRepositoryTest {
  @InjectMocks
  private SquadronDeploymentRepositoryImpl squadronDeploymentRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String SQUADRON_DIRECTORY = Paths.get("squadrons", "deployment").toString();

  @Test
  void shouldGetSquadronDeployment() {
    var deploymentId = new Id(ALLIES, gamePaths.getSquadronDeploymentFileName());

    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(deploymentId, gamePaths.getSquadronDeploymentDirectory())).willReturn(inputStream);

    var result = squadronDeploymentRepository.get(ALLIES);

    assertNotNull(result);
    assertEquals(1, result.size());

    var airbaseId = new Id(ALLIES, "Eagle");

    assertEquals(1, result.get(0).getAirbases().size());
    assertTrue(result.get(0).getAirbases().contains(airbaseId));

    var squadronId = new Id(ALLIES, "Swordfish-M1");

    assertEquals(3, result.get(0).getSquadrons().size());
    assertTrue(result.get(0).getSquadrons().contains(squadronId));
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", SQUADRON_DIRECTORY, gamePaths.getSquadronDeploymentFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
