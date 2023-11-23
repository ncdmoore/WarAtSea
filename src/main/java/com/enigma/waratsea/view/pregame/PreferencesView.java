package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.ai.strategy.commandStrategy.CommandStrategyType;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.PreferencesViewModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class PreferencesView implements View {
  private static final String CSS_FILE = "savedGameView.css";

  private final Props props;
  private final ResourceProvider resourceProvider;
  private final PreferencesViewModel preferencesViewModel;

  @Inject
  public PreferencesView(final @Named("View") Props props,
                         final ResourceProvider resourceProvider,
                         final PreferencesViewModel preferencesViewModel) {
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.preferencesViewModel = preferencesViewModel;
  }

  @Override
  public void display(final Stage stage) {
    var titlePane = buildTitle();
    var mainPane = buildPreferences();
    var pushButton = buildPushButton(stage);

    var overAllPane = new BorderPane();
    overAllPane.setTop(titlePane);
    overAllPane.setCenter(mainPane);
    overAllPane.setBottom(pushButton);

    var sceneWidth = props.getInt("pregame.scene.width");
    var sceneHeight = props.getInt("pregame.scene.height");

    Scene scene = new Scene(overAllPane, sceneWidth, sceneHeight);
    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
    stage.show();
  }

  private Node buildTitle() {
    var title = new Label("Preferences");
    title.setId("title");

    var titlePane = new HBox(title);
    titlePane.setId("title-pane");

    return titlePane;
  }

  private Node buildPreferences() {
    return new VBox(buildCommandStrategyPane());
  }

  private Node buildCommandStrategyPane() {
    var instructionLabel = new Label("Select AI Command Strategy:");
    instructionLabel.getStyleClass().add("instruction");

    var strategyGroup = new ToggleGroup();

    var radioButtons = CommandStrategyType.stream()
        .sorted()
        .map(commandStrategyType -> buildRadioButton(commandStrategyType, strategyGroup))
        .collect(Collectors.toMap(
            button -> (CommandStrategyType) button.getUserData(),
            button -> button,
            (type, button) -> button,
            LinkedHashMap::new));

    var currentSelectedStrategy = preferencesViewModel.getSelectedCommandStrategyType();
    radioButtons.get(currentSelectedStrategy).setSelected(true);

    var selectedStrategy = preferencesViewModel.getSelectedCommandStrategy();
    selectedStrategy.bind(strategyGroup.selectedToggleProperty());

    var radioButtonVBox = new VBox(instructionLabel);

    radioButtonVBox.getChildren()
        .addAll(radioButtons.values());

    return radioButtonVBox;
  }

  private RadioButton buildRadioButton(final CommandStrategyType type,
                                       final ToggleGroup toggleGroup) {
    var radioButton =  new RadioButton(type.getValue());
    radioButton.setUserData(type);
    radioButton.setToggleGroup(toggleGroup);

    return radioButton;
  }

  private Node buildPushButton(final Stage stage) {
    var okButton = new Button("Ok");
    okButton.setOnAction(event -> preferencesViewModel.ok(stage));

    var hBox = new HBox(okButton);
    hBox.setId("push-buttons-pane");
    return hBox;
  }
}
