package com.enigma.waratsea.view;

import com.google.inject.name.Named;

public interface ViewFactory {
    @Named("Start")
    View buildStart();

    @Named("NewGame")
    View buildNewGame();

    @Named("SavedGame")
    View buildSavedGame();

    @Named("OrderOfBattleSummary")
    View buildOrderOfBattleSummary();
}
