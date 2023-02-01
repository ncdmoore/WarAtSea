package com.enigma.waratsea.mapper;

import com.enigma.waratsea.entity.squadron.AircraftAllotmentEntity;
import com.enigma.waratsea.entity.squadron.AllotmentEntity;
import com.enigma.waratsea.entity.squadron.GroupAllotmentEntity;
import com.enigma.waratsea.entity.squadron.SquadronTypeAllotmentEntity;
import com.enigma.waratsea.model.Id;
import com.enigma.waratsea.model.squadron.Allotment;
import com.enigma.waratsea.model.squadron.GroupAllotment;
import com.enigma.waratsea.model.squadron.SquadronTypeAllotment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "jsr330")
public abstract class AllotmentMapper {
  public static final AllotmentMapper INSTANCE = Mappers.getMapper(AllotmentMapper.class);

  abstract public Allotment toModel(AllotmentEntity allotmentEntity);

  SquadronTypeAllotment mapTypeAllotment(SquadronTypeAllotmentEntity squadronTypeAllotmentEntity) {
    return SquadronTypeAllotment.builder()
        .dice(squadronTypeAllotmentEntity.getDice())
        .factor(squadronTypeAllotmentEntity.getFactor())
        .groups(mapGroupAllotments(squadronTypeAllotmentEntity.getGroups()))
        .build();
  }

  List<GroupAllotment> mapGroupAllotments(List<GroupAllotmentEntity> groups) {
    return groups.stream()
        .map(this::mapGroupAllotment)
        .sorted()
        .toList();
  }

  GroupAllotment mapGroupAllotment(GroupAllotmentEntity groupAllotmentEntity) {
    return GroupAllotment.builder()
        .priority(groupAllotmentEntity.getPriority())
        .selectSize(groupAllotmentEntity.getSelectSize())
        .aircraft(mapIds(groupAllotmentEntity.getAircraft()))
        .build();
  }

  List<Id> mapIds(List<AircraftAllotmentEntity> entities) {
    var ids =  entities.stream()
        .flatMap(this::expand)
        .toList();

    return new ArrayList<>(ids);
  }

  Stream<Id> expand(AircraftAllotmentEntity aircraftAllotment) {
    var number = aircraftAllotment.getNumber();
    var id = aircraftAllotment.getId();

    List<Id> aircraft = new ArrayList<>();
    for (int count = 0; count < number; count++) {
      var aircraftId = new Id(id.getSide(), id.getName());
      aircraft.add(aircraftId);
    }

    return aircraft.stream();
  }
}
