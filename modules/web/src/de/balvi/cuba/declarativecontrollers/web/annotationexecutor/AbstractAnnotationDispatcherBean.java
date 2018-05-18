package de.balvi.cuba.declarativecontrollers.web.annotationexecutor;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.components.Frame;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseFieldAnnotationExecutor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

abstract public class AbstractAnnotationDispatcherBean<T extends AnnotationExecutor, K extends AnnotationExecutor> {

    protected Field[] getDeclaredFields(Frame frame) {
        return frame.getClass().getDeclaredFields();
    }

    protected com.haulmont.cuba.gui.components.Component getFieldValue(Frame frame, Field field) {
        try {
            field.setAccessible(true);
            return (com.haulmont.cuba.gui.components.Component) field.get(frame);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    protected Annotation[] getClassAnnotations(Frame frame) {
        return frame.getClass().getAnnotations();
    }

    protected <T extends AnnotationExecutor> Collection<T> getSupportedAnnotationExecutors(Collection<T> potentialAnnotationExecutors, Annotation annotation) {
        Collection<AnnotationExecutor> result = new ArrayList<AnnotationExecutor>();
        for (AnnotationExecutor annotationExecutor : potentialAnnotationExecutors) {
            if (annotationExecutor.supports(annotation)) {
                result.add(annotationExecutor);
            }
        }
        return (Collection<T>) result;
    }

}
