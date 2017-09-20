package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor;

import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Window;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface EditorFieldAnnotationExecutor<A extends Annotation, T extends Component> extends AnnotationExecutor {

    void init(A annotation, Window.Editor editor, T target, Map<String, Object> params);

    void postInit(A annotation, Window.Editor editor, T target);

}