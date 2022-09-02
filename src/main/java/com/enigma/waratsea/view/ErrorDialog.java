package com.enigma.waratsea.view;

import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Singleton
public class ErrorDialog {
  private static final String CSS_FILE = "fatalError.css";
  private final Props props;
  private final ResourceProvider resourceProvider;

  @Inject
  ErrorDialog(final @Named("View") Props props,
              final ResourceProvider resourceProvider) {
    this.props = props;
    this.resourceProvider = resourceProvider;
  }

  public void display(final String message, final boolean fatal) {
    var stageWidth = props.getInt("fatal.dialog.width");
    var stage = new Stage();

    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Fatal Error");
    stage.setMinWidth(stageWidth);

    var label = new Label(message);

    var ok = new Button("Ok");
    ok.setOnAction(event -> close(stage, fatal));

    var vBox = new VBox(label, ok);
    vBox.setId("error-main-pane");

    var width = props.getInt("fatal.dialog.scene.width");
    var height = props.getInt("fatal.dialog.scene.height");

    var scene = new Scene(vBox, width, height);

    scene.getStylesheets().add(resourceProvider.getCss(CSS_FILE));

    stage.setScene(scene);
    stage.showAndWait();
  }

  private void close(final Stage stage, final boolean fatal) {
    stage.close();
    if (fatal) {
      Platform.exit();
    }
  }
}
