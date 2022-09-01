package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.view.FatalErrorDialog;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.ViewFactory;
import com.enigma.waratsea.view.pregame.NewGameView;
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

  private final Provider<FatalErrorDialog> fatalErrorDialogProvider;
  private final Map<Class<?>, Page> newGamePages = new HashMap<>();

  @Inject
  Navigate(final Provider<FatalErrorDialog> fatalErrorDialogProvider,
           final ViewFactory viewFactory) {

    this.fatalErrorDialogProvider = fatalErrorDialogProvider;

    Page startPage = new Page(viewFactory::buildStart);
    Page scenarioPage = new Page(viewFactory::buildNewGame);

    startPage.setNext(scenarioPage);

    newGamePages.put(StartView.class, startPage);
    newGamePages.put(NewGameView.class, scenarioPage);
  }

  public void goNext(final Class<?> currentPage, final Stage stage) {
    Page nextPage = newGamePages.get(currentPage).getNext();

    while (!nextPage.isActive()) {
      nextPage = nextPage.getNext();
    }

    nextPage.getViewSupplier().get().display(stage);
  }

  public void goPrev(final Class<?> currentPage, final Stage stage) {
    Page prevPage = newGamePages.get(currentPage).getPrev();

    while (!prevPage.isActive()) {
      prevPage = prevPage.getPrev();
    }

    prevPage.getViewSupplier().get().display(stage);
  }

  public void goFatalError(final String message) {
    fatalErrorDialogProvider.get().display(message);
  }
}
