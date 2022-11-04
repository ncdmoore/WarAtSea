package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.RegionEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.LoadMapEvent;
import com.enigma.waratsea.mapper.RegionMapper;
import com.enigma.waratsea.model.*;
import com.enigma.waratsea.repository.RegionRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.impl.RegionServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.enigma.waratsea.model.GameName.ARCTIC_CONVOY;
import static com.enigma.waratsea.model.Nation.BRITISH;
import static com.enigma.waratsea.model.Nation.UNITED_STATES;
import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {
  @InjectMocks
  private RegionServiceImpl regionService;

  @Mock
  private RegionRepository regionRepository;

  @Mock
  private RegionMapper regionMapper;

  @Mock
  private GameService gameService;

  @Spy
  private Events events;

  private static final String GAME_MAP = "game-map";
  private static final String REGION_NAME_1 = "region-name-1";
  private static final String REGION_NAME_2 = "region-name-2";
  private static final String AIRFIELD_ID_1 = "airfield-id-1";
  private static final String AIRFIELD_ID_2 = "airfield-id-2";

  private static Game game;
  private static List<RegionEntity> regionEntities;
  private static List<Region> regions;

  @BeforeAll
  static void setUpData() {
    var scenario = new Scenario();
    scenario.setMap(GAME_MAP);

    game = new Game(ARCTIC_CONVOY);
    game.setScenario(scenario);

    regionEntities = buildRegionEntities();
    regions = buildRegions(regionEntities);
  }

  @BeforeEach
  void setUpTest() {
    given(gameService.getGame()).willReturn(game);
    given(regionRepository.get(any())).willReturn(regionEntities);

    for (int i = 0; i < regionEntities.size();  i++) {
      given(regionMapper.toModel(regionEntities.get(i))).willReturn(regions.get(i));
    }

    events.getLoadMapEvent().fire(new LoadMapEvent());
  }

  @Test
  void testGetAirfieldRegion() {
    var result1 = regionService.getAirfieldRegion(BRITISH, new Id(ALLIES, AIRFIELD_ID_1));
    var result2 = regionService.getAirfieldRegion(BRITISH, new Id(ALLIES, AIRFIELD_ID_2));
    var result3 = regionService.getAirfieldRegion(UNITED_STATES, new Id(ALLIES, AIRFIELD_ID_2));

    assertNotNull(result1);
    assertEquals(REGION_NAME_1, result1.getName());

    assertNotNull(result2);
    assertEquals(REGION_NAME_2, result2.getName());

    assertNotNull(result3);
    assertEquals(REGION_NAME_2, result3.getName());
  }

  private static List<RegionEntity> buildRegionEntities() {
    var region1 = RegionEntity.builder()
        .name(REGION_NAME_1)
        .nation(BRITISH)
        .airfields(List.of("ALLIES:" + AIRFIELD_ID_1))
        .build();

    var region2 = RegionEntity.builder()
        .name(REGION_NAME_2)
        .nation(BRITISH)
        .airfields(List.of("ALLIES:" + AIRFIELD_ID_2))
        .build();

    var region3 = RegionEntity.builder()
        .name(REGION_NAME_2)
        .nation(UNITED_STATES)
        .airfields(List.of("ALLIES:" + AIRFIELD_ID_2))
        .build();

    return List.of(region1, region2, region3);
  }

  private static List<Region> buildRegions(final List<RegionEntity> entities) {
    return entities.stream()
        .map(RegionServiceTest::buildRegion)
        .toList();
  }

  private static Region buildRegion(final RegionEntity entity) {
    var airfields = entity.getAirfields()
        .stream()
        .map(RegionServiceTest::buildAirfield)
        .toList();

    return Region.builder()
        .name(entity.getName())
        .nation(entity.getNation())
        .airfields(airfields)
        .build();
  }

  private static Airfield buildAirfield(final String airfieldId) {
    var id = new Id(airfieldId);

    return Airfield.builder()
        .id(id)
        .build();
  }
}
