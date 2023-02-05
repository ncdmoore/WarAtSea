package com.enigma.watatsea.model.squadron;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.GroupAllotment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.*;

class GroupAllotmentTest {
  private List<Id> initialAllotment;
  private List<Id> aircraft;

  @BeforeEach
  void setUp() {
    var aircraftId1 = new Id(ALLIES, "aircraft-1");
    var aircraftId2 = new Id(ALLIES, "aircraft-2");
    var aircraftId3 = new Id(ALLIES, "aircraft-3");

    initialAllotment = List.of(aircraftId1, aircraftId2, aircraftId3);

    aircraft = new ArrayList<>(initialAllotment);
  }

  @Test
  void shouldGetSelectSizeOfAircraftIds() {
    var selectSize = 2;
    var numberNeeded = 4;

    var groupAllotment = GroupAllotment.builder()
        .aircraft(aircraft)
        .priority(1)
        .selectSize(selectSize)
        .build();

    var result = groupAllotment.selectAircraft(numberNeeded);

    assertNotNull(result);
    assertEquals(selectSize, result.size());

    result.forEach(aircraftId -> assertTrue(initialAllotment.contains(aircraftId)));
  }

  @Test
  void shouldGetNeededNumberOfAircraftIds() {
    var selectSize = 2;
    var numberNeeded = 1;

    var groupAllotment = GroupAllotment.builder()
        .aircraft(aircraft)
        .priority(1)
        .selectSize(selectSize)
        .build();

    var result = groupAllotment.selectAircraft(numberNeeded);

    assertNotNull(result);
    assertEquals(numberNeeded, result.size());

    result.forEach(aircraftId -> assertTrue(initialAllotment.contains(aircraftId)));
  }

  @Test
  void shouldGetAvailableAircraftIds() {
    var selectSize = 2;
    var numberNeeded = 4;

    var groupAllotment = GroupAllotment.builder()
        .aircraft(aircraft)
        .priority(1)
        .selectSize(selectSize)
        .build();

    groupAllotment.selectAircraft(numberNeeded);
    var remaining = groupAllotment.getAircraft().size();

    var result = groupAllotment.selectAircraft(numberNeeded);

    assertNotNull(result);
    assertEquals(remaining, result.size());

    result.forEach(aircraftId -> assertTrue(initialAllotment.contains(aircraftId)));
  }

  @Test
  void shouldGetZeroAircraftIds() {
    var selectSize = 2;
    var numberNeeded = 4;

    var groupAllotment = GroupAllotment.builder()
        .aircraft(aircraft)
        .priority(1)
        .selectSize(selectSize)
        .build();

    groupAllotment.selectAircraft(numberNeeded);
    groupAllotment.selectAircraft(numberNeeded);
    var remaining = groupAllotment.getAircraft().size();

    var result = groupAllotment.selectAircraft(numberNeeded);

    assertNotNull(result);
    assertEquals(remaining, result.size());
    assertEquals(0, result.size());
  }
}
