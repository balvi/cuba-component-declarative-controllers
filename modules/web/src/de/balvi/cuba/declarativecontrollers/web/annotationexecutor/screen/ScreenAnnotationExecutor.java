package de.balvi.cuba.declarativecontrollers.web.annotationexecutor.screen;

import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AnnotationExecutor;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface ScreenAnnotationExecutor<A extends Annotation> extends AnnotationExecutor {

    void init(A annotation, Screen screen, ScreenOptions options);

    void beforeShow(A annotation, Screen screen, ScreenOptions options);

}
