package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.screen;

import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;

public interface ScreenFieldAnnotationExecutor<A extends Annotation, T extends Component> extends AnnotationExecutor {

    void init(A annotation, Screen screen, T target, ScreenOptions options);

    void beforeshow(A annotation, Screen screen, T target, ScreenOptions options);

}
