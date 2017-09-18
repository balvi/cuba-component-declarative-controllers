package de.balvi.cuba.declarativecontrollers.web.editor;

import java.util.Map;

public interface EditorAnnotationDispatcher {

    String NAME = "dbcdc_EditorAnnotationExecutorService";

    void executeInit(AnnotatableAbstractEditor editor, Map<String, Object> params);

    void executePostInit(AnnotatableAbstractEditor editor);
}