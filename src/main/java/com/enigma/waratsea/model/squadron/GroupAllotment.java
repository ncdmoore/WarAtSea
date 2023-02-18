package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.model.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
@Getter
@Builder
public class GroupAllotment implements Comparable<GroupAllotment> {
  private static Random random = new Random();

  private int priority;
  private int selectSize;
  private List<Id> aircraft;

  public List<Id> selectAircraft(final int numberNeeded) {
    var numberToSelect = Math.min(selectSize, aircraft.size());
    numberToSelect = Math.min(numberToSelect, numberNeeded);

    return IntStream.range(0, numberToSelect)
        .mapToObj(this::getAircraftId)
        .toList();
  }

  @Override
  public int compareTo(@NotNull final GroupAllotment o) {
    if (priority == o.priority) {
      return 0;
    } else if (priority < o.priority) {
      return -1;
    }
    return 1;
  }

  private Id getAircraftId(final int count) {
    var index = random.nextInt(aircraft.size());

    var aircraftId = aircraft.remove(index);

    log.info("Select aircraft: '{}'", aircraftId);

    return aircraftId;
  }
}
