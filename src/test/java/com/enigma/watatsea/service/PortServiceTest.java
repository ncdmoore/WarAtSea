package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.PortEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.port.Port;
import com.enigma.waratsea.repository.PortRepository;
import com.enigma.waratsea.service.impl.PortServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PortServiceTest {
  @InjectMocks
  private PortServiceImpl portService;

  @Mock
  private PortRepository portRepository;

  @SuppressWarnings("unused")
  @Spy
  private Events events;

  private static final String PORT_ID_1 = "port-name-1";
  private static final String PORT_ID_2 = "port-name-2";

  @Test
  void testGetSinglePort() {
    var id = new Id(ALLIES, PORT_ID_1);

    var portEntity = PortEntity.builder()
        .id(id)
        .build();

    given(portRepository.get(id)).willReturn(portEntity);

    var result = portService.get(id);

    assertEquals(id, result.getId());
  }

  @Test
  void testGetPorts() {
    var portIds = List.of(
        new Id(ALLIES, PORT_ID_1),
        new Id(ALLIES, PORT_ID_2)
    );

    portIds.forEach(id -> {
      var portEntity = buildEntity(id);
      given(portRepository.get(id)).willReturn(portEntity);
    });

    var result = portService.get(portIds);

    assertEquals(portIds.size(), result.size());

    var resultIds = result.stream()
        .map(Port::getId)
        .toList();

    assertEquals(portIds.size(), portService.get(ALLIES).size());

    portIds.forEach(id -> assertTrue(resultIds.contains(id)));
  }

  private PortEntity buildEntity(Id id) {
    return PortEntity.builder()
        .id(id)
        .build();
  }
}
