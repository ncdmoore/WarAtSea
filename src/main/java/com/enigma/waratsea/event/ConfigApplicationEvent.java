package com.enigma.waratsea.event;

import com.enigma.waratsea.model.GameName;

public record ConfigApplicationEvent(GameName gameName) implements Event {
}
