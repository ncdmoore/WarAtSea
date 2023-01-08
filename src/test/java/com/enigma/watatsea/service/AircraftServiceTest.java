package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.aircraft.AircraftEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.AircraftRepository;
import com.enigma.waratsea.service.impl.AircraftServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AircraftServiceTest {
  @InjectMocks
  private AircraftServiceImpl aircraftService;

  @Mock
  private AircraftRepository aircraftRepository;

  @SuppressWarnings("unused")
  @Spy
  private Events events;

  @Test
  void shouldGetAircraft() {
    var aircraftId = new Id(ALLIES, "aircraft");

    var aircraftEntity = AircraftEntity.builder().id(aircraftId).build();

    given(aircraftRepository.get(aircraftId)).willReturn(aircraftEntity);

    var result = aircraftService.get(aircraftId);

    assertEquals(aircraftId, result.getId());
  }
}
