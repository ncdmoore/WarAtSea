package com.enigma.watatsea.service;

import com.enigma.waratsea.entity.MtbFlotillaEntity;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.mapper.MtbFlotillaMapper;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.MtbFlotilla;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.repository.MtbFlotillaRepository;
import com.enigma.waratsea.service.impl.MtbFlotillaServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MtbFlotillaServiceTest {
  @InjectMocks
  private MtbFlotillaServiceImpl mtbFlotillaService;

  @Mock
  private MtbFlotillaRepository mtbFlotillaRepository;

  @Mock
  private MtbFlotillaMapper mtbFlotillaMapper;

  @Spy
  @SuppressWarnings("unused")
  private Events events;

  private static final List<String> mtbNames = List.of("mtb1, mtb2");

  @ParameterizedTest
  @EnumSource(value = Side.class, names = {"ALLIES", "AXIS"})
  void shouldGetMtbFlotillas(final Side side) {
    var mtbEntities = buildEntities(side);
    var mtbFlotillas = buildMtbFlotillas(side);

    given(mtbFlotillaRepository.get(side)).willReturn(mtbEntities);
    given(mtbFlotillaMapper.entitiesToModels(mtbEntities)).willReturn(mtbFlotillas);

    var result = mtbFlotillaService.get(side);

    assertNotNull(result);
    assertEquals(mtbNames.size(), result.size());

    var numberOfFlotillas = result.stream()
        .map(mtbFlotilla -> mtbFlotilla.getId().getName())
        .filter(mtbNames::contains)
        .count();

    assertEquals(mtbNames.size(), numberOfFlotillas);
  }

  private List<MtbFlotillaEntity> buildEntities(final Side side) {
    return mtbNames.stream()
        .map(name -> new Id(side, name))
        .map(this::buildEntity)
        .toList();
  }

  private MtbFlotillaEntity buildEntity(final Id id) {
    return MtbFlotillaEntity.builder()
        .id(id)
        .build();
  }

  private MtbFlotilla buildFlotilla(final Id id) {
    return MtbFlotilla.builder()
        .id(id)
        .build();
  }

  private List<MtbFlotilla> buildMtbFlotillas(final Side side) {
    return mtbNames.stream()
        .map(name -> new Id(side, name))
        .map(this::buildFlotilla)
        .toList();
  }
}
