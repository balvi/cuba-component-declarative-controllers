package de.balvi.cuba.declarativecontrollers.web.screen;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AbstractAnnotationDispatcherBean;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.screen.ScreenAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.screen.ScreenFieldAnnotationExecutor;
import groovy.transform.CompileStatic;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

@CompileStatic
@Component(ScreenAnnotationDispatcher.NAME)
public class ScreenAnnotationDispatcherBean extends AbstractAnnotationDispatcherBean<ScreenAnnotationExecutor, ScreenFieldAnnotationExecutor> implements ScreenAnnotationDispatcher {

    @Override
    public void executeInit(Screen screen, ScreenOptions screenOptions) {
        executeInitUniversal(screen, screenOptions);
    }

    private void executeInitUniversal(Screen screen, ScreenOptions screenOptions) {
        executeInitForClassAnnotations(screen, screenOptions);
        executeInitForFieldAnnotations(screen, screenOptions);
    }

    private void executeInitForFieldAnnotations(Screen screen, ScreenOptions screenOptions) {
        for (Field field : getDeclaredFields(screen)) {
            Annotation[] fieldAnnotations = field.getAnnotations();

            for (Annotation annotation : fieldAnnotations) {

                Collection<ScreenFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedScreenFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(screen, field);
                    for (ScreenFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.init(annotation, screen, fieldValue, screenOptions);
                    }
                }

            }
        }
    }

    private void executeInitForClassAnnotations(Screen screen, ScreenOptions screenOptions) {
        for (Annotation annotation : getClassAnnotations(screen)) {
            Collection<ScreenAnnotationExecutor> supportedAnnotationExecutors = getSupportedScreenAnnotationExecutors(annotation);

            for (ScreenAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.init(annotation, screen, screenOptions);
            }
        }
    }

    @Override
    public void executeBeforeShow(Screen screen, ScreenOptions screenOptions) {
        executeBeforeShowUniversal(screen, screenOptions);
    }

    private void executeBeforeShowUniversal(Screen screen, ScreenOptions screenOptions){
        executeBeforeShowForClassAnnotations(screen, screenOptions);
        executeBeforeShowForFieldAnnotations(screen, screenOptions);
    }

    private void executeBeforeShowForFieldAnnotations(Screen screen, ScreenOptions screenOptions) {
        for (Field field : getDeclaredFields(screen)) {

            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {

                Collection<ScreenFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedScreenFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(screen, field);
                    for (ScreenFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.beforeshow(annotation, screen, fieldValue, screenOptions);
                    }
                }
            }
        }
    }

    private void executeBeforeShowForClassAnnotations(Screen screen, ScreenOptions screenOptions) {
        for (Annotation annotation : getClassAnnotations(screen)) {

            Collection<ScreenAnnotationExecutor> supportedAnnotations = getSupportedScreenAnnotationExecutors(annotation);

            for (ScreenAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.beforeShow(annotation, screen, screenOptions);
            }
        }
    }


    protected Collection<ScreenAnnotationExecutor> getSupportedScreenAnnotationExecutors(Annotation annotation) {
        Collection<ScreenAnnotationExecutor> annotationExecutors = getScreenAnnotationExecutors();
        return (Collection<ScreenAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<ScreenFieldAnnotationExecutor> getSupportedScreenFieldAnnotationExecutors(Annotation annotation) {
        Collection<ScreenFieldAnnotationExecutor> annotationExecutors = getScreenFieldAnnotationExecutors();
        return (Collection<ScreenFieldAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<ScreenAnnotationExecutor> getScreenAnnotationExecutors() {
        return AppBeans.getAll(ScreenAnnotationExecutor.class).values();
    }


    protected Collection<ScreenFieldAnnotationExecutor> getScreenFieldAnnotationExecutors() {
        return AppBeans.getAll(ScreenFieldAnnotationExecutor.class).values();
    }
}
