package com.enigma.waratsea.dto;

import java.util.Set;

public record CargoDto(String originPort, Set<String> destinationPorts) {
}
