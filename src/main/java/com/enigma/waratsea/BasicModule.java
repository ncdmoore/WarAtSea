package com.enigma.waratsea;

import com.enigma.waratsea.mapper.GameMapper;
import com.enigma.waratsea.model.GameName;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.property.PropsFactory;
import com.enigma.waratsea.property.PropsImpl;
import com.enigma.waratsea.strategy.DefaultVisibilityStrategy;
import com.enigma.waratsea.strategy.DefaultWeatherStrategy;
import com.enigma.waratsea.strategy.VisibilityStrategy;
import com.enigma.waratsea.strategy.WeatherStrategy;
import com.enigma.waratsea.strategy.arcticConvoy.ArcticConvoyWeatherStrategy;
import com.enigma.waratsea.strategy.bombAlley.BombAlleyVisibilityStrategy;
import com.enigma.waratsea.strategy.bombAlley.BombAlleyWeatherStrategy;
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
    install(new FactoryModuleBuilder().implement(Props.class, PropsImpl.class).build(PropsFactory.class));

    bind(GameMapper.class).toInstance(GameMapper.INSTANCE);

    bindWeatherStrategies();
    bindVisibilityStrategies();
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
