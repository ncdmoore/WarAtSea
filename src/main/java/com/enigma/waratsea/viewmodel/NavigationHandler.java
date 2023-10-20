package com.enigma.waratsea.viewmodel;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.ScenarioHasOptionsEvent;
import com.enigma.waratsea.event.user.StartNewGameEvent;
import com.enigma.waratsea.event.user.StartSavedGameEvent;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.ViewFactory;
import com.enigma.waratsea.view.game.MainView;
import com.enigma.waratsea.view.pregame.NewGameView;
import com.enigma.waratsea.view.pregame.OrderOfBattleSummaryView;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.view.pregame.ScenarioSquadronOptionsView;
import com.enigma.waratsea.view.pregame.SquadronDeploymentView;
import com.enigma.waratsea.view.pregame.StartView;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
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
public class NavigationHandler implements BootStrapped {
  @Data
  private static class Page {
    private boolean active = true;
    private Supplier<View> viewSupplier;
    private Page prev;
    private Page next;

    Page(final Supplier<View> viewSupplier) {
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
    events.getStartNewGameEvent().register(this::handleStartNewGame);
    events.getStartSavedGameEvent().register(this::handleStartSavedGame);
    events.getNavigateEvent().register(this::handleNavigate);
    events.getScenarioOptionsEvent().register(this::handleScenarioOptions);
  }

  private void handleNavigate(final NavigateEvent navigateEvent) {
    var type = navigateEvent.getType();
    var currentPage = navigateEvent.getClazz();
    var stage = navigateEvent.getStage();

    navigationFunctions.get(type).accept(currentPage, stage);
  }

  private void handleScenarioOptions(final ScenarioHasOptionsEvent scenarioHasOptionsEvent) {
    var scenario = scenarioHasOptionsEvent.getScenario();
    var side = scenarioHasOptionsEvent.getSide();

    newGamePageFlow.get(ScenarioSquadronOptionsView.class).active = scenario.hasAllotmentOptions(side);
    newGamePageFlow.get(SquadronDeploymentView.class).active = scenario.hasAllotment(side);
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
    Page optionsPage = new Page(viewFactory::buildScenarioSquadronOptions);
    Page oobPage = new Page(viewFactory::buildOrderOfBattleSummary);
    Page squadronDeploymentPage = new Page(viewFactory::buildSquronDeployment);
    Page mainPage = new Page(viewFactory::buildMainView);

    startPage.setNext(newGamePage);
    newGamePage.setNext(optionsPage);

    optionsPage.setNext(oobPage);
    optionsPage.active = false;

    oobPage.setNext(squadronDeploymentPage);

    squadronDeploymentPage.setNext(mainPage);
    squadronDeploymentPage.active = false;

    newGamePageFlow.put(StartView.class, startPage);
    newGamePageFlow.put(NewGameView.class, newGamePage);
    newGamePageFlow.put(ScenarioSquadronOptionsView.class, optionsPage);
    newGamePageFlow.put(OrderOfBattleSummaryView.class, oobPage);
    newGamePageFlow.put(SquadronDeploymentView.class, squadronDeploymentPage);
    newGamePageFlow.put(MainView.class, mainPage);
  }

  private void buildSavedGameFlow() {
    Page startPage = new Page(viewFactory::buildStart);
    Page savedGamePage = new Page(viewFactory::buildSavedGame);
    Page oobPage = new Page(viewFactory::buildOrderOfBattleSummary);
    Page mainPage = new Page(viewFactory::buildMainView);

    startPage.setNext(savedGamePage);
    savedGamePage.setNext(oobPage);
    oobPage.setNext(mainPage);

    savedGamePageFlow.put(StartView.class, startPage);
    savedGamePageFlow.put(SavedGameView.class, savedGamePage);
    savedGamePageFlow.put(OrderOfBattleSummaryView.class, oobPage);
    savedGamePageFlow.put(MainView.class, mainPage);
  }

  private void handleStartNewGame(final StartNewGameEvent startNewGameEvent) {
    pageFlow = newGamePageFlow;
  }

  private void handleStartSavedGame(final StartSavedGameEvent startSavedGameEvent) {
    pageFlow = savedGamePageFlow;
  }
}
