package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.enigma.waratsea.service.impl.AirfieldServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.enigma.waratsea.model.Side.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AirfieldServiceTest {
  @InjectMocks
  private AirfieldServiceImpl airfieldService;

  @Mock
  private AirfieldRepository airfieldRepository;

  @SuppressWarnings("unused")
  @Spy
  private Events events;

  private static final String AIRFIELD_ID_1 = "ALLIES:airfield-name-1";
  private static final String AIRFIELD_ID_2 = "ALLIES:airfield-name-2";

  @Test
  void testGetSingleAirfield() {
    var id = new Id(AIRFIELD_ID_1);

    var airfieldEntity = AirfieldEntity.builder()
        .id(AIRFIELD_ID_1).
        build();

    given(airfieldRepository.get(id)).willReturn(airfieldEntity);

    var result = airfieldService.get(id);

    assertEquals(AIRFIELD_ID_1, result.getId());
  }

  @Test
  void testGetAirfields() {
    var airfieldIdStrings = List.of(AIRFIELD_ID_1, AIRFIELD_ID_2);

    var airfieldId1 = new Id(ALLIES, AIRFIELD_ID_1);
    var airfieldId2 = new Id(ALLIES, AIRFIELD_ID_2);

    var airfieldEntity1 = buildEntity(AIRFIELD_ID_1);
    var airfieldEntity2 = buildEntity(AIRFIELD_ID_2);

    given(airfieldRepository.get(airfieldId1)).willReturn(airfieldEntity1);
    given(airfieldRepository.get(airfieldId2)).willReturn(airfieldEntity2);

    var result = airfieldService.get(List.of(airfieldId1, airfieldId2));

    assertEquals(airfieldIdStrings.size(), result.size());

    var resultIds = result.stream()
        .map(Airfield::getId)
        .toList();

    assertTrue(resultIds.contains(AIRFIELD_ID_1));
    assertTrue(resultIds.contains(AIRFIELD_ID_2));



    assertEquals(airfieldIdStrings.size(), airfieldService.get(ALLIES).size());
  }

  @Test
  public void testGetSidesAirfields() {
    var airfieldIdStrings = List.of(AIRFIELD_ID_1, AIRFIELD_ID_2);

    var airfieldId1 = new Id(ALLIES, AIRFIELD_ID_1);
    var airfieldId2 = new Id(AXIS, AIRFIELD_ID_2);

    var airfieldEntity1 = buildEntity(AIRFIELD_ID_1);
    var airfieldEntity2 = buildEntity(AIRFIELD_ID_2);

    given(airfieldRepository.get(airfieldId1)).willReturn(airfieldEntity1);
    given(airfieldRepository.get(airfieldId2)).willReturn(airfieldEntity2);

    var airfields = airfieldService.get(List.of(airfieldId1, airfieldId2));

    assertEquals(airfieldIdStrings.size(), airfields.size());

    var alliedAirfields = airfieldService.get(ALLIES);
    var axisAirfields = airfieldService.get(AXIS);
    var neutralAirfields = airfieldService.get(NEUTRAL);

    assertEquals(1, alliedAirfields.size());
    assertEquals(1, axisAirfields.size());
    assertEquals(0, neutralAirfields.size());
  }

  private AirfieldEntity buildEntity(String id) {
    return AirfieldEntity.builder()
        .id(id)
        .build();
  }
}
