package de.balvi.cuba.declarativecontrollers.web.annotationexecutor;

import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.screen.Screen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

abstract public class AbstractAnnotationDispatcherBean<T extends AnnotationExecutor, K extends AnnotationExecutor> {

    protected Field[] getDeclaredFields(Screen screen) {
        return screen.getClass().getDeclaredFields();
    }

    protected com.haulmont.cuba.gui.components.Component getFieldValue(Screen screen, Field field) {
        try {
            field.setAccessible(true);
            return (com.haulmont.cuba.gui.components.Component) field.get(screen);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Annotation[] getClassAnnotations(Screen screen) {
        return screen.getClass().getAnnotations();
    }

    protected Collection<? extends AnnotationExecutor> getSupportedAnnotationExecutors(Collection<? extends AnnotationExecutor> potentialAnnotationExecutors, Annotation annotation) {
        Collection<AnnotationExecutor> result = new ArrayList<AnnotationExecutor>();
        for (AnnotationExecutor annotationExecutor : potentialAnnotationExecutors) {
            if (annotationExecutor.supports(annotation)) {
                result.add(annotationExecutor);
            }
        }
        return result;

    }
}
