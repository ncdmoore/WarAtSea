package com.enigma.watatsea.repository;

import com.enigma.waratsea.entity.option.AllotmentModificationEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.option.OptionId;
import com.enigma.waratsea.model.squadron.AllotmentType;
import com.enigma.waratsea.repository.impl.GamePaths;
import com.enigma.waratsea.repository.impl.ResourceProvider;
import com.enigma.waratsea.repository.impl.SquadronAllotmentModRepositoryImpl;
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
import static com.enigma.waratsea.model.Nation.GERMAN;
import static com.enigma.waratsea.model.Side.AXIS;
import static com.enigma.waratsea.model.option.OptionType.ALLOTMENT;
import static com.enigma.waratsea.model.squadron.AllotmentType.BOMBER;
import static com.enigma.waratsea.model.squadron.AllotmentType.FIGHTER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SquadronAllotmentModRepositoryTest {
  @InjectMocks
  private SquadronAllotmentModRepositoryImpl squadronAllotmentModRepository;

  @Spy
  @SuppressWarnings("unused")
  private GamePaths gamePaths;

  @Mock
  private ResourceProvider resourceProvider;

  private static final String MODIFICATION_DIRECTORY = Paths.get("options", "allotment").toString();
  private static final String MODIFICATION_FILE_NAME = "allotmentModification";

  private static final OptionId option1 = new OptionId(ALLOTMENT, "option-1");
  private static final OptionId option2 = new OptionId(ALLOTMENT, "option-2");
  private static final OptionId option3 = new OptionId(ALLOTMENT, "option-3");

  private static final Id allotmentId = new Id(AXIS, GERMAN.name());

  private static final List<OptionId> modIds = List.of(
      option1,
      option2,
      option3
  );

  private static final List<String> texts = List.of(
      "Add 1-6 fighter squadrons",
      "Add 1-6 bomber squadrons",
      "Add 1-3 fighter squadrons and 1-3 bomber squadrons"
  );

  @Test
  void shouldGetAllotmentModifications() {
    var modificationId = new Id(AXIS, GERMAN.toLower());

    var inputStream = getInputStream();

    given(resourceProvider.getResourceInputStream(any())).willReturn(inputStream);

    var result = squadronAllotmentModRepository.get(modificationId);

    assertNotNull(result);
    assertEquals(3, result.size());

    result.forEach(modification -> {
      assertTrue(modIds.contains(modification.getId()));
      assertTrue(texts.contains(modification.getText()));
    });

    verifyModification(result, option1, FIGHTER);
    verifyModification(result, option2, BOMBER);
    verifyModifications(result);
  }

  private void verifyModification(final List<AllotmentModificationEntity> mods, final OptionId optionId, final AllotmentType type) {
    var modification = mods.stream()
        .filter(mod -> optionId.equals(mod.getId()))
        .findAny()
        .orElseThrow();

    assertEquals(1, modification.getModifications().size());
    assertEquals(allotmentId, modification.getModifications().get(0).getAllotmentId());
    assertEquals(type, modification.getModifications().get(0).getType());
    assertEquals(2, modification.getModifications().get(0).getDice());
    assertEquals(0, modification.getModifications().get(0).getFactor());
  }

  private void verifyModifications(final List<AllotmentModificationEntity> mods) {
    var modification = mods.stream()
        .filter(mod -> option3.equals(mod.getId()))
        .findAny()
        .orElseThrow();

    assertEquals(2, modification.getModifications().size());

    assertEquals(allotmentId, modification.getModifications().get(0).getAllotmentId());
    assertEquals(FIGHTER, modification.getModifications().get(0).getType());
    assertEquals(1, modification.getModifications().get(0).getDice());
    assertEquals(0, modification.getModifications().get(0).getFactor());

    assertEquals(allotmentId, modification.getModifications().get(1).getAllotmentId());
    assertEquals(BOMBER, modification.getModifications().get(1).getType());
    assertEquals(1, modification.getModifications().get(1).getDice());
    assertEquals(0, modification.getModifications().get(1).getFactor());
  }

  private InputStream getInputStream() {
    var fullPath = Paths.get("/", MODIFICATION_DIRECTORY, MODIFICATION_FILE_NAME + JSON_EXTENSION).toString();

    return getClass().getResourceAsStream(fullPath);
  }
}
