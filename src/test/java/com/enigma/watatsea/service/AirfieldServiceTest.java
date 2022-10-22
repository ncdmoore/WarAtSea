package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.AirfieldEntity;
import com.enigma.waratsea.model.Airfield;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.enigma.waratsea.service.impl.AirfieldServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AirfieldServiceTest {
  @InjectMocks
  private AirfieldServiceImpl airfieldService;

  @Mock
  private AirfieldRepository airfieldRepository;

  private final String airfieldId1 = "ALLIES:airfield-name-1";
  private final String airfieldId2 = "ALLIES:airfield-name-2";

  @Test
  void testGetSingleAirfield() {
    var id = new Id(airfieldId1);

    var airfieldEntity = AirfieldEntity.builder()
        .id(airfieldId1).
        build();

    given(airfieldRepository.get(id)).willReturn(airfieldEntity);

    var result = airfieldService.get(id);

    assertEquals(airfieldId1, result.getId());
  }

  @Test
  void testGetAirfields() {
    var airfieldIdStrings = List.of(airfieldId1, airfieldId2);

    var airfieldEntities = airfieldIdStrings.stream()
        .map(this::buildEntity)
        .toList();

    var airfieldIds = airfieldIdStrings.stream()
        .map(Id::new)
        .toList();

    given(airfieldRepository.get(airfieldIds)).willReturn(airfieldEntities);

    var result = airfieldService.get(airfieldIds);

    assertEquals(airfieldIdStrings.size(), result.size());

    var resultIds = result.stream()
        .map(Airfield::getId)
        .toList();

    assertTrue(resultIds.contains(airfieldId1));
    assertTrue(resultIds.contains(airfieldId2));
  }

  private AirfieldEntity buildEntity(String id) {
    return AirfieldEntity.builder()
        .id(id)
        .build();
  }
}
