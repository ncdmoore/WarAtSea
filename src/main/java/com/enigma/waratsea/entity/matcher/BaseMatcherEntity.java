package com.enigma.waratsea.entity.matcher;

import com.enigma.waratsea.model.Side;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BaseMatcherEntity {
  private Set<String> names;
  private Side side;
}
