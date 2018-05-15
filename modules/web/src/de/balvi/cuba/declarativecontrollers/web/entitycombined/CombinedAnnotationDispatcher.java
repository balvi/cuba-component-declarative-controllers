package de.balvi.cuba.declarativecontrollers.web.entitycombined;

import java.util.Map;

public interface CombinedAnnotationDispatcher {

    String NAME = "dbcdc_CombinedAnnotationExecutorService";

    void executeInit(AnnotatableAbstractCombined browse, Map<String, Object> params);

    void executeReady(AnnotatableAbstractCombined browse, Map<String, Object> params);
    
}