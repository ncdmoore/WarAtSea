package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.cargo.ManifestEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.ManifestRepositoryImpl;
import com.enigma.waratsea.repository.impl.ResourceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ManifestRepositoryTest {
  @InjectMocks
  private ManifestRepositoryImpl manifestRepository;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private ResourceProvider resourceProvider;

  private static final String CARGO_DIRECTORY = "cargo";

  private static final List<String> MANIFEST_IDS = List.of(
      "cargo-1", "cargo-2", "cargo-3"
  );

  private static final List<String> CARGO1_SHIP_NAMES = List.of(
    "TL01", "TL02", "TL03", "TL04"
  );

  private static final List<String> CARGO2_SHIP_NAMES = List.of(
      "Southampton", "Gloucester", "Ilex", "Janus"
  );

  private static final List<String> CARGO3_SHIP_NAMES = List.of(
      "TL05", "TL06"
  );

  @Test
  void shouldGetCargoManifest() {
    var inputStream = getInputStream();

    given(resourceProvider.getResourceInputStream(any())).willReturn(inputStream);

    var result = manifestRepository.get(ALLIES);

    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals(MANIFEST_IDS, result.stream().map(ManifestEntity::getId).toList());

    var cargo1 = result.get(0);

    assertEquals("Gibraltar", cargo1.getOriginPort());
    assertTrue(cargo1.getDestinationPorts().contains("Malta"));
    assertEquals(CARGO1_SHIP_NAMES, cargo1.getShips().stream().map(Id::getName).toList());

    var cargo2 = result.get(1);

    assertEquals("Athens", cargo2.getOriginPort());
    assertTrue(cargo2.getDestinationPorts().contains("Malta"));
    assertEquals(CARGO2_SHIP_NAMES, cargo2.getShips().stream().map(Id::getName).toList());

    var cargo3 = result.get(2);

    assertEquals("Alexandria", cargo3.getOriginPort());
    assertTrue(cargo3.getDestinationPorts().contains("Malta"));
    assertEquals(CARGO3_SHIP_NAMES, cargo3.getShips().stream().map(Id::getName).toList());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", CARGO_DIRECTORY, gamePaths.getCargoFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
