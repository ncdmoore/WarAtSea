package com.enigma.waratsea.model;

import com.google.gson.annotations.SerializedName;

public enum SquadronDeploymentType {
  @SerializedName(value = "COMPUTER", alternate = {"Computer", "computer"})
  COMPUTER,

  @SerializedName(value = "HUMAN", alternate = {"Human", "human"})
  HUMAN
}
