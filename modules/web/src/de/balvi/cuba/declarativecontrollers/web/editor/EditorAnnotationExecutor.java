package de.balvi.cuba.declarativecontrollers.web.editor;

import com.haulmont.cuba.gui.components.Window;
import de.balvi.cuba.declarativecontrollers.web.AnnotationExecutor;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface EditorAnnotationExecutor<A extends Annotation> extends AnnotationExecutor {

    void init(A annotation, Window.Editor editor, Map<String, Object> params);

    void postInit(A annotation, Window.Editor editor);

}