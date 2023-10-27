package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.impl.PortRepositoryImpl;
import com.enigma.waratsea.repository.provider.GamePaths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.port.PortSize.MAJOR;
import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PortRepositoryTest {
  @InjectMocks
  private PortRepositoryImpl portRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String PORT_DIRECTORY = "ports";
  private static final String PORT_NAME = "Scapa_Flow";
  private static final String PORT_TITLE = "Scapa Flow";
  private static final String GRID_REFERENCE = "L43";

  @Test
  void shouldGetPortEntity() {
    var portId = new Id(ALLIES, PORT_NAME);

    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = portRepository.get(portId);

    assertNotNull(result);
    assertEquals(portId, result.getId());
    assertEquals(PORT_TITLE, result.getTitle());
    assertEquals(MAJOR, result.getSize());
    assertEquals(15, result.getMaxAntiAir());
    assertEquals(15, result.getAntiAir());
    assertEquals(GRID_REFERENCE, result.getGridReference());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get(  "/", PORT_DIRECTORY, PORT_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
