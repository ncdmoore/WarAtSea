package com.enigma.waratsea.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Nation {
  AUSTRALIAN("Australian", "HMAS"),
  BRITISH("British", "HMS"),
  FINNISH("Finnish", "FN"),
  FRENCH("French", ""),
  GERMAN("German", "KMS"),
  GREEK("Greek", "HS"),
  ITALIAN("Italian", "RN"),
  JAPANESE("Japanese", "IJN"),
  POLISH("Polish", "ORP"),
  UNITED_STATES("United States", "USS"),
  USSR("USSR", "CCCP");

  private final String value;
  private final String shipPrefix;

  @Override
  public String toString() {
    return value;
  }

  public String toLower() {
    return value.toLowerCase().replaceAll("\\s+", "_");
  }
}
