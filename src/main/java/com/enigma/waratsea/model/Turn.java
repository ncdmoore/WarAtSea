package com.enigma.waratsea.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import static com.enigma.waratsea.model.TimeRange.NIGHT_2;

/**
 * Game turn.
 */
@Data
@Builder
public class Turn {
  private TimeRange timeRange;
  private int number;
  private LocalDate date;

  public Turn next() {
    var newDate = isNewDay() ? date.plusDays(1) : date;

    return Turn.builder()
        .timeRange(timeRange.next())
        .number(number + 1)
        .date(newDate)
        .build();
  }

  private boolean isNewDay() {
    return timeRange == NIGHT_2;
  }
}
