package com.enigma.waratsea.repository.provider;

import com.enigma.waratsea.model.Side;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Paths;

import static com.enigma.waratsea.Constants.JSON_EXTENSION;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class FilePath {
  private String baseDirectory;
  private Side side;
  private String fileName;

  public String getPath() {
    var sidePath = side.toLower();
    var fullFileName = fileName + JSON_EXTENSION;
    return Paths.get(baseDirectory, sidePath, fullFileName).toString();
  }
}
