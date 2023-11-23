package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.ai.strategy.commandStrategy.CommandStrategyType;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.model.preferences.Preferences;
import com.enigma.waratsea.service.PreferencesService;
import com.enigma.waratsea.view.pregame.PreferencesView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import lombok.Getter;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;

public class PreferencesViewModel {
  private final Events events;
  private final PreferencesService preferencesService;

  @Getter
  private final ObjectProperty<Toggle> selectedCommandStrategy = new SimpleObjectProperty<>();

  @Getter
  private CommandStrategyType selectedCommandStrategyType;

  private Preferences preferences;

  @Inject
  public PreferencesViewModel(final Events events,
                              final PreferencesService preferencesService) {
    this.events = events;
    this.preferencesService = preferencesService;

    loadPreferences();
  }

  public void ok(final Stage stage) {
    selectedCommandStrategyType = getSelectedCommandStrategyFromToggle(getSelectedCommandStrategy().get());

    preferences.getAi()
        .setCommandStrategy(selectedCommandStrategyType);

    preferencesService.save(preferences);

    goToPreviousPage(stage);
  }

  private void loadPreferences() {
    preferences = preferencesService.get();

    selectedCommandStrategyType = preferences.getAi()
        .getCommandStrategy();
  }

  private CommandStrategyType getSelectedCommandStrategyFromToggle(final Toggle toggle) {
    return (CommandStrategyType) toggle.getToggleGroup()
        .getToggles()
        .stream()
        .filter(Toggle::isSelected)
        .findFirst()
        .orElseThrow()
        .getUserData();
  }

  private void goToPreviousPage(final Stage stage) {
    events.getNavigateEvent()
        .fire(buildNavigateEvent(stage));
  }

  private NavigateEvent buildNavigateEvent(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(PreferencesView.class)
        .stage(stage)
        .type(BACKWARD)
        .build();
  }
}
