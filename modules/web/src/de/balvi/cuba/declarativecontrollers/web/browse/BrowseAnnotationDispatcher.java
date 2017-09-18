package de.balvi.cuba.declarativecontrollers.web.browse;

import java.util.Map;

public interface BrowseAnnotationDispatcher {

    String NAME = "dbcdc_BrowseAnnotationExecutorService";

    void executeInit(AnnotatableAbstractLookup browse, Map<String, Object> params);
    void executeReady(AnnotatableAbstractLookup browse, Map<String, Object> params);
}