package com.enigma.waratsea;

import com.enigma.waratsea.mapper.AirfieldMapper;
import com.enigma.waratsea.mapper.AllotmentMapper;
import com.enigma.waratsea.mapper.AllotmentModificationMapper;
import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.mapper.ManifestMapper;
import com.enigma.waratsea.mapper.MissionMapper;
import com.enigma.waratsea.mapper.MtbFlotillaMapper;
import com.enigma.waratsea.mapper.RegionMapper;
import com.enigma.waratsea.mapper.ShipMapper;
import com.enigma.waratsea.mapper.ShipRegistryMapper;
import com.enigma.waratsea.mapper.SquadronMapper;
import com.enigma.waratsea.mapper.SubmarineFlotillaMapper;
import com.enigma.waratsea.mapper.TaskForceMapper;
import com.enigma.waratsea.mapper.VictoryMapper;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.orchestration.ConfigApplicationSaga;
import com.enigma.waratsea.orchestration.ConfigNewGameSaga;
import com.enigma.waratsea.orchestration.ConfigSavedGameSaga;
import com.enigma.waratsea.orchestration.ConfigScenarioOptionsSaga;
import com.enigma.waratsea.property.AppProps;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.property.ViewProps;
import com.enigma.waratsea.repository.AircraftRepository;
import com.enigma.waratsea.repository.AirfieldRepository;
import com.enigma.waratsea.repository.GameRepository;
import com.enigma.waratsea.repository.ManifestRepository;
import com.enigma.waratsea.repository.MapRepository;
import com.enigma.waratsea.repository.MissionRepository;
import com.enigma.waratsea.repository.MtbFlotillaRepository;
import com.enigma.waratsea.repository.PortRepository;
import com.enigma.waratsea.repository.RegionRepository;
import com.enigma.waratsea.repository.ScenarioRepository;
import com.enigma.waratsea.repository.ShipRepository;
import com.enigma.waratsea.repository.SquadronAllotmentModRepository;
import com.enigma.waratsea.repository.SquadronAllotmentRepository;
import com.enigma.waratsea.repository.SquadronDeploymentRepository;
import com.enigma.waratsea.repository.SquadronRepository;
import com.enigma.waratsea.repository.SubmarineFlotillaRepository;
import com.enigma.waratsea.repository.TaskForceRepository;
import com.enigma.waratsea.repository.VictoryRepository;
import com.enigma.waratsea.repository.impl.AircraftRepositoryImpl;
import com.enigma.waratsea.repository.impl.AirfieldRepositoryImpl;
import com.enigma.waratsea.repository.impl.DataProvider;
import com.enigma.waratsea.repository.impl.GameRepositoryImpl;
import com.enigma.waratsea.repository.impl.ManifestRepositoryImpl;
import com.enigma.waratsea.repository.impl.MapRepositoryImpl;
import com.enigma.waratsea.repository.impl.MissionRepositoryImpl;
import com.enigma.waratsea.repository.impl.MtbFlotillaRepositoryImpl;
import com.enigma.waratsea.repository.impl.PortRepositoryImpl;
import com.enigma.waratsea.repository.impl.RegionRepositoryImpl;
import com.enigma.waratsea.repository.impl.ResourceProvider;
import com.enigma.waratsea.repository.impl.ScenarioRepositoryImpl;
import com.enigma.waratsea.repository.impl.ShipRepositoryImpl;
import com.enigma.waratsea.repository.impl.SquadronAllotmentModRepositoryImpl;
import com.enigma.waratsea.repository.impl.SquadronAllotmentRepositoryImpl;
import com.enigma.waratsea.repository.impl.SquadronDeploymentRepositoryImpl;
import com.enigma.waratsea.repository.impl.SquadronRepositoryImpl;
import com.enigma.waratsea.repository.impl.SubmarineFlotillaRepositoryImpl;
import com.enigma.waratsea.repository.impl.TaskForceRepositoryImpl;
import com.enigma.waratsea.repository.impl.VictoryRepositoryImpl;
import com.enigma.waratsea.service.AirbaseService;
import com.enigma.waratsea.service.AircraftService;
import com.enigma.waratsea.service.AirfieldService;
import com.enigma.waratsea.service.DiceService;
import com.enigma.waratsea.service.GameService;
import com.enigma.waratsea.service.ManifestService;
import com.enigma.waratsea.service.MapService;
import com.enigma.waratsea.service.MissionService;
import com.enigma.waratsea.service.MtbFlotillaService;
import com.enigma.waratsea.service.PlayerService;
import com.enigma.waratsea.service.PortService;
import com.enigma.waratsea.service.RegionService;
import com.enigma.waratsea.service.ScenarioService;
import com.enigma.waratsea.service.ShipService;
import com.enigma.waratsea.service.SquadronAllotmentModService;
import com.enigma.waratsea.service.SquadronAllotmentService;
import com.enigma.waratsea.service.SquadronDeploymentService;
import com.enigma.waratsea.service.SquadronService;
import com.enigma.waratsea.service.SubmarineFlotillaService;
import com.enigma.waratsea.service.TaskForceService;
import com.enigma.waratsea.service.VictoryService;
import com.enigma.waratsea.service.WeatherService;
import com.enigma.waratsea.service.impl.AirbaseServiceImpl;
import com.enigma.waratsea.service.impl.AircraftServiceImpl;
import com.enigma.waratsea.service.impl.AirfieldServiceImpl;
import com.enigma.waratsea.service.impl.DiceServiceImpl;
import com.enigma.waratsea.service.impl.GameServiceImpl;
import com.enigma.waratsea.service.impl.ManifestServiceImpl;
import com.enigma.waratsea.service.impl.MapServiceImpl;
import com.enigma.waratsea.service.impl.MissionServiceImpl;
import com.enigma.waratsea.service.impl.MtbFlotillaServiceImpl;
import com.enigma.waratsea.service.impl.PlayerServiceImpl;
import com.enigma.waratsea.service.impl.PortServiceImpl;
import com.enigma.waratsea.service.impl.RegionServiceImpl;
import com.enigma.waratsea.service.impl.ScenarioServiceImpl;
import com.enigma.waratsea.service.impl.ShipServiceImpl;
import com.enigma.waratsea.service.impl.SquadronAllotmentModServiceImpl;
import com.enigma.waratsea.service.impl.SquadronAllotmentServiceImpl;
import com.enigma.waratsea.service.impl.SquadronDeploymentServiceImpl;
import com.enigma.waratsea.service.impl.SquadronServiceImpl;
import com.enigma.waratsea.service.impl.SubmarineFlotillaServiceImpl;
import com.enigma.waratsea.service.impl.TaskForceServiceImpl;
import com.enigma.waratsea.service.impl.VictoryServiceImpl;
import com.enigma.waratsea.service.impl.WeatherServiceImpl;
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
import com.enigma.waratsea.view.pregame.OrderOfBattleSummaryView;
import com.enigma.waratsea.view.pregame.SavedGameView;
import com.enigma.waratsea.view.pregame.ScenarioSquadronOptionsView;
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
    bootStrappedBinder.addBinding().to(ConfigApplicationSaga.class);
    bootStrappedBinder.addBinding().to(ConfigNewGameSaga.class);
    bootStrappedBinder.addBinding().to(ConfigSavedGameSaga.class);
    bootStrappedBinder.addBinding().to(ConfigScenarioOptionsSaga.class);
    bootStrappedBinder.addBinding().to(NavigationHandler.class);
    bootStrappedBinder.addBinding().to(ResourceProvider.class);
    bootStrappedBinder.addBinding().to(DataProvider.class);
    bootStrappedBinder.addBinding().to(ErrorHandler.class);
    bootStrappedBinder.addBinding().to(GameService.class);
    bootStrappedBinder.addBinding().to(MapService.class);
    bootStrappedBinder.addBinding().to(AirfieldService.class);
    bootStrappedBinder.addBinding().to(PortService.class);
    bootStrappedBinder.addBinding().to(RegionService.class);
    bootStrappedBinder.addBinding().to(PlayerService.class);
    bootStrappedBinder.addBinding().to(AircraftService.class);
    bootStrappedBinder.addBinding().to(SquadronService.class);
    bootStrappedBinder.addBinding().to(SquadronAllotmentService.class);
    bootStrappedBinder.addBinding().to(SquadronAllotmentModService.class);
    bootStrappedBinder.addBinding().to(SquadronDeploymentService.class);
    bootStrappedBinder.addBinding().to(ShipService.class);
    bootStrappedBinder.addBinding().to(ManifestService.class);
    bootStrappedBinder.addBinding().to(TaskForceService.class);
    bootStrappedBinder.addBinding().to(SubmarineFlotillaService.class);
    bootStrappedBinder.addBinding().to(MtbFlotillaService.class);
    bootStrappedBinder.addBinding().to(MissionService.class);
    bootStrappedBinder.addBinding().to(VictoryService.class);
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
        .implement(View.class, Names.named("ScenarioSquadronOptions"), ScenarioSquadronOptionsView.class)
        .implement(View.class, Names.named("OrderOfBattleSummary"), OrderOfBattleSummaryView.class)
        .build(ViewFactory.class));
  }

  private void bindRepositories() {
    bind(ScenarioRepository.class).to(ScenarioRepositoryImpl.class);
    bind(MapRepository.class).to(MapRepositoryImpl.class);
    bind(RegionRepository.class).to(RegionRepositoryImpl.class);
    bind(AirfieldRepository.class).to(AirfieldRepositoryImpl.class);
    bind(PortRepository.class).to(PortRepositoryImpl.class);
    bind(AircraftRepository.class).to(AircraftRepositoryImpl.class);
    bind(SquadronRepository.class).to(SquadronRepositoryImpl.class);
    bind(SquadronDeploymentRepository.class).to(SquadronDeploymentRepositoryImpl.class);
    bind(SquadronAllotmentRepository.class).to(SquadronAllotmentRepositoryImpl.class);
    bind(SquadronAllotmentModRepository.class).to(SquadronAllotmentModRepositoryImpl.class);
    bind(ShipRepository.class).to(ShipRepositoryImpl.class);
    bind(ManifestRepository.class).to(ManifestRepositoryImpl.class);
    bind(TaskForceRepository.class).to(TaskForceRepositoryImpl.class);
    bind(SubmarineFlotillaRepository.class).to(SubmarineFlotillaRepositoryImpl.class);
    bind(MtbFlotillaRepository.class).to(MtbFlotillaRepositoryImpl.class);
    bind(MissionRepository.class).to(MissionRepositoryImpl.class);
    bind(GameRepository.class).to(GameRepositoryImpl.class);
    bind(VictoryRepository.class).to(VictoryRepositoryImpl.class);
  }

  private void bindMappers() {
    bind(RegionMapper.class).toInstance(RegionMapper.INSTANCE);
    bind(GameMapper.class).toInstance(GameMapper.INSTANCE);
    bind(AirfieldMapper.class).toInstance(AirfieldMapper.INSTANCE);
    bind(AllotmentMapper.class).toInstance(AllotmentMapper.INSTANCE);
    bind(AllotmentModificationMapper.class).toInstance(AllotmentModificationMapper.INSTANCE);
    bind(SquadronMapper.class).toInstance(SquadronMapper.INSTANCE);
    bind(ShipRegistryMapper.class).toInstance(ShipRegistryMapper.INSTANCE);
    bind(ShipMapper.class).toInstance(ShipMapper.INSTANCE);
    bind(ManifestMapper.class).toInstance(ManifestMapper.INSTANCE);
    bind(TaskForceMapper.class).toInstance(TaskForceMapper.INSTANCE);
    bind(SubmarineFlotillaMapper.class).toInstance(SubmarineFlotillaMapper.INSTANCE);
    bind(MtbFlotillaMapper.class).toInstance(MtbFlotillaMapper.INSTANCE);
    bind(MissionMapper.class).toInstance(MissionMapper.INSTANCE);
    bind(VictoryMapper.class).toInstance(VictoryMapper.INSTANCE);
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
    bind(PlayerService.class).to(PlayerServiceImpl.class);
    bind(AirfieldService.class).to(AirfieldServiceImpl.class);
    bind(AirbaseService.class).to(AirbaseServiceImpl.class);
    bind(PortService.class).to(PortServiceImpl.class);
    bind(AircraftService.class).to(AircraftServiceImpl.class);
    bind(SquadronService.class).to(SquadronServiceImpl.class);
    bind(SquadronAllotmentService.class).to(SquadronAllotmentServiceImpl.class);
    bind(SquadronAllotmentModService.class).to(SquadronAllotmentModServiceImpl.class);
    bind(SquadronDeploymentService.class).to(SquadronDeploymentServiceImpl.class);
    bind(ShipService.class).to(ShipServiceImpl.class);
    bind(ManifestService.class).to(ManifestServiceImpl.class);
    bind(TaskForceService.class).to(TaskForceServiceImpl.class);
    bind(SubmarineFlotillaService.class).to(SubmarineFlotillaServiceImpl.class);
    bind(MtbFlotillaService.class).to(MtbFlotillaServiceImpl.class);
    bind(MissionService.class).to(MissionServiceImpl.class);
    bind(GameService.class).to(GameServiceImpl.class);
    bind(VictoryService.class).to(VictoryServiceImpl.class);
  }
}
