package com.enigma.waratsea.event;

import com.enigma.waratsea.model.GameName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameNameEvent implements Event {
  private final GameName gameName;
}
