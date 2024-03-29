package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.dto.AllotmentModificationDto;
import com.enigma.waratsea.model.die.Die;
import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Getter
@Builder
public class SquadronTypeAllotment {
  private int dice;
  private int factor;
  private List<GroupAllotment> groups;

  public List<Id> get(final Die die) {
    var numberToSelect = calculateNumberToSelect(die);

    log.info("Total number of squadrons to select: '{}', based on dice: '{}', factor: '{}'",
        numberToSelect, dice, factor);

    var totalInGroups = getGroupsTotal();

    if (numberToSelect > totalInGroups) {
      log.warn("Number to select is greater than total number of aircraft in all groups, "
          + "setting number to select to total in all groups.");
      numberToSelect = totalInGroups;
    }

    return selectAircraft(numberToSelect);
  }

  public void adjust(final AllotmentModificationDto dto) {
    dice += dto.getDice();
    factor += dto.getFactor();

    log.info("Adjust squadron type: {}, dice: {}, factor: {}", dto.getType(), dto.getDice(), dto.getFactor());
  }

  private int calculateNumberToSelect(final Die die) {
    var diceRoll = IntStream.range(0, dice)
        .map(i -> die.roll())
        .reduce(0, Integer::sum);

    var totalSteps = roundUpToEven(diceRoll + factor);

    return totalSteps / 2;
  }

  private List<Id> selectAircraft(final int numberToSelect) {
    log.info("Select '{}' squadrons.", numberToSelect);

    List<Id> aircraft = new ArrayList<>();

    var numberNeeded = numberToSelect;
    int groupIndex = 0;
    while (numberNeeded > 0) {
      var selected = groups.get(groupIndex)
          .selectAircraft(numberNeeded);

      aircraft.addAll(selected);
      numberNeeded -= selected.size();
      groupIndex = nextGroup(groupIndex);
    }

    return aircraft;
  }

  private int nextGroup(final int currentGroupIndex) {
    var nextGroupIndex = currentGroupIndex + 1;
    return nextGroupIndex == groups.size() ? 0 : nextGroupIndex;
  }

  private int roundUpToEven(final int value) {
     return (value % 2 == 0) ? value : value + 1;
  }

  private int getGroupsTotal() {
    return groups.stream()
        .map(groupAllotment -> groupAllotment.getAircraft().size())
        .reduce(0, Integer::sum);
  }
}
