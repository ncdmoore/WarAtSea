package com.enigma.waratsea.view.pregame;

import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.property.PropsFactory;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.pregame.StartViewModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.enigma.waratsea.Globals.VIEW_PROPS;

/**
 * This is the game starting screen.
 * It shows the game name and the main game start buttons.
 */
@Singleton
public class StartView implements View {
    private static final String CSS_FILE = "startView.css";

    private final Props viewProps;
    private final ResourceProvider resourceProvider;
    private final StartViewModel startViewModel;

    @Inject
    public StartView(final PropsFactory propsFactory,
                     final ResourceProvider resourceProvider,
                     final StartViewModel startViewModel) {
        this.viewProps = propsFactory.create(VIEW_PROPS);
        this.resourceProvider = resourceProvider;
        this.startViewModel = startViewModel;
    }

    @Override
    public void display(final Stage stage) {
        Node titlePane = buildTitlePane();
        Node buttonPane = buildButtonPane(stage);
        VBox mainPane = buildMainPane(titlePane, buttonPane);

        int sceneWidth = viewProps.getInt("pregame.scene.width");
        int sceneHeight = viewProps.getInt("pregame.scene.height");

        Scene scene = new Scene(mainPane, sceneWidth, sceneHeight);
        scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

        stage.setScene(scene);
        stage.show();
    }

    private Node buildTitlePane() {
        int flagHBoxWidth = viewProps.getInt("pregame.image.width");

        ImageView alliesFlag = resourceProvider.getGameImageView(viewProps.getString("allies.flag.large.image"));

        Region leftRegion = new Region();
        HBox.setHgrow(leftRegion, Priority.ALWAYS);

        Label title = new Label(viewProps.getString("game.title"));
        title.setId("start-title");

        Region rightRegion = new Region();
        HBox.setHgrow(rightRegion, Priority.ALWAYS);

        ImageView axisFlag = resourceProvider.getGameImageView(viewProps.getString("axis.flag.large.image"));

        HBox hBox = new HBox(alliesFlag, leftRegion, title, rightRegion, axisFlag);
        hBox.setMaxWidth(flagHBoxWidth);
        hBox.setId("start-title-pane");

        return hBox;
    }

    private Node buildButtonPane(final Stage stage) {
        ImageView backgroundImageView = getBackgroundImage();
        Node buttons = buildButtons(stage);

        return new StackPane(backgroundImageView, buttons);
    }

    private VBox buildMainPane(final Node titlePane, final Node buttonPane) {
        VBox mainPane = new VBox(titlePane, buttonPane);
        mainPane.setId("start-main-pane");
        return mainPane;
    }

    private Node buildButtons(final Stage stage) {
        Button newButton = buildButton("New Game");
        newButton.setOnAction(event -> startViewModel.newGame(stage));

        Button savedButton = buildButton("Saved Game");
        savedButton.setOnAction(event -> startViewModel.savedGame());

        Button optionsButton = buildButton("Options");
        optionsButton.setOnAction(event -> startViewModel.options());

        Button quitButton = buildButton("Quit Game");
        quitButton.setOnAction(event -> startViewModel.quitGame(stage));

        VBox vBox = new VBox(newButton, savedButton, optionsButton, quitButton);
        vBox.setId("start-command-buttons-vbox");

        return vBox;
    }

    private Button buildButton(final String title) {
        int buttonWidth = viewProps.getInt("pregame.start.button.width");

        Button button = new Button(title);
        button.setMinWidth(buttonWidth);
        button.setMaxWidth(buttonWidth);

        return button;
    }

    private ImageView getBackgroundImage() {
        return resourceProvider.getGameImageView(viewProps.getString("pregame.start.image"));
    }
}
