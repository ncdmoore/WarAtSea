package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.release.ReleaseEntity;
import com.enigma.waratsea.entity.release.ShipCombatReleaseEntity;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.impl.ReleaseRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.enigma.waratsea.Constants.ANY_SHIP;
import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.event.action.ShipAction.SHIP_DAMAGED;
import static com.enigma.waratsea.event.action.ShipAction.SHIP_SPOTTED;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReleaseRepositoryTest {
  @InjectMocks
  private ReleaseRepositoryImpl releaseRepository;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String SHIP_DAMAGED_ID = "ship-damaged";
  private static final String SHIP_SPOTTED_ID = "ship-spotted";


  private static final List<String> RELEASE_CONDITION_IDS = List.of(
      SHIP_DAMAGED_ID,
      SHIP_SPOTTED_ID
  );

  @Test
  void shouldGetReleaseConditions() {
    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = releaseRepository.get(AXIS);

    assertNotNull(result);
    assertEquals(RELEASE_CONDITION_IDS.size(), result.size());
    assertEquals(RELEASE_CONDITION_IDS, result.stream().map(ReleaseEntity::getId).toList());

    var releaseConditions = result.stream().collect(Collectors.toMap(ReleaseEntity::getId, rc -> rc));

    var shipDamagedCondition = (ShipCombatReleaseEntity) releaseConditions.get(SHIP_DAMAGED_ID);
    var shipDamagedMatcher = shipDamagedCondition.getMatcher();

    assertTrue(shipDamagedMatcher.getActions().contains(SHIP_DAMAGED));
    assertSame(shipDamagedMatcher.getShip().getSide(), AXIS);
    assertSame(shipDamagedMatcher.getEnemy().getSide(), ALLIES);
    assertTrue(shipDamagedMatcher.getEnemy().getTypes().contains(ANY_SHIP));

    var shipSpottedCondition =  (ShipCombatReleaseEntity) releaseConditions.get(SHIP_SPOTTED_ID);
    var shipSpottedMatcher = shipSpottedCondition.getMatcher();

    assertTrue(shipSpottedMatcher.getActions().contains(SHIP_SPOTTED));
    assertSame(shipSpottedMatcher.getShip().getSide(), ALLIES);

  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", gamePaths.getReleaseDirectory(), gamePaths.getReleaseFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
