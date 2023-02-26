package com.enigma.waratsea.model.squadron;

import com.enigma.waratsea.model.aircraft.Aircraft;
import com.enigma.waratsea.model.aircraft.AttackRating;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.enigma.waratsea.model.squadron.SquadronStrength.FULL;

@RequiredArgsConstructor
public enum SquadronConfiguration {
  NONE("None") {
    public AttackRating getAirAttackRating(final Aircraft aircraft) {
      return aircraft.getAir();
    }

    public AttackRating getLandAttackRating(final Aircraft aircraft) {
      return aircraft.getLand();
    }

    public AttackRating getNavalTransportAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalTransport();
    }

    public AttackRating getNavalWarshipAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalWarship();
    }

    public int getRange(final Aircraft aircraft) {
      return aircraft.getPerformance().getRange();
    }

    public int getEndurance(final Aircraft aircraft) {
      return aircraft.getPerformance().getEndurance();
    }

    public int getFerryDistance(final Aircraft aircraft) {
      return aircraft.getPerformance().getFerryDistance();
    }

    public int getRadius(final Aircraft aircraft) {
      return aircraft.getPerformance().getRadius();
    }
  },

  DROP_TANKS("Drop Tanks") {
    public AttackRating getAirAttackRating(final Aircraft aircraft) {
      return aircraft.getAir();
    }

    public AttackRating getLandAttackRating(final Aircraft aircraft) {
      return aircraft.getLand();
    }

    public AttackRating getNavalTransportAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalTransport();
    }

    public AttackRating getNavalWarshipAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalWarship();
    }

    public int getRange(final Aircraft aircraft) {
      return (int) Math.ceil(aircraft.getPerformance().getRange() * DROP_TANK_FACTOR);
    }
    
    public int getEndurance(final Aircraft aircraft) {
      return aircraft.getPerformance().getEndurance();
    }

    public int getFerryDistance(final Aircraft aircraft) {
      var enhancedRange =  getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return enhancedRange * endurance;
    }

    public int getRadius(final Aircraft aircraft) {
      var enhancedRange =  getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return getEnhancedRadius(enhancedRange, endurance);
    }
  },

  LEAN_ENGINE("Lean engine") {
    public AttackRating getAirAttackRating(final Aircraft aircraft) {
      return aircraft.getAir().reduceRoundUp(LEAN_ENGINE_FACTOR);
    }

    public AttackRating getLandAttackRating(final Aircraft aircraft) {
      return aircraft.getLand().reduceRoundUp(LEAN_ENGINE_FACTOR);
    }

    public AttackRating getNavalTransportAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalTransport().reduceRoundUp(LEAN_ENGINE_FACTOR);
    }

    public AttackRating getNavalWarshipAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalWarship().reduceRoundUp(LEAN_ENGINE_FACTOR);
    }

    public int getRange(final Aircraft aircraft) {
      return aircraft.getPerformance().getRange();
    }
    
    public int getEndurance(final Aircraft aircraft) {
      return aircraft.getPerformance().getEndurance() * LEAN_ENGINE_FACTOR;
    }

    public int getFerryDistance(final Aircraft aircraft) {
      var range = getRange(aircraft);
      var enhancedEndurance = getEndurance(aircraft);
      return range * enhancedEndurance;
    }

    public int getRadius(final Aircraft aircraft) {
      var range = getRange(aircraft);
      var enhancedEndurance = getEndurance(aircraft);
      return getEnhancedRadius(range, enhancedEndurance);
    }
  },
  
  REDUCED_PAYLOAD("Reduced payload") {
    public AttackRating getAirAttackRating(final Aircraft aircraft) {
      return aircraft.getAir();
    }

    public AttackRating getLandAttackRating(final Aircraft aircraft) {
      return aircraft.getLand().reduceRoundDown(REDUCED_PAYLOAD_FACTOR);
    }

    public AttackRating getNavalTransportAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalTransport().reduceRoundDown(REDUCED_PAYLOAD_FACTOR);
    }

    public AttackRating getNavalWarshipAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalWarship().reduceRoundDown(REDUCED_PAYLOAD_FACTOR);
    }

    public int getRange(final Aircraft aircraft) {
      var modifier = getReducedPayloadModifier(aircraft);
      return aircraft.getPerformance().getRange() + modifier;
    }
    
    public int getEndurance(final Aircraft aircraft) {
      return aircraft.getPerformance().getEndurance();
    }

    public int getFerryDistance(final Aircraft aircraft) {
      var enhancedRange = getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return enhancedRange * endurance;
    }

    public int getRadius(final Aircraft aircraft) {
      var enhancedRange =  getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return getEnhancedRadius(enhancedRange, endurance);
    }
  },
  
  SEARCH("Search") {
    public AttackRating getAirAttackRating(final Aircraft aircraft) {
      return aircraft.getAir();
    }

    public AttackRating getLandAttackRating(final Aircraft aircraft) {
      return aircraft.getLand().reduceRoundDown(SEARCH_FACTOR);
    }

    public AttackRating getNavalTransportAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalTransport().reduceRoundDown(SEARCH_FACTOR);
    }

    public AttackRating getNavalWarshipAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalWarship().reduceRoundDown(SEARCH_FACTOR);
    }

    public int getRange(final Aircraft aircraft) {
      var modifier = getSearchModifier(aircraft);
      return aircraft.getPerformance().getRange() + modifier;
    }
    
    public int getEndurance(final Aircraft aircraft) {
      return aircraft.getPerformance().getEndurance();
    }

    public int getFerryDistance(final Aircraft aircraft) {
      var enhancedRange = getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return enhancedRange * endurance;        
    }

    public int getRadius(final Aircraft aircraft) {
      var enhancedRange = getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return getEnhancedRadius(enhancedRange, endurance);
    }
  },
  
  STRIPPED_DOWN("Stripped down") {
    public AttackRating getAirAttackRating(final Aircraft aircraft) {
      return aircraft.getAir().zeroModifier();
    }

    public AttackRating getLandAttackRating(final Aircraft aircraft) {
      return aircraft.getLand().zeroAll();
    }

    public AttackRating getNavalTransportAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalTransport().zeroAll();
    }

    public AttackRating getNavalWarshipAttackRating(final Aircraft aircraft) {
      return aircraft.getNavalWarship().zeroAll();
    }

    public int getRange(final Aircraft aircraft) {
      return aircraft.getPerformance().getRange() * STRIPPED_DOWN_FACTOR;
    }
    
    public int getEndurance(final Aircraft aircraft) {
      return aircraft.getPerformance().getEndurance();
    }

    public int getFerryDistance(final Aircraft aircraft) {
      var enhancedRange = getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return enhancedRange * endurance;        
    }

    public int getRadius(final Aircraft aircraft) {
      var enhancedRange = getRange(aircraft);
      var endurance = getEndurance(aircraft);
      return getEnhancedRadius(enhancedRange, endurance);
    }
  };

  public abstract AttackRating getAirAttackRating(Aircraft aircraft);
  public abstract AttackRating getLandAttackRating(Aircraft aircraft);
  public abstract AttackRating getNavalTransportAttackRating(Aircraft aircraft);
  public abstract AttackRating getNavalWarshipAttackRating(Aircraft aircraft);

  public abstract int getRange(Aircraft aircraft);
  public abstract int getEndurance(Aircraft aircraft);
  public abstract int getFerryDistance(Aircraft aircraft);
  public abstract int getRadius(Aircraft aircraft);

  private static final double DROP_TANK_FACTOR = 1.5; 
  private static final int LEAN_ENGINE_FACTOR = 2;
  private static final int REDUCED_PAYLOAD_FACTOR = 2;
  private static final int SEARCH_FACTOR = 2;
  private static final int SEARCH_MODIFIER = 2;
  private static final int ORDINANCE_PAYLOAD_THRESHOLD = 2;
  private static final int STRIPPED_DOWN_FACTOR = 3;

  @Getter
  private final String value;

  private static int getSearchModifier(final Aircraft aircraft) {
    return SEARCH_MODIFIER + (hasExtraFuelCapacity(aircraft) ? SEARCH_MODIFIER : 0);
  }
  
  private static int getReducedPayloadModifier(final Aircraft aircraft) {
    return hasExtraFuelCapacity(aircraft) ? SEARCH_MODIFIER : 0;
  }
  
  private static boolean hasExtraFuelCapacity(final Aircraft aircraft) {
    var landAttackRating = aircraft.getLand();
    var navalAttackRating = aircraft.getNavalWarship();

    return landAttackRating.getAttack(FULL).getFactor() >= ORDINANCE_PAYLOAD_THRESHOLD
        || navalAttackRating.getAttack(FULL).getFactor() >= ORDINANCE_PAYLOAD_THRESHOLD;
  }

  private static int getEnhancedRadius(final int enhancedRange, final int enhancedEndurance) {
    return (enhancedRange * enhancedEndurance) / 2 + ((enhancedRange % 2) * enhancedEndurance) / 2;  // Two-way distance. Return.
  }
}
