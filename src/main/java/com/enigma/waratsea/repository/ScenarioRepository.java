package com.enigma.waratsea.repository;

import com.enigma.waratsea.entity.Scenario;
import com.enigma.waratsea.resource.ResourceNames;
import com.enigma.waratsea.resource.ResourceProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Singleton
@Slf4j
public class ScenarioRepository {
    private static final String SUMMARY_FILE_NAME = "summary.json";

    private final ResourceNames resourceNames;
    private final ResourceProvider resourceProvider;

    @Inject
    public ScenarioRepository(final ResourceNames resourceNames,
                              final ResourceProvider resourceProvider) {
        this.resourceNames = resourceNames;
        this.resourceProvider = resourceProvider;
    }

    public List<Scenario> getAll() {
        return Collections.emptyList();
    }

    private File[] getScenarioDirs()  {
        return resourceProvider.getResourceUrl(resourceNames.getScenarioDirectory())
                .map(url -> new File(url.getPath()).listFiles(File::isDirectory)).get();
    }

    private boolean isReadable(final File directory) {
        boolean isFileReadable = directory.canRead();
        log.debug("File: '{}' is readable is '{}'", directory, isFileReadable);
        return isFileReadable;
    }

    //Todo create guice factory for properties so we can easily inject view and app properties.
    // create a propsFactory
    // use assisted inject to create an app property and a view property

    /*private Optional<Scenario> readScenarioSummary(final File directory)  {
        Path path = Paths.get(directory.getPath(), SUMMARY_FILE_NAME);

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setDateFormat(props.getString("scenario.date.format")).create();
            Scenario scenario = gson.fromJson(br, Scenario.class);

            log.debug("load scenario: {}", scenario.getTitle());

            return Optional.of(scenario);
        } catch (Exception ex) {                                                                                        // Catch any Gson errors.
            log.error("Unable to load scenario: {}", directory.getName(), ex);
            return Optional.empty();                                                                                    // Null's should be removed from the scenario list.
        }
    }*/
}
