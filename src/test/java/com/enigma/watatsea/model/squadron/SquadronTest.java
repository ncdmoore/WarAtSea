package com.enigma.watatsea.model.squadron;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.squadron.Squadron;
import org.junit.jupiter.api.Test;

import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SquadronTest {

  @Test
  void shouldGetSquadronTitleFromAircraft() {
    var id = new Id(ALLIES, "plane-number");

    var aircraft = Aircraft.builder()
        .designation("designation.")
        .build();

    var squadron = Squadron.builder()
        .id(id)
        .aircraft(aircraft)
        .build();

    var result = squadron.getTitle();

    assertEquals("plane-designation.number", result);
  }
}
