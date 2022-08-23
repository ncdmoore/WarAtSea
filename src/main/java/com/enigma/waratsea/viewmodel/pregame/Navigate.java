package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.pregame.ScenarioView;
import com.enigma.waratsea.view.pregame.StartView;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides view or game screen navigation.
 * It is mostly used to navigate through the scenario selection and initial force deployment screens.
 */
@Singleton
public class Navigate {
  @Data
  private static class Page {
    private boolean active = true;
    private Provider<? extends View> view;
    private Page prev;
    private Page next;

    public Page(final Provider<? extends View> view) {
      this.view = view;
    }

    public void setNext(final Page nextPage) {
      next = nextPage;
      nextPage.setPrev(this);
    }
  }

  private final Map<Class<?>, Page> newGamePages = new HashMap<>();

  @Inject
  Navigate(final Provider<StartView> startViewProvider,
           final Provider<ScenarioView> scenarioViewProvider) {

    Page startPage = new Page(startViewProvider);
    Page scenarioPage = new Page(scenarioViewProvider);

    startPage.setNext(scenarioPage);

    newGamePages.put(StartView.class, startPage);
    newGamePages.put(ScenarioView.class, scenarioPage);
  }

  public void goNext(final Class<?> currentPage, final Stage stage) {
    Page nextPage = newGamePages.get(currentPage).getNext();

    while (!nextPage.isActive()) {
      nextPage = nextPage.getNext();
    }

    nextPage.getView().get().display(stage);
  }

  public void goPrev(final Class<?> currentPage, final Stage stage) {
    Page prevPage = newGamePages.get(currentPage).getPrev();

    while (!prevPage.isActive()) {
      prevPage = prevPage.getPrev();
    }

    prevPage.getView().get().display(stage);
  }
}
