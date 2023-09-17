package com.enigma.waratsea.viewmodel;

import com.enigma.waratsea.BootStrapped;
import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.view.dialog.ErrorDialog;
import com.enigma.waratsea.viewmodel.events.ErrorEvent;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ErrorHandler implements BootStrapped {
  private final Provider<ErrorDialog> errorDialogProvider;

  @Inject
  public ErrorHandler(final Events events,
                      final Provider<ErrorDialog> errorDialogProvider) {
    this.errorDialogProvider = errorDialogProvider;

    registerEvents(events);
  }

  private void registerEvents(final Events events) {
    events.getErrorEvents().register(this::handleError);
  }

  private void handleError(final ErrorEvent errorEvent) {
    var message = errorEvent.getMessage();
    var fatal = errorEvent.isFatal();

    errorDialogProvider.get().display(message, fatal);
  }
}
