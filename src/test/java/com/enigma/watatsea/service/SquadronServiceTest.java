package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.squadron.SquadronEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.mapper.SquadronMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.squadron.Squadron;
import com.enigma.waratsea.repository.SquadronRepository;
import com.enigma.waratsea.service.impl.SquadronServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SquadronServiceTest {
  @InjectMocks
  private SquadronServiceImpl squadronService;

  @Mock
  private SquadronRepository squadronRepository;

  @Mock
  private SquadronMapper squadronMapper;

  @Spy
  @SuppressWarnings("unused")
  private Events events;

  private static final String SQUADRON_ID = "squadron-1";

  @Test
  void shouldGetSquadron() {
    var id = new Id(AXIS, SQUADRON_ID);

    var squadronEntity = SquadronEntity.builder()
        .id(id)
        .build();

    var aircraft = Aircraft.builder()
        .id(new Id(AXIS, "JU88A"))
        .build();

    var squadron = Squadron.builder()
        .id(id)
        .aircraft(aircraft)
        .build();

    given(squadronRepository.get(id)).willReturn(squadronEntity);
    given(squadronMapper.toModel(squadronEntity)).willReturn(squadron);
    var result = squadronService.get(id);

    assertNotNull(result);
    assertEquals(result.getId(), id);
    assertEquals(aircraft, result.getAircraft());
  }
}
