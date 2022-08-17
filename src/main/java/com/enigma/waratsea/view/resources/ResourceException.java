package com.enigma.waratsea.view.resources;

import lombok.extern.slf4j.Slf4j;

/**
 * Indicates that a resource could not be found.
 */
@Slf4j
public class ResourceException extends RuntimeException {
    ResourceException(final String name) {
        super(name);
        log.error("Unable to load resource: '{}'", name);
    }
}
