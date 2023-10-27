package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.mission.MissionEntity;
import com.enigma.waratsea.repository.provider.DataProvider;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.impl.MissionRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MissionRepositoryTest {
  @InjectMocks
  private MissionRepositoryImpl missionRepository;

  @Spy
  private GamePaths gamePaths;

  @Mock
  private DataProvider dataProvider;

  private static final Set<String> IDS = Set.of(
      "Mission-1", "Mission-2", "Mission-3",
      "Mission-4", "Mission-5", "Mission-6"
  );

  @Test
  void shouldGetMissions() {
    var inputStream = getInputStream();

    given(dataProvider.getDataInputStream(any())).willReturn(inputStream);

    var result = missionRepository.get(ALLIES);

    assertNotNull(result);
    assertFalse(result.isEmpty());

    var ids = result.stream()
        .map(MissionEntity::getId)
        .collect(Collectors.toSet());

    assertEquals(IDS, ids);
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/",
        gamePaths.getMissionDirectory(),
        gamePaths.getMissionFileName() + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
