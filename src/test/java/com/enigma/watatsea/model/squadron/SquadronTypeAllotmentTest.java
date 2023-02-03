package com.enigma.watatsea.model.squadron;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.GroupAllotment;
import com.enigma.waratsea.model.squadron.SquadronTypeAllotment;
import com.enigma.watatsea.mock.Die1;
import com.enigma.watatsea.mock.Die2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.*;

class SquadronTypeAllotmentTest {

  private final Id aircraftId1 = new Id(ALLIES, "aircraft-1");
  private final Id aircraftId2 = new Id(ALLIES, "aircraft-2");
  private final Id aircraftId3 = new Id(ALLIES, "aircraft-3");

  private final Id aircraftId4 = new Id(ALLIES, "aircraft-4");
  private final Id aircraftId5 = new Id(ALLIES, "aircraft-5");

  private final List<Id> initialIds1 = List.of(aircraftId1, aircraftId2, aircraftId3);
  private final List<Id> initialIds2 = List.of(aircraftId4, aircraftId5);

  private final List<Id> ids1 = new ArrayList<>(initialIds1);
  private final List<Id> ids2 = new ArrayList<>(initialIds2);

  private SquadronTypeAllotment squadronTypeAllotment;

  @BeforeEach
  void setUp() {

    var group1 = GroupAllotment.builder()
        .aircraft(ids1)
        .priority(1)
        .selectSize(2)
        .build();

    var group2 = GroupAllotment.builder()
        .aircraft(ids2)
        .priority(2)
        .selectSize(1)
        .build();

    var groups = List.of(group1, group2);

    squadronTypeAllotment = SquadronTypeAllotment.builder()
        .dice(3)
        .factor(1)
        .groups(groups)
        .build();
  }

  @Test
  void shouldGetAllotmentFormFirstGroupOnly() {
    var die = new Die1();

    var result = squadronTypeAllotment.get(die);

    assertNotNull(result);
    assertEquals(2, result.size());

    result.forEach(id -> assertTrue(initialIds1.contains(id)));
  }

  @Test
  void shouldGetAllotmentFormBothGroups() {
    var die = new Die2();

    var result = squadronTypeAllotment.get(die);

    assertNotNull(result);
    assertEquals(4, result.size());

    var both = Stream.concat(initialIds1.stream(), initialIds2.stream())
        .toList();

    result.forEach(id -> assertTrue(both.contains(id)));
  }
}
