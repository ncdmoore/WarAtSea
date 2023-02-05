package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.SubmarineFlotillaEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.impl.DataProvider;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.SubmarineFlotillaRepositoryImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SubmarineFlotillaRepositoryTest {
  @InjectMocks
  private SubmarineFlotillaRepositoryImpl submarineFlotillaRepository;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String FLOTILLA_DIRECTORY = Paths.get("flotillas", "data").toString();

  private static final List<Id> SUBMARINE_FLOTILLA_IDS = List.of(
      new Id(ALLIES, "Flotilla-1"),
      new Id(ALLIES, "Flotilla-2")
  );

  private static final List<String> FLOTILLA_ONE_SUB_NAMES = List.of("T1", "T2");
  private static final List<String> FLOTILLA_TWO_SUB_NAMES = List.of("T3", "T4");

  @Test
  void shouldGetSubmarineFlotilla() {
    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = submarineFlotillaRepository.get(ALLIES);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(SUBMARINE_FLOTILLA_IDS, result.stream().map(SubmarineFlotillaEntity::getId).toList());

    var flotilla1 = result.get(0);

    assertEquals(2, flotilla1.getSubs().size());
    assertEquals(FLOTILLA_ONE_SUB_NAMES, flotilla1.getSubs().stream().map(Id::getName).toList());

    var flotilla2 = result.get(1);

    assertEquals(2, flotilla2.getSubs().size());
    assertEquals(FLOTILLA_TWO_SUB_NAMES, flotilla2.getSubs().stream().map(Id::getName).toList());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", FLOTILLA_DIRECTORY, gamePaths.getSubmarineFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
