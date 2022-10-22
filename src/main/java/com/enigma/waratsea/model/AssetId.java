package com.enigma.waratsea.model;

import lombok.Getter;

import static com.enigma.waratsea.Constants.ID_SEPARATOR;

@Getter
public class AssetId {
  private final Side side;
  private final String name;

  public AssetId(final String id) {
    var parsedId = id.split(ID_SEPARATOR);
    side = Side.valueOf(parsedId[0]);
    name = parsedId[1];
  }
}
