package com.enigma.waratsea.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class ResourceProvider {
    private final ResourceNames resourceNames;

    @Inject
    public ResourceProvider(final ResourceNames resourceNames) {
        this.resourceNames  = resourceNames;
    }

    public InputStream getResourceInputStream(final String resourceName) {
        var fullPath = Paths.get(resourceNames.getGameName(), resourceName);
        return getClass()
                .getClassLoader()
                .getResourceAsStream(fullPath.toString());
    }

    public List<Path> getSubDirectoryPaths(final String parentDirectoryName) {
        var fullName = Paths.get(resourceNames.getGameName(), parentDirectoryName);

        try {
            return getSubDirectoryPathsFromJar(fullName.toString());
        } catch (URISyntaxException | IOException e) {
            log.error("Unable to get sub directory paths for directory: '{}'", fullName, e);
            return Collections.emptyList();
        }
    }

    private List<Path> getSubDirectoryPathsFromJar(final String directoryName) throws URISyntaxException, IOException {
        var jarUri = getJarPathUri();
        try (FileSystem fs = FileSystems.newFileSystem(jarUri, Collections.emptyMap())) {
            return Files.walk(fs.getPath(directoryName))
                    .filter(Files::isDirectory)
                    .filter(Files::isReadable)
                    .filter(path -> isPathSubDirectory(path, directoryName))
                    .collect(Collectors.toList());
        }
    }

    private URI getJarPathUri() throws URISyntaxException {
        var jarPath = getClass().getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();

        return URI.create("jar:file:" + jarPath);
    }

    private boolean isPathSubDirectory(final Path path, final String parentName) {
        return path.getParent().endsWith(parentName);
    }
}
