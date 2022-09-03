package com.enigma.waratsea.viewmodel;

import com.enigma.waratsea.event.LoadGameEvent;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.enigma.waratsea.event.NewGameEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.ViewFactory;
import com.enigma.waratsea.view.pregame.NewGameView;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.view.pregame.StartView;
import com.enigma.waratsea.viewmodel.events.NavigationType;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;
import static com.enigma.waratsea.viewmodel.events.NavigationType.FORWARD;

/**
 * This class provides view or game screen navigation.
 * It is mostly used to navigate through the scenario selection and initial force deployment screens.
 */
@Singleton
public class NavigationHandler {
  @Data
  private static class Page {
    private boolean active = true;
    private Supplier<View> viewSupplier;
    private Page prev;
    private Page next;

    public Page(final Supplier<View> viewSupplier) {
      this.viewSupplier = viewSupplier;
    }

    public void setNext(final Page nextPage) {
      next = nextPage;
      nextPage.setPrev(this);
    }
  }
  private final ViewFactory viewFactory;
  private final Map<Class<?>, Page> newGamePageFlow = new HashMap<>();
  private final Map<Class<?>, Page> savedGamePageFlow = new HashMap<>();

  private final Map<NavigationType, BiConsumer<Class<?>, Stage>> navigationFunctions = Map.of(
      FORWARD, this::goNext,
      BACKWARD, this::goPrev
  );

  private Map<Class<?>, Page> pageFlow;

  @Inject
  NavigationHandler(final Events events,
                    final ViewFactory viewFactory) {
    this.viewFactory = viewFactory;

    buildNewGameFlow();
    buildSavedGameFlow();

    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getNewGameEvents().register(this::handleNewGame);
    events.getLoadGameEvents().register(this::handleLoadGame);
    events.getNavigateEvents().register(this::handleNavigate);
  }

  private void handleNavigate(final NavigateEvent navigateEvent) {
    var type = navigateEvent.getType();
    var currentPage = navigateEvent.getClazz();
    var stage = navigateEvent.getStage();

    navigationFunctions.get(type).accept(currentPage, stage);
  }

  private void goNext(final Class<?> currentPage, final Stage stage) {
    Page nextPage = pageFlow.get(currentPage).getNext();

    while (!nextPage.isActive()) {
      nextPage = nextPage.getNext();
    }

    nextPage.getViewSupplier().get().display(stage);
  }

  private void goPrev(final Class<?> currentPage, final Stage stage) {
    Page prevPage = pageFlow.get(currentPage).getPrev();

    while (!prevPage.isActive()) {
      prevPage = prevPage.getPrev();
    }

    prevPage.getViewSupplier().get().display(stage);
  }

  private void buildNewGameFlow() {
    Page startPage = new Page(viewFactory::buildStart);
    Page newGamePage = new Page(viewFactory::buildNewGame);

    startPage.setNext(newGamePage);

    newGamePageFlow.put(StartView.class, startPage);
    newGamePageFlow.put(NewGameView.class, newGamePage);
  }

  private void buildSavedGameFlow() {
    Page startPage = new Page(viewFactory::buildStart);
    Page savedGamePage = new Page(viewFactory::buildSavedGame);

    startPage.setNext(savedGamePage);

    savedGamePageFlow.put(StartView.class, startPage);
    savedGamePageFlow.put(SavedGameView.class, savedGamePage);
  }

  private void handleNewGame(final NewGameEvent newGameEvent) {
    pageFlow = newGamePageFlow;
  }

  private void handleLoadGame(final LoadGameEvent loadGameEvent) {
    pageFlow = savedGamePageFlow;
  }
}
