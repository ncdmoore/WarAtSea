package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.ship.FlightDeckEntity;
import com.enigma.waratsea.model.ship.FlightDeck;
import org.mapstruct.factory.Mappers;

public interface FlightDeckMapper {
  FlightDeckMapper INSTANCE = Mappers.getMapper(FlightDeckMapper.class);

  FlightDeck toModel(FlightDeckEntity flightDeckEntity);
}
