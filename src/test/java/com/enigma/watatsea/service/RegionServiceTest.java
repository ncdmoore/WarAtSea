package com.enigma.watatsea.service;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.mapper.RegionMapper;
import com.enigma.waratsea.repository.RegionRepository;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.impl.RegionServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {
  @InjectMocks
  private RegionServiceImpl regionService;

  @Mock
  private RegionRepository regionRepository;

  @Mock
  private RegionMapper regionMapper;

  @Mock
  private GameService gameService;

  @Spy
  private Events events;
}
