package com.enigma.waratsea.repository.provider;

import com.google.inject.Singleton;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Singleton
public class Resource {
  public InputStream getInputStream(final String fullPath) {
    return getClass()
        .getClassLoader()
        .getResourceAsStream(fullPath);
  }

  public  URI getUri() throws URISyntaxException {
    var jarPath = getClass().getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI()
        .getPath();

    return URI.create("jar:file:" + jarPath);
  }
}
