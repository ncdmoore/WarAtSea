package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.DataProvider;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.ResourceProvider;
import com.enigma.waratsea.repository.impl.ShipRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Collections;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Nation.AUSTRALIAN;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.ship.ShipType.DESTROYER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ShipRepositoryTest {
  @InjectMocks
  private ShipRepositoryImpl shipRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private ResourceProvider resourceProvider;

  @Mock
  private DataProvider dataProvider;

  private static final String SHIP_DIRECTORY = Paths.get("ships", "data").toString();
  private static final String REGISTRY_DIRECTORY = Paths.get("ships", "registry").toString();
  private static final String SHIP_NAME = "Ship";
  private static final String SHIP_TITLE = "DD01 Ship";
  private static final Id SHIP_CLASS_ID = new Id(ALLIES, "Scott_Class");
  private static final String REGISTRY_NAME = "registry";

  @Test
  void shouldGetShipRegistry() {
    var registryId = new Id(ALLIES, DESTROYER.toLower());

    var inputStream = getRegistryInputStream();

    given(resourceProvider.getDefaultResourceInputStream(registryId, REGISTRY_DIRECTORY)).willReturn(inputStream);

    var result = shipRepository.getRegistry(ALLIES, DESTROYER);

    assertNotNull(result);
    assertEquals(1, result.size());

    var shipId = new Id(ALLIES, SHIP_NAME);

    assertEquals(shipId, result.get(0).getId());
    assertEquals(SHIP_TITLE, result.get(0).getTitle());
    assertEquals(AUSTRALIAN, result.get(0).getNation());
    assertEquals(SHIP_CLASS_ID, result.get(0).getShipClassId());
    assertEquals(Collections.emptySet(), result.get(0).getSquadrons());
  }

  @Test
  void shouldGetShip() {
    var shipId = new Id(ALLIES, SHIP_NAME);

    var inputStream = getShipInputStream();

    given(dataProvider.getDataInputStream(shipId, SHIP_DIRECTORY)).willReturn(inputStream);

    var result = shipRepository.get(shipId, DESTROYER);

    assertNotNull(result);
    assertEquals(SHIP_CLASS_ID, result.getId());
  }


  private InputStream getRegistryInputStream() {
    var fullPath = Paths.get("/", REGISTRY_DIRECTORY, REGISTRY_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }

  private InputStream getShipInputStream() {
    var fullPath = Paths.get("/", SHIP_DIRECTORY, SHIP_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
