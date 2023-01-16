package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.OrderOfBattleSummaryViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OrderOfBattleSummaryView implements View {
  private static final String CSS_FILE = "savedGameView.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final OrderOfBattleSummaryViewModel orderOfBattleSummaryViewModel;

  @Inject
  public OrderOfBattleSummaryView(final @Named("View") Props props,
                                  final ResourceProvider resourceProvider,
                                  final OrderOfBattleSummaryViewModel orderOfBattleSummaryViewModel) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.orderOfBattleSummaryViewModel = orderOfBattleSummaryViewModel;
  }

  @Override
  public void display(Stage stage) {
    var titlePane = buildTitle();
    var pushButtons = buildPushButtons(stage);

    var overAllPane = new BorderPane();
    overAllPane.setTop(titlePane);
    overAllPane.setBottom(pushButtons);

    var sceneWidth = props.getInt("pregame.scene.width");
    var sceneHeight = props.getInt("pregame.scene.height");

    Scene scene = new Scene(overAllPane, sceneWidth, sceneHeight);
    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
    stage.show();
  }


  private Node buildTitle() {
    var title = new Label("Order of Battle Summary");
    title.setId("title");

    var titlePane = new HBox(title);
    titlePane.setId("title-pane");

    return titlePane;
  }

  private Node buildPushButtons(final Stage stage) {
    var backButton = new Button("Back");
    backButton.setOnAction(event -> orderOfBattleSummaryViewModel.goBack(stage));

    var continueButton = new Button("Continue");
    continueButton.setOnAction(event -> orderOfBattleSummaryViewModel.continueOn(stage));

    var hBox = new HBox(backButton, continueButton);
    hBox.setId("push-buttons-pane");
    return hBox;
  }
}
