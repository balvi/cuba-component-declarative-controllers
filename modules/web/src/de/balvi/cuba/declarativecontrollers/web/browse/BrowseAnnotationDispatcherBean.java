package de.balvi.cuba.declarativecontrollers.web.browse;

import com.haulmont.cuba.core.global.AppBeans;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AbstractAnnotationDispatcherBean;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseFieldAnnotationExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Component(BrowseAnnotationDispatcher.NAME)
class BrowseAnnotationDispatcherBean extends AbstractAnnotationDispatcherBean<BrowseAnnotationExecutor, BrowseFieldAnnotationExecutor> implements BrowseAnnotationDispatcher {

    @Override
    public void executeInit(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        executeInitForClassAnnotations(browse, params);
        executeInitForFieldAnnotations(browse, params);
    }

    private void executeInitForFieldAnnotations(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        for (Field field : getDeclaredFields(browse)) {
            Annotation[] fieldAnnotations = field.getAnnotations();

            for (Annotation annotation : fieldAnnotations) {

                Collection<BrowseFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedAnnotationExecutors(getBrowseFieldAnnotationExecutors(), annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(browse, field);
                    for (BrowseFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.init(annotation, browse, fieldValue, params);
                    }
                }

            }
        }
    }

    private void executeInitForClassAnnotations(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        for (Annotation annotation : getClassAnnotations(browse)) {
            Collection<BrowseAnnotationExecutor> supportedAnnotationExecutors = getSupportedAnnotationExecutors(getBrowseAnnotationExecutors(), annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.init(annotation, browse, params);
            }
        }
    }


    @Override
    public void executeReady(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        executeReadyForClassAnnotations(browse, params);
        executeReadyForFieldAnnotations(browse, params);
    }

    private void executeReadyForFieldAnnotations(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        for (Field field : getDeclaredFields(browse)) {

            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {

                Collection<BrowseFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedAnnotationExecutors(getBrowseFieldAnnotationExecutors(), annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(browse, field);
                    for (BrowseFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.ready(annotation, browse, fieldValue, params);
                    }
                }
            }
        }
    }

    private void executeReadyForClassAnnotations(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        for (Annotation annotation : getClassAnnotations(browse)) {

            Collection<BrowseAnnotationExecutor> supportedAnnotations = getSupportedAnnotationExecutors(getBrowseAnnotationExecutors(), annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.ready(annotation, browse, params);
            }
        }
    }


    /*
    protected Collection<BrowseAnnotationExecutor> getSupportedBrowseAnnotationExecutors(Annotation annotation) {
        Collection<BrowseAnnotationExecutor> annotationExecutors = getBrowseAnnotationExecutors();
        return (Collection<BrowseAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<BrowseFieldAnnotationExecutor> getSupportedBrowseFieldAnnotationExecutors(Annotation annotation) {
        Collection<BrowseFieldAnnotationExecutor> annotationExecutors = getBrowseFieldAnnotationExecutors();
        return (Collection<BrowseFieldAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }
    */
    protected Collection<BrowseAnnotationExecutor> getBrowseAnnotationExecutors() {
        return AppBeans.getAll(BrowseAnnotationExecutor.class).values();
    }


    protected Collection<BrowseFieldAnnotationExecutor> getBrowseFieldAnnotationExecutors() {
        return AppBeans.getAll(BrowseFieldAnnotationExecutor.class).values();
    }


}