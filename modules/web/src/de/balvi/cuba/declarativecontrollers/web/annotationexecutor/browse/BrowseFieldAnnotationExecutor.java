package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse;

import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Window;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface BrowseFieldAnnotationExecutor<A extends Annotation, T extends Component> extends AnnotationExecutor {

    void init(A annotation, Window.Lookup browse, T target, Map<String, Object> params);

    void ready(A annotation, Window.Lookup browse, T target, Map<String, Object> params);

}