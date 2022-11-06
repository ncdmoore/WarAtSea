package com.enigma.waratsea;

import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.mapper.RegionMapper;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.orchestration.ConfigGameSaga;
import com.enigma.waratsea.property.*;
import com.enigma.waratsea.repository.*;
import com.enigma.waratsea.repository.impl.*;
import com.enigma.waratsea.service.*;
import com.enigma.waratsea.service.impl.*;
import com.enigma.waratsea.strategy.DefaultVisibilityStrategy;
import com.enigma.waratsea.strategy.DefaultWeatherStrategy;
import com.enigma.waratsea.strategy.VisibilityStrategy;
import com.enigma.waratsea.strategy.WeatherStrategy;
import com.enigma.waratsea.strategy.arcticConvoy.ArcticConvoyWeatherStrategy;
import com.enigma.waratsea.strategy.bombAlley.BombAlleyVisibilityStrategy;
import com.enigma.waratsea.strategy.bombAlley.BombAlleyWeatherStrategy;
import com.enigma.waratsea.view.View;
import com.enigma.waratsea.view.ViewFactory;
import com.enigma.waratsea.view.pregame.NewGameView;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.view.pregame.StartView;
import com.enigma.waratsea.viewmodel.ErrorHandler;
import com.enigma.waratsea.viewmodel.NavigationHandler;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import static com.enigma.waratsea.model.GameName.ARCTIC_CONVOY;
import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;

/**
 * Guice basic module.
 */
public class BasicModule extends AbstractModule {
  @Override
  protected void configure() {
    bindBootStrapped();

    bindProps();

    bindViews();

    bindRepositories();
    bindMappers();
    bindWeatherStrategies();
    bindVisibilityStrategies();
    bindServices();
  }

  private void bindBootStrapped() {
    Multibinder<BootStrapped> bootStrappedBinder = Multibinder.newSetBinder(binder(), BootStrapped.class);
    bootStrappedBinder.addBinding().to(NavigationHandler.class);
    bootStrappedBinder.addBinding().to(DataProvider.class);
    bootStrappedBinder.addBinding().to(ErrorHandler.class);
    bootStrappedBinder.addBinding().to(GameService.class);
    bootStrappedBinder.addBinding().to(ConfigGameSaga.class);
    bootStrappedBinder.addBinding().to(MapService.class);
    bootStrappedBinder.addBinding().to(AirfieldService.class);
    bootStrappedBinder.addBinding().to(PortService.class);
    bootStrappedBinder.addBinding().to(RegionService.class);
    bootStrappedBinder.addBinding().to(PlayerService.class);
  }

  private void bindProps() {
    bind(Props.class).annotatedWith(Names.named("View")).to(ViewProps.class);
    bind(Props.class).annotatedWith(Names.named("App")).to(AppProps.class);
  }

  private void bindViews() {
    install(new FactoryModuleBuilder()
        .implement(View.class, Names.named("Start"), StartView.class)
        .implement(View.class, Names.named("NewGame"), NewGameView.class)
        .implement(View.class, Names.named("SavedGame"), SavedGameView.class)
        .build(ViewFactory.class));
  }

  private void bindRepositories() {
    bind(ScenarioRepository.class).to(ScenarioRepositoryImpl.class);
    bind(MapRepository.class).to(MapRepositoryImpl.class);
    bind(RegionRepository.class).to(RegionRepositoryImpl.class);
    bind(AirfieldRepository.class).to(AirfieldRepositoryImpl.class);
    bind(PortRepository.class).to(PortRepositoryImpl.class);
    bind(GameRepository.class).to(GameRepositoryImpl.class);
  }

  private void bindMappers() {
    bind(RegionMapper.class).toInstance(RegionMapper.INSTANCE);
    bind(GameMapper.class).toInstance(GameMapper.INSTANCE);
  }

  private void bindWeatherStrategies() {
    bind(WeatherStrategy.class).annotatedWith(Names.named("Default")).to(DefaultWeatherStrategy.class);
    MapBinder<GameName, WeatherStrategy> mapBinder = MapBinder.newMapBinder(binder(), GameName.class, WeatherStrategy.class);
    mapBinder.addBinding(BOMB_ALLEY).to(BombAlleyWeatherStrategy.class);
    mapBinder.addBinding(ARCTIC_CONVOY).to(ArcticConvoyWeatherStrategy.class);
  }

  private void bindVisibilityStrategies() {
    bind(VisibilityStrategy.class).annotatedWith(Names.named("Default")).to(DefaultVisibilityStrategy.class);
    MapBinder<GameName, VisibilityStrategy> mapBinder = MapBinder.newMapBinder(binder(), GameName.class, VisibilityStrategy.class);
    mapBinder.addBinding(BOMB_ALLEY).to(BombAlleyVisibilityStrategy.class);
  }

  private void bindServices() {
    bind(DiceService.class).to(DiceServiceImpl.class);
    bind(ScenarioService.class).to(ScenarioServiceImpl.class);
    bind(MapService.class).to(MapServiceImpl.class);
    bind(RegionService.class).to(RegionServiceImpl.class);
    bind(WeatherService.class).to(WeatherServiceImpl.class);
    bind(GameService.class).to(GameServiceImpl.class);
    bind(PlayerService.class).to(PlayerServiceImpl.class);
    bind(AirfieldService.class).to(AirfieldServiceImpl.class);
    bind(PortService.class).to(PortServiceImpl.class);
  }
}
