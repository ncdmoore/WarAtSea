package com.enigma.waratsea.viewmodel.pregame;

import com.enigma.waratsea.event.Events;
import com.enigma.waratsea.event.user.SaveGameEvent;
import com.enigma.waratsea.model.Game;
import com.enigma.waratsea.model.MtbFlotilla;
import com.enigma.waratsea.model.Nation;
import com.enigma.waratsea.model.Side;
import com.enigma.waratsea.model.SubmarineFlotilla;
import com.enigma.waratsea.model.player.Player;
import com.enigma.waratsea.model.taskForce.TaskForce;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.view.pregame.OrderOfBattleSummaryView;
import com.enigma.waratsea.view.resources.ResourceProvider;
import com.enigma.waratsea.viewmodel.events.NavigateEvent;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.enigma.waratsea.viewmodel.events.NavigationType.BACKWARD;
import static com.enigma.waratsea.viewmodel.events.NavigationType.FORWARD;

@Slf4j
public class OrderOfBattleSummaryViewModel {
  private final Events events;
  private final Props props;
  private final ResourceProvider resourceProvider;
  private final GameService gameService;

  @Getter
  private final ObjectProperty<Image> flag = new SimpleObjectProperty<>();

  @Getter
  private final ObjectProperty<Image> taskForceImage = new SimpleObjectProperty<>();

  @Getter
  private final ObjectProperty<Image> submarineFlotillaImage = new SimpleObjectProperty<>();

  @Getter
  private final BooleanProperty submarineNotPresent = new SimpleBooleanProperty();

  @Getter
  private final ObjectProperty<Image> mtbFlotillaImage = new SimpleObjectProperty<>();

  @Getter
  private final BooleanProperty mtbNotPresent = new SimpleBooleanProperty();

  @Getter
  private final ObjectProperty<Image> airForceImage = new SimpleObjectProperty<>();

  @Getter
  private final ListProperty<TaskForce> taskForces = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ListProperty<SubmarineFlotilla> submarineFlotillas = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ListProperty<MtbFlotilla> mtbFlotillas = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ListProperty<Nation> nations = new SimpleListProperty<>(FXCollections.emptyObservableList());

  @Getter
  private final ObjectProperty<Side> side = new SimpleObjectProperty<>();

  @Getter
  private final ObjectProperty<Player> player = new SimpleObjectProperty<>();

  @Inject
  public OrderOfBattleSummaryViewModel(final Events events,
                                       final @Named("View") Props props,
                                       final ResourceProvider resourceProvider,
                                       final GameService gameService) {
    this.events = events;
    this.props = props;
    this.resourceProvider = resourceProvider;
    this.gameService = gameService;

    var game = gameService.getGame();

    setSide(game);
    setPlayer(game);
    setFlag(game);
    setTaskForceImage(game);
    setSubmarineFlotillaImage(game);
    setMtbFlotillaImage(game);
    setAirForceImage(game);
    setTaskForces(game);
    setSubmarineFlotillas(game);
    setMtbFlotillas(game);
    setNations(game);
  }

  public void goBack(final Stage stage) {
    events.getNavigateEvent().fire(buildBackwardNav(stage));
  }

  public void continueOn(final Stage stage) {
    var selectedScenarioName = gameService.getGame()
        .getScenario()
        .getName();

    events.getSaveGameEvent().fire(new SaveGameEvent(selectedScenarioName));
    events.getNavigateEvent().fire(buildForwardNav(stage));
  }

  private void setSide(final Game game) {
    side.setValue(game.getHumanSide());
  }

  private void setPlayer(final Game game) {
    var humanPlayer = game.getHuman();
    player.setValue(humanPlayer);
  }

  private void setFlag(final Game game) {
    var humanSide = game.getHumanSide();
    var scenario = game.getScenario().getName();
    var propertyName = humanSide.toLower() + ".flag.medium.image";
    var imageName = props.getString(propertyName);
    var image = resourceProvider.getImage(scenario, imageName);
    flag.setValue(image);
  }

  private void setTaskForceImage(final Game game) {
    var humanSide = game.getHumanSide();
    var scenario = game.getScenario().getName();
    var propertyName = humanSide.toLower() + ".task.force.ships.image";
    var imageName = props.getString(propertyName);
    var image = resourceProvider.getImage(scenario, imageName);
    taskForceImage.setValue(image);
  }

  private void setSubmarineFlotillaImage(final Game game) {
    var humanSide = game.getHumanSide();
    var scenario = game.getScenario().getName();
    var propertyName = humanSide.toLower() + ".submarine.flotilla.image";
    var imageName = props.getString(propertyName);
    var image = resourceProvider.getImage(scenario, imageName);
    submarineFlotillaImage.setValue(image);
  }

  private void setMtbFlotillaImage(final Game game) {
    var humanSide = game.getHumanSide();
    var scenario = game.getScenario().getName();
    var propertyName = humanSide.toLower() + ".mtb.flotilla.image";
    var imageName = props.getString(propertyName);
    var image = resourceProvider.getImage(scenario, imageName);
    mtbFlotillaImage.setValue(image);
  }

  private void setAirForceImage(final Game game) {
    var humanSide = game.getHumanSide();
    var scenario = game.getScenario().getName();
    var propertyName = humanSide.toLower() + ".air.force.squadrons.image";
    var imageName = props.getString(propertyName);
    var image = resourceProvider.getImage(scenario, imageName);
    airForceImage.setValue(image);
  }

  private void setTaskForces(final Game game) {
    var humanPlayer = game.getHuman();

    var playerTaskForces = humanPlayer.getTaskForces()
        .stream()
        .sorted()
        .toList();

    taskForces.setValue(FXCollections.observableList(playerTaskForces));
  }

  private void setSubmarineFlotillas(final Game game) {
    var humanPlayer = game.getHuman();

    var playerSubFlotillas = humanPlayer.getSubmarineFlotillas()
        .stream()
        .sorted()
        .toList();

    submarineFlotillas.setValue(FXCollections.observableList(playerSubFlotillas));
    submarineNotPresent.setValue(playerSubFlotillas.isEmpty());
  }

  private void setMtbFlotillas(final Game game) {
    var humanPlayer = game.getHuman();

    var playerMtbFlotillas = humanPlayer.getMtbFlotillas()
        .stream()
        .sorted()
        .toList();

    mtbFlotillas.setValue(FXCollections.observableList(playerMtbFlotillas));
    mtbNotPresent.setValue(playerMtbFlotillas.isEmpty());
  }

  private void setNations(final Game game) {
    var humanPlayer = game.getHuman();

    var playerNations = humanPlayer.getNations()
        .stream()
        .sorted()
        .toList();

    nations.setValue(FXCollections.observableList(playerNations));
  }

  private NavigateEvent buildForwardNav(final Stage stage) {
    return NavigateEvent
        .builder()
        .clazz(OrderOfBattleSummaryView.class)
        .stage(stage)
        .type(FORWARD)
        .build();
  }

  private NavigateEvent buildBackwardNav(final Stage stage) {
    return NavigateEvent.builder()
        .clazz(OrderOfBattleSummaryView.class)
        .type(BACKWARD)
        .stage(stage)
        .build();
  }
}
