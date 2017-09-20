package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse;

import com.haulmont.cuba.gui.components.Window;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface BrowseAnnotationExecutor<A extends Annotation> extends AnnotationExecutor {

    void init(A annotation, Window.Lookup browse, Map<String, Object> params);

    void ready(A annotation, Window.Lookup browse, Map<String, Object> params);
}