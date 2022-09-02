package com.enigma.waratsea;

import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.property.*;
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
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;

import static com.enigma.waratsea.model.GameName.ARCTIC_CONVOY;
import static com.enigma.waratsea.model.GameName.BOMB_ALLEY;

/**
 * Guice basic module.
 */
public class BasicModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(GameMapper.class).toInstance(GameMapper.INSTANCE);

    bindProps();
    bindViews();
    bindWeatherStrategies();
    bindVisibilityStrategies();
  }

  private void bindProps() {
    install(new FactoryModuleBuilder().implement(PropsWrapper.class, PropsWrapperImpl.class).build(PropsWrapperFactory.class));

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
}
