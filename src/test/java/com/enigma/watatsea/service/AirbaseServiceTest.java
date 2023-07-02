package com.enigma.watatsea.service;

import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.ship.AircraftCarrier;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.service.AirfieldService;
import com.enigma.waratsea.service.TaskForceService;
import com.enigma.waratsea.service.impl.AirbaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static com.enigma.waratsea.model.Nation.BRITISH;
import static com.enigma.waratsea.model.Nation.GERMAN;
import static com.enigma.waratsea.model.Side.ALLIES;
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirbaseServiceTest {
  @InjectMocks
  private AirbaseServiceImpl airbaseService;

  @Mock
  private AirfieldService airfieldService;

  @Mock
  private TaskForceService taskForceService;

  @ParameterizedTest
  @EnumSource(value = Side.class, names = {"ALLIES", "AXIS"})
  void shouldGetNations(final Side side) {
    var nation = setNationFromSide(side);

    lenient().when(airfieldService.get(side)).thenReturn(Set.of(buildAirfield(side)));
    lenient().when(taskForceService.get(side)).thenReturn(Collections.emptySet());

    var result = airbaseService.getNations(side);

    assertTrue(result.contains(nation));
  }

  @Test
  void shouldGetAirbase() {
    var alliesAirfield = buildAirfield(ALLIES);
    var axisAirfield = buildAirfield(AXIS);

    var alliesTaskForce = buildTaskForce(ALLIES);
    var axisTaskForce = buildTaskForce(AXIS);

    var alliedCarriers = alliesTaskForce.getAirbases();
    var axisCarriers = axisTaskForce.getAirbases();

    when(airfieldService.get(ALLIES)).thenReturn(Set.of(alliesAirfield));
    when(airfieldService.get(AXIS)).thenReturn(Set.of(axisAirfield));
    when(taskForceService.get(ALLIES)).thenReturn(Set.of(alliesTaskForce));
    when(taskForceService.get(AXIS)).thenReturn(Set.of(axisTaskForce));

    var result = airbaseService.get();

    assertNotNull(result);
    assertEquals(alliesAirfield, result.get(alliesAirfield.getId()));
    assertEquals(axisAirfield, result.get(axisAirfield.getId()));

    alliedCarriers.forEach(carrier -> assertEquals(carrier, result.get(carrier.getId())));
    axisCarriers.forEach(carrier -> assertEquals(carrier, result.get(carrier.getId())));
  }

  private Airfield buildAirfield(final Side side) {
    var nation = setNationFromSide(side);

    return Airfield.builder()
        .id(new Id(side, "airfield"))
        .nations(Set.of(nation))
        .build();
  }

  private TaskForce buildTaskForce(final Side side) {
    var nation = setNationFromSide(side);

    var aircraftCarrier = AircraftCarrier.builder()
        .id(new Id(side, "aircraft-carrier"))
        .title(nation + "carrier")
        .nation(nation)
        .build();

    return TaskForce.builder()
        .id(new Id(side, "task-force"))
        .ships(Set.of(aircraftCarrier))
        .build();
  }

  private Nation setNationFromSide(final Side side) {
    return side == ALLIES ? BRITISH : GERMAN;
  }
}
