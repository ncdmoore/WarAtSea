package com.enigma.waratsea.event;

import com.enigma.waratsea.model.GameName;

public record GameNameEvent(GameName gameName) implements Event {
}
