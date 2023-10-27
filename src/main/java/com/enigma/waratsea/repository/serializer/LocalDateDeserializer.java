package com.enigma.waratsea.repository.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
  private final String dateFormat;

  public LocalDateDeserializer(final String dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public LocalDate deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
      throws JsonParseException {
    return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.ENGLISH));
  }
}

