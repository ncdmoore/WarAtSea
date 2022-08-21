package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.model.Scenario;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.property.PropsFactory;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.ScenarioViewModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.beans.binding.Bindings;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static com.enigma.waratsea.Globals.VIEW_PROPS;

/**
 * This is the new game scenario selection screen.
 */
@Singleton
public class ScenarioView implements View {
    private static final String CSS_FILE = "scenarioView.css";

    private final ListView<Scenario> scenarios = new ListView<>();

    private final Props props;
    private final ResourceProvider resourceProvider;
    private final ScenarioViewModel scenarioViewModel;

    @Inject
    public ScenarioView(final PropsFactory propsFactory,
                        final ResourceProvider resourceProvider,
                        final ScenarioViewModel scenarioViewModel) {
        this.props = propsFactory.create(VIEW_PROPS);
        this.resourceProvider = resourceProvider;
        this.scenarioViewModel = scenarioViewModel;
    }

    @Override
    public void display(final Stage stage) {
        var titlePane = buildTitle();
        var pushButtons = buildPushButtons(stage);
        var scenarioList = buildScenarioList();
        var scenarioDetails = buildScenarioDetails();

        var scenarioPane = new HBox(scenarioList, scenarioDetails);
        scenarioPane.setId("scenario-pane");

        var mainPane = new VBox(scenarioPane, pushButtons);
        mainPane.setId("main-pane");

        var overAllPane = new VBox(titlePane, mainPane);

        var sceneWidth = props.getInt("pregame.scene.width");
        var sceneHeight = props.getInt("pregame.scene.height");

        Scene scene = new Scene(overAllPane, sceneWidth, sceneHeight);
        scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

        stage.setScene(scene);
        stage.show();
    }

    private Node buildTitle() {
        var title = new Label("Scenario Selection");
        title.setId("title");

        var titlePane = new HBox(title);
        titlePane.setId("title-pane");

        return titlePane;
    }

    private Node buildScenarioList() {
        var selectedScenario = scenarioViewModel.getSelectedScenario();

        var scenarioImage = new ImageView();

        scenarioImage.imageProperty().bind(Bindings.createObjectBinding(() ->
                Optional.ofNullable(selectedScenario.getValue())
                        .map(s -> resourceProvider.getImage(s.getName(), s.getImage()))
                        .orElse(null), selectedScenario));

        scenarios.itemsProperty().bind(scenarioViewModel.getScenariosProperty());
        selectedScenario.bind(scenarios.getSelectionModel().selectedItemProperty());

        scenarios.getSelectionModel().selectFirst();

        scenarios.setMaxWidth(props.getInt("pregame.scenario.list.width"));
        scenarios.setMaxHeight(props.getInt("pregame.scenario.list.height"));

        var listPane = new VBox(scenarioImage, scenarios);
        listPane.setId("list-pane");

        return listPane;
    }

    private Node buildScenarioDetails() {
        var dateLabel = new Text("Date:");
        var turnLabel = new Text("Number of Turns:");
        var descriptionLabel = new Text("Description:");

        var selectedScenario = scenarioViewModel.getSelectedScenario();

        var descriptionValue = new Text();
        var dateValue = new Text();
        var turnValue = new Text();

        descriptionValue.setWrappingWidth(props.getInt("pregame.scenario.description.wrap"));

        dateValue.textProperty().bind(Bindings.createStringBinding(() ->
                Optional.ofNullable(selectedScenario.getValue())
                        .map(this::formatDate)
                        .orElse(""), selectedScenario));

        descriptionValue.textProperty().bind(Bindings.createStringBinding(() ->
                Optional.ofNullable(selectedScenario.getValue())
                        .map(Scenario::getDescription)
                        .orElse(""), selectedScenario));

        turnValue.textProperty().bind(Bindings.createStringBinding(() ->
                Optional.ofNullable(selectedScenario.getValue())
                        .map(this::getMaxTurns)
                        .orElse(""), selectedScenario));

        var detailsGrid = new GridPane();
        detailsGrid.add(dateLabel, 0, 0);
        detailsGrid.add(dateValue, 1, 0);
        detailsGrid.add(turnLabel, 0, 1);
        detailsGrid.add(turnValue, 1, 1);
        detailsGrid.add(descriptionLabel, 0, 2);
        detailsGrid.add(descriptionValue, 1, 2);
        detailsGrid.setId("details-grid");
        GridPane.setValignment(descriptionLabel, VPos.TOP);

        return detailsGrid;
    }

    private Node buildPushButtons(final Stage stage) {
        var backButton = new Button("Back");
        backButton.setOnAction(event -> scenarioViewModel.goBack(stage));

        var continueButton = new Button("Continue");
        continueButton.setOnAction(event -> scenarioViewModel.continueOn(stage));

        var hBox =  new HBox(backButton, continueButton);
        hBox.setId("push-buttons-pane");
        return hBox;
    }

    private String formatDate(final Scenario scenario) {
        var dateFormat = props.getString("date.format");
        return scenario
                .getDate()
                .format(DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.ENGLISH));
    }

    private String getMaxTurns(final Scenario scenario) {
        return Integer.toString(scenario.getMaxTurns());
    }
}
