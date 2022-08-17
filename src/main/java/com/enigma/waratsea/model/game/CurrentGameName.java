package com.enigma.waratsea.model.game;

import com.google.inject.Singleton;
import lombok.Data;

@Singleton
@Data
public class CurrentGameName {
    private GameName value = GameName.BOMB_ALLEY;  // The default game is Bomb Alley.

    public String toString() {
        return value.getValue();
    }
}
