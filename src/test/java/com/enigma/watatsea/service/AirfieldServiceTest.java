package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.mapper.AirfieldMapper;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AirfieldServiceTest {
  @InjectMocks
  private AirfieldServiceImpl airfieldService;

  @Mock
  private AirfieldRepository airfieldRepository;

  @Mock
  private AirfieldMapper airfieldMapper;

  @Spy
  @SuppressWarnings("unused")
  private Events events;

  private static final String AIRFIELD_ID_1 = "airfield-name-1";
  private static final String AIRFIELD_ID_2 = "airfield-name-2";

  @Test
  void testGetSingleAirfield() {
    var id = new Id(ALLIES, AIRFIELD_ID_1);

    var airfieldEntity = buildEntity(id);
    var airfield = buildAirfield(airfieldEntity);

    given(airfieldRepository.get(id)).willReturn(airfieldEntity);
    given(airfieldMapper.toModel(airfieldEntity)).willReturn(airfield);

    var result = airfieldService.get(id);

    assertNotNull(result);
    assertEquals(id, result.getId());
  }

  @Test
  void testGetAirfields() {
    var airfieldIdStrings = List.of(AIRFIELD_ID_1, AIRFIELD_ID_2);

    var airfieldId1 = new Id(ALLIES, AIRFIELD_ID_1);
    var airfieldId2 = new Id(ALLIES, AIRFIELD_ID_2);

    var airfieldEntity1 = buildEntity(airfieldId1);
    var airfieldEntity2 = buildEntity(airfieldId2);

    var airfield1 = buildAirfield(airfieldEntity1);
    var airfield2 = buildAirfield(airfieldEntity2);

    given(airfieldRepository.get(airfieldId1)).willReturn(airfieldEntity1);
    given(airfieldMapper.toModel(airfieldEntity1)).willReturn(airfield1);
    given(airfieldRepository.get(airfieldId2)).willReturn(airfieldEntity2);
    given(airfieldMapper.toModel(airfieldEntity2)).willReturn(airfield2);

    var result = airfieldService.get(List.of(airfieldId1, airfieldId2));

    assertEquals(airfieldIdStrings.size(), result.size());

    var resultIds = result.stream()
        .map(Airfield::getId)
        .toList();

    assertTrue(resultIds.contains(airfieldId1));
    assertTrue(resultIds.contains(airfieldId2));
    assertEquals(airfieldIdStrings.size(), airfieldService.get(ALLIES).size());
  }

  @Test
  public void testGetSidesAirfields() {
    var airfieldIds = List.of(
        new Id(ALLIES, AIRFIELD_ID_1),
        new Id(AXIS, AIRFIELD_ID_2)
    );

    airfieldIds.forEach(id -> {
      var airfieldEntity = buildEntity(id);
      var airfield = buildAirfield(airfieldEntity);
      given(airfieldRepository.get(id)).willReturn(airfieldEntity);
      given(airfieldMapper.toModel(airfieldEntity)).willReturn(airfield);
    });

    var result = airfieldService.get(airfieldIds);

    assertEquals(airfieldIds.size(), result.size());

    var alliedAirfields = airfieldService.get(ALLIES);
    var axisAirfields = airfieldService.get(AXIS);
    var neutralAirfields = airfieldService.get(NEUTRAL);

    assertEquals(1, alliedAirfields.size());
    assertEquals(1, axisAirfields.size());
    assertEquals(0, neutralAirfields.size());
  }

  private AirfieldEntity buildEntity(final Id id) {
    return AirfieldEntity.builder()
        .id(id)
        .build();
  }

  private Airfield buildAirfield(final AirfieldEntity airfieldEntity) {
    return Airfield.builder()
        .id(airfieldEntity.getId())
        .build();
  }
}
