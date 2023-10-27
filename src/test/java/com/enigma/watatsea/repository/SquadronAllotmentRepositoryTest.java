package com.enigma.watatsea.repository;

import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.NationId;
import com.enigma.waratsea.repository.provider.GamePaths;
import com.enigma.waratsea.repository.provider.ResourceProvider;
import com.enigma.waratsea.repository.impl.SquadronAllotmentRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;
import static com.enigma.waratsea.model.Nation.BRITISH;
import static com.enigma.waratsea.model.Side.ALLIES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SquadronAllotmentRepositoryTest {
  @InjectMocks
  private SquadronAllotmentRepositoryImpl squadronAllotmentRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private ResourceProvider resourceProvider;

  private static final String SQUADRON_DIRECTORY = Paths.get("squadrons", "allotment").toString();
  private static final String ALLOTMENT_FILE_NAME = "allotment";
  private static final String TIME_FRAME = "1940";

  @Test
  void shouldGetSquadronAllotment() {
    var allotmentId = new NationId(ALLIES, BRITISH);

    var inputStream = getInputStream();

    given(resourceProvider.getResourceInputStream(any())).willReturn(inputStream);

    var result = squadronAllotmentRepository.get(TIME_FRAME, allotmentId);

    assertTrue(result.isPresent());

    var allotmentEntity = result.get();

    assertEquals(BRITISH, allotmentEntity.getId().getNation());

    assertNotNull(allotmentEntity.getBombers());
    assertEquals(1, allotmentEntity.getBombers().getDice());
    assertEquals(4, allotmentEntity.getBombers().getFactor());
    assertNotNull(allotmentEntity.getBombers().getGroups());
    assertEquals(2, allotmentEntity.getBombers().getGroups().size());

    var group1 = allotmentEntity.getBombers().getGroups().get(0);

    assertEquals(1, group1.getPriority());
    assertEquals(2, group1.getSelectSize());
    assertEquals(1, group1.getAircraft().size());

    var aircraft1 = group1.getAircraft().get(0);
    assertEquals(new Id(ALLIES, "Blenheim"), aircraft1.getId());
    assertEquals(8, aircraft1.getNumber());

    var group2 = allotmentEntity.getBombers().getGroups().get(1);

    assertEquals(2, group2.getPriority());
    assertEquals(1, group2.getSelectSize());
    assertEquals(1, group2.getAircraft().size());

    var aircraft2 = group2.getAircraft().get(0);
    assertEquals(new Id(ALLIES, "Wellington"), aircraft2.getId());
    assertEquals(8, aircraft2.getNumber());

    assertNotNull(allotmentEntity.getFighters());
    assertNotNull(allotmentEntity.getRecon());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", SQUADRON_DIRECTORY, ALLOTMENT_FILE_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}


