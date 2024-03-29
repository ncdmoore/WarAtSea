package com.enigma.waratsea.repository.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateSerializer implements JsonSerializer<LocalDate> {
  private final String dateFormat;

  public LocalDateSerializer(final String dateFormat) {
    this.dateFormat = dateFormat;
  }

  @Override
  public JsonElement serialize(final LocalDate date, final Type typeOfSrc, final JsonSerializationContext context) {
    return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern(dateFormat).withLocale(Locale.ENGLISH)));
  }
}
