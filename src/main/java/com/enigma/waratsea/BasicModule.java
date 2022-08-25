package com.enigma.waratsea;

import com.enigma.waratsea.mappers.GameMapper;
import com.enigma.waratsea.property.Props;
import com.enigma.waratsea.property.PropsFactory;
import com.enigma.waratsea.property.PropsImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Guice basic module.
 */
public class BasicModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().implement(Props.class, PropsImpl.class).build(PropsFactory.class));

    bind(GameMapper.class).toInstance(GameMapper.INSTANCE);
  }
}
