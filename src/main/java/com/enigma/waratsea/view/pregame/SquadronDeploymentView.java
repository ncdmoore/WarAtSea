package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.SquadronDeploymentViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SquadronDeploymentView implements View {
  private static final String CSS_FILE = "mainView.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final SquadronDeploymentViewModel scenarioSquadronOptionsViewModel;


  @Inject
  public SquadronDeploymentView(final @Named("View") Props props,
                                final ResourceProvider resourceProvider,
                                final SquadronDeploymentViewModel scenarioSquadronOptionsViewModel) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.scenarioSquadronOptionsViewModel = scenarioSquadronOptionsViewModel;
  }

  @Override
  public void display(final Stage stage) {
    var overAllPane = new BorderPane();

    var label = new Label("Squadron Deployment");
    overAllPane.setCenter(label);

    var screenBounds = getSceneBounds(stage);

    var scene = new Scene(overAllPane, screenBounds.getWidth(), screenBounds.getHeight());
    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
    stage.show();
  }

  private Rectangle2D getSceneBounds(final Stage stage) {
    var screenBounds = Screen.getPrimary().getVisualBounds();

    stage.setX(screenBounds.getMinX());
    stage.setY(screenBounds.getMinY());
    stage.setWidth(screenBounds.getWidth());
    stage.setHeight(screenBounds.getHeight());

    return screenBounds;
  }

}
