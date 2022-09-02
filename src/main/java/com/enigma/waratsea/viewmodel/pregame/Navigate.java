package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.LoadGameEvent;
import com.enigma.waratsea.event.NewGameEvent;
import com.enigma.waratsea.model.Events;
import com.enigma.waratsea.view.FatalErrorDialog;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.ViewFactory;
import com.enigma.waratsea.view.pregame.NewGameView;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.view.pregame.StartView;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This class provides view or game screen navigation.
 * It is mostly used to navigate through the scenario selection and initial force deployment screens.
 */
@Singleton
public class Navigate {
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

  //
  // scenario selected event triggers minefield and flotilla settings.

  // next and prev navigation events.

  // would fatal dialog event listener. Handled separately from navigation.




  private final ViewFactory viewFactory;
  private final Provider<FatalErrorDialog> fatalErrorDialogProvider;
  private final Map<Class<?>, Page> newGamePageFlow = new HashMap<>();
  private final Map<Class<?>, Page> savedGamePageFlow = new HashMap<>();

  private Map<Class<?>, Page> pageFlow;

  @Inject
  Navigate(final Events events,
           final Provider<FatalErrorDialog> fatalErrorDialogProvider,
           final ViewFactory viewFactory) {
    this.viewFactory = viewFactory;
    this.fatalErrorDialogProvider = fatalErrorDialogProvider;

    buildNewGameFlow();
    buildSavedGameFlow();

    events.getNewGameEvents().register(this::handleNewGame);
    events.getLoadGameEvents().register(this::handleLoadGame);
  }

  public void goNext(final Class<?> currentPage, final Stage stage) {
    Page nextPage = pageFlow.get(currentPage).getNext();

    while (!nextPage.isActive()) {
      nextPage = nextPage.getNext();
    }

    nextPage.getViewSupplier().get().display(stage);
  }

  public void goPrev(final Class<?> currentPage, final Stage stage) {
    Page prevPage = pageFlow.get(currentPage).getPrev();

    while (!prevPage.isActive()) {
      prevPage = prevPage.getPrev();
    }

    prevPage.getViewSupplier().get().display(stage);
  }

  public void goFatalError(final String message) {
    fatalErrorDialogProvider.get().display(message);
  }


  public void buildNewGameFlow() {
    Page startPage = new Page(viewFactory::buildStart);
    Page newGamePage = new Page(viewFactory::buildNewGame);

    startPage.setNext(newGamePage);

    newGamePageFlow.put(StartView.class, startPage);
    newGamePageFlow.put(NewGameView.class, newGamePage);
  }

  public void buildSavedGameFlow() {
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
