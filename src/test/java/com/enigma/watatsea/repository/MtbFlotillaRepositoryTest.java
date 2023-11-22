package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.MtbFlotillaEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.FilePath;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.impl.MtbFlotillaRepositoryImpl;
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
import static com.enigma.waratsea.model.Side.AXIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MtbFlotillaRepositoryTest {
  @InjectMocks
  private MtbFlotillaRepositoryImpl mtbFlotillaRepository;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final String FLOTILLA_DIRECTORY = Paths.get("flotillas", "data").toString();

  private static final List<Id> MTB_FLOTILLA_IDS = List.of(
      new Id(AXIS, "Flotilla-1"),
      new Id(AXIS, "Flotilla-2"),
      new Id(AXIS, "Flotilla-3")
  );

  private static final List<String> FLOTILLA_ONE_SUB_NAMES = List.of("MTB01", "MTB02");
  private static final List<String> FLOTILLA_TWO_SUB_NAMES = List.of("MTB03", "MTB04");
  private static final List<String> FLOTILLA_THREE_SUB_NAMES = List.of("MTB05");

  @Test
  void shouldGetMtbFlotilla() {
    var inputStream = getInputStream();

    given(dataProvider.getInputStream(any(FilePath.class))).willReturn(inputStream);

    var result = mtbFlotillaRepository.get(AXIS);

    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals(MTB_FLOTILLA_IDS, result.stream().map(MtbFlotillaEntity::getId).toList());

    var flotilla1 = result.get(0);

    assertEquals(2, flotilla1.getBoats().size());
    assertEquals(FLOTILLA_ONE_SUB_NAMES, flotilla1.getBoats().stream().map(Id::getName).toList());

    var flotilla2 = result.get(1);

    assertEquals(2, flotilla2.getBoats().size());
    assertEquals(FLOTILLA_TWO_SUB_NAMES, flotilla2.getBoats().stream().map(Id::getName).toList());

    var flotilla3 = result.get(2);

    assertEquals(1, flotilla3.getBoats().size());
    assertEquals(FLOTILLA_THREE_SUB_NAMES, flotilla3.getBoats().stream().map(Id::getName).toList());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", FLOTILLA_DIRECTORY, gamePaths.getMtbFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
