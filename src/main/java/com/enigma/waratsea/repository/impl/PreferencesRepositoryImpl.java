package com.enigma.waratsea.repository.impl;

import com.enigma.waratsea.entity.preferences.PreferencesEntity;
import com.enigma.waratsea.exception.GameException;
import com.enigma.waratsea.repository.PreferencesRepository;
import com.enigma.waratsea.repository.provider.PreferencesProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class PreferencesRepositoryImpl implements PreferencesRepository {
  private final PreferencesProvider preferencesProvider;

  @Override
  public PreferencesEntity get() {
    return read();
  }

  @Override
  public void save(final PreferencesEntity preferences) {
    write(preferences);
  }

  private PreferencesEntity read() {
    try (var in = getInputStream();
         var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
         var br = new BufferedReader(reader)) {
      log.debug("Read preferences");
      return toEntity(br);
    } catch (IOException e) {
      throw new GameException("Unable to read preferences", e);
    }
  }

  private void write(final PreferencesEntity preferences) {
    try (var out = getOutputStream();
         var writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
      log.debug("Save preferences");
      var json = toJson(preferences);
      writer.write(json);
    } catch (IOException e) {
      throw new GameException("Unable to save preferences", e);
    }
  }

  private InputStream getInputStream() {
    return preferencesProvider.getInputStream();
  }

  private OutputStream getOutputStream() throws FileNotFoundException {
    return preferencesProvider.getOutputStream();
  }

  private PreferencesEntity toEntity(final BufferedReader bufferedReader) {
    var gson = new Gson();

    var preferences = gson.fromJson(bufferedReader, PreferencesEntity.class);

    log.debug("load preferences");

    return preferences;
  }

  private String toJson(final PreferencesEntity preferences) {
    var gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    return gson.toJson(preferences);
  }
}
