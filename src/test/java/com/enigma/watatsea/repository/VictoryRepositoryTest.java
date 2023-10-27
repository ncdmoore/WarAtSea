package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.victory.*;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.impl.VictoryRepositoryImpl;
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

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.event.action.BaseAction.AIRFIELD_ATTACKED;
import static com.enigma.waratsea.event.action.BaseAction.PORT_ATTACKED;
import static com.enigma.waratsea.event.action.ShipAction.*;
import static com.enigma.waratsea.event.action.SquadronAction.SQUADRON_DAMAGED;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.ship.ShipType.BATTLESHIP;
import static com.enigma.waratsea.model.ship.ShipType.CORVETTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VictoryRepositoryTest {
  @InjectMocks
  private VictoryRepositoryImpl victoryRepository;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String SHIP_CARGO_LOST_ID = "ship-cargo-lost";
  private static final String SHIP_SUNK_ID = "ship-sunk";
  private static final String SHIP_OUT_OF_FUEL_ID = "ship-out-of-fuel";
  private static final String CAPITAL_SHIP_HULL_ID = "capital-ship-hull-damaged";
  private static final String NON_CAPITAL_SHIP_HULL_ID = "non-capital-ship-hull-damaged";
  private static final String PRIMARY_GUN_DAMAGED_ID = "primary-gun-damaged";
  private static final String BATTLESHIP_BOMBARDMENT_ID = "battleship-bombardment";
  private static final String HEAVY_CRUISER_BOMBARDMENT_ID = "heavy-cruiser-bombardment";
  private static final String SQUADRON_STEP_DESTROYED_ID = "squadron-step-destroyed";


  private static final List<String> VICTORY_CONDITION_IDS = List.of(
      SHIP_CARGO_LOST_ID,
      SHIP_SUNK_ID,
      SHIP_OUT_OF_FUEL_ID,
      CAPITAL_SHIP_HULL_ID,
      NON_CAPITAL_SHIP_HULL_ID,
      PRIMARY_GUN_DAMAGED_ID,
      BATTLESHIP_BOMBARDMENT_ID,
      HEAVY_CRUISER_BOMBARDMENT_ID,
      SQUADRON_STEP_DESTROYED_ID
  );


  @Test
  void shouldGetVictoryConditions() {
    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = victoryRepository.get(AXIS);

    assertNotNull(result);
    assertEquals(VICTORY_CONDITION_IDS.size(), result.size());
    assertEquals(VICTORY_CONDITION_IDS, result.stream().map(VictoryEntity::getId).toList());

    var victoryConditions = result.stream().collect(Collectors.toMap(VictoryEntity::getId, vc -> vc));

    var cargoLostCondition = (ShipCargoLostVictoryEntity) victoryConditions.get(SHIP_CARGO_LOST_ID);
    var cargoLostMatcher = cargoLostCondition.getMatcher();

    assertTrue(cargoLostMatcher.getActions().contains(SHIP_SUNK));
    assertSame(cargoLostMatcher.getShip().getSide(), ALLIES);

    var shipSunkCondition =  (ShipSunkVictoryEntity) victoryConditions.get(SHIP_SUNK_ID);
    var shipSunkMatcher = shipSunkCondition.getMatcher();

    assertTrue(shipSunkMatcher.getActions().contains(SHIP_SUNK));
    assertSame(shipSunkMatcher.getShip().getSide(), ALLIES);

    var shipOutOfFuelCondition =  (ShipOutOfFuelVictoryEntity) victoryConditions.get(SHIP_OUT_OF_FUEL_ID);
    var shipOutOfFuelMatcher = shipOutOfFuelCondition.getMatcher();

    assertSame(shipOutOfFuelMatcher.getShip().getSide(), ALLIES);
    assertTrue(shipOutOfFuelMatcher.getActions().contains(SHIP_OUT_OF_FUEL));

    var capitalShipHullDamaged = (ShipDamagedVictoryEntity) victoryConditions.get(CAPITAL_SHIP_HULL_ID);
    var capitalShipMatcher = capitalShipHullDamaged.getMatcher();

    assertEquals(3, capitalShipHullDamaged.getPoints());
    assertTrue(capitalShipMatcher.getActions().contains(SHIP_HULL_DAMAGED));
    assertTrue(capitalShipMatcher.getShip().getTypes().contains(BATTLESHIP));
    assertSame(capitalShipMatcher.getShip().getSide(), ALLIES);

    var nonCapitalShipHullDamaged = (ShipDamagedVictoryEntity) victoryConditions.get(NON_CAPITAL_SHIP_HULL_ID);
    var nonCapitalShipMatcher = nonCapitalShipHullDamaged.getMatcher();

    assertEquals(1, nonCapitalShipHullDamaged.getPoints());
    assertTrue(nonCapitalShipMatcher.getActions().contains(SHIP_HULL_DAMAGED));
    assertTrue(nonCapitalShipMatcher.getShip().getTypes().contains(CORVETTE));
    assertSame(nonCapitalShipMatcher.getShip().getSide(), ALLIES);

    var primaryGunDamaged = (ShipDamagedVictoryEntity) victoryConditions.get(PRIMARY_GUN_DAMAGED_ID);
    var primaryGunShipMatcher = primaryGunDamaged.getMatcher();

    assertEquals(3, primaryGunDamaged.getPoints());
    assertTrue(primaryGunShipMatcher.getActions().contains(SHIP_PRIMARY_DAMAGED));
    assertSame(primaryGunShipMatcher.getShip().getSide(), ALLIES);

    var battleshipBombardment = (ShipBombardmentVictoryEntity) victoryConditions.get(BATTLESHIP_BOMBARDMENT_ID);
    var battleshipMatcher = battleshipBombardment.getMatcher();

    assertEquals(5, battleshipBombardment.getPoints());
    assertTrue(battleshipMatcher.getActions().contains(AIRFIELD_ATTACKED));
    assertSame(battleshipMatcher.getBase().getSide(), ALLIES);
    assertTrue(battleshipMatcher.getEnemy().getTypes().contains("BATTLECRUISER"));

    var cruiserBombardment = (ShipBombardmentVictoryEntity) victoryConditions.get(HEAVY_CRUISER_BOMBARDMENT_ID);
    var cruiserMatcher = cruiserBombardment.getMatcher();

    assertEquals(1, cruiserBombardment.getPoints());
    assertTrue(cruiserMatcher.getActions().contains(PORT_ATTACKED));
    assertSame(cruiserMatcher.getBase().getSide(), ALLIES);
    assertTrue(cruiserMatcher.getEnemy().getTypes().contains("HEAVY_CRUISER"));

    var squadronStepDestroyed = (SquadronStepDestroyedVictoryEntity) victoryConditions.get(SQUADRON_STEP_DESTROYED_ID);
    var stepDestroyedMatcher = squadronStepDestroyed.getMatcher();

    assertEquals(6, squadronStepDestroyed.getPoints());
    assertSame(stepDestroyedMatcher.getSquadron().getSide(), ALLIES);
    assertTrue(stepDestroyedMatcher.getActions().contains(SQUADRON_DAMAGED));
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", gamePaths.getVictoryDirectory(), gamePaths.getVictoryFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
