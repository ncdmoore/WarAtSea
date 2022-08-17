package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.properties.ViewProps;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.ScenarioViewModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is the new game scenario selection screen.
 */
@Singleton
public class ScenarioView implements View {
    private static final String CSS_FILE = "scenarioView.css";

    private final ViewProps viewProps;
    private final ResourceProvider resourceProvider;
    private final ScenarioViewModel scenarioViewModel;

    @Inject
    public ScenarioView(final ViewProps viewProps,
                        final ResourceProvider resourceProvider,
                        final ScenarioViewModel scenarioViewModel) {
        this.viewProps = viewProps;
        this.resourceProvider = resourceProvider;
        this.scenarioViewModel = scenarioViewModel;
    }

    @Override
    public void display(final Stage stage) {
        Node title = buildTitle();

        Node pushButtons = buildPushButtons(stage);

        VBox vBox = new VBox(title, pushButtons);

        int sceneWidth = viewProps.getInt("pregame.scene.width");
        int sceneHeight = viewProps.getInt("pregame.scene.height");

        Scene scene = new Scene(vBox, sceneWidth, sceneHeight);
        scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

        stage.setScene(scene);
        stage.show();
    }

    private Node buildTitle() {
        Label title = new Label("Select a Scenario");
        title.setId("scenario-title");

        HBox titlePane = new HBox(title);
        titlePane.setId("scenario-title-pane");

        return titlePane;
    }

    private Node buildPushButtons(final Stage stage) {
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> scenarioViewModel.goBack(stage));

        Button continueButton = new Button("Continue");
        continueButton.setOnAction(event -> scenarioViewModel.continueOn(stage));

        HBox hBox =  new HBox(backButton, continueButton);
        hBox.setId("scenario-push-buttons");
        return hBox;
    }
}
