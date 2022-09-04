package com.enigma.waratsea.repository.impl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
  private final String dateFormat;

  public LocalDateDeserializer(final String dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.ENGLISH));
  }
}

