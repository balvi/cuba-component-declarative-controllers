package de.balvi.cuba.declarativecontrollers.web.entitycombined;

import com.haulmont.cuba.core.global.AppBeans;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AbstractAnnotationDispatcherBean;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseFieldAnnotationExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Component(CombinedAnnotationDispatcher.NAME)
class CombinedAnnotationDispatcherBean extends AbstractAnnotationDispatcherBean<BrowseAnnotationExecutor, BrowseFieldAnnotationExecutor> implements CombinedAnnotationDispatcher {

    @Override
    public void executeInit(AnnotatableAbstractCombined browse, Map<String, Object> params) {
        executeInitForClassAnnotations(browse, params);
        executeInitForFieldAnnotations(browse, params);
    }

    private void executeInitForFieldAnnotations(AnnotatableAbstractCombined browse, Map<String, Object> params) {
        for (Field field : getDeclaredFields(browse)) {
            Annotation[] fieldAnnotations = field.getAnnotations();

            for (Annotation annotation : fieldAnnotations) {

                Collection<BrowseFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedCombinedFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(browse, field);
                    for (BrowseFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.init(annotation, browse, fieldValue, params);
                    }
                }

            }
        }
    }

    private void executeInitForClassAnnotations(AnnotatableAbstractCombined browse, Map<String, Object> params) {
        for (Annotation annotation : getClassAnnotations(browse)) {
            Collection<BrowseAnnotationExecutor> supportedAnnotationExecutors = getSupportedCombinedAnnotationExecutors(annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.init(annotation, browse, params);
            }
        }
    }


    @Override
    public void executeReady(AnnotatableAbstractCombined browse, Map<String, Object> params) {
        executeReadyForClassAnnotations(browse, params);
        executeReadyForFieldAnnotations(browse, params);
    }

    private void executeReadyForFieldAnnotations(AnnotatableAbstractCombined browse, Map<String, Object> params) {
        for (Field field : getDeclaredFields(browse)) {

            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {

                Collection<BrowseFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedCombinedFieldAnnotationExecutors(annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(browse, field);
                    for (BrowseFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.ready(annotation, browse, fieldValue, params);
                    }
                }
            }
        }
    }

    private void executeReadyForClassAnnotations(AnnotatableAbstractCombined browse, Map<String, Object> params) {
        for (Annotation annotation : getClassAnnotations(browse)) {

            Collection<BrowseAnnotationExecutor> supportedAnnotations = getSupportedCombinedAnnotationExecutors(annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.ready(annotation, browse, params);
            }
        }
    }


    protected Collection<BrowseAnnotationExecutor> getSupportedCombinedAnnotationExecutors(Annotation annotation) {
        Collection<BrowseAnnotationExecutor> annotationExecutors = getCombinedAnnotationExecutors();
        return (Collection<BrowseAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<BrowseFieldAnnotationExecutor> getSupportedCombinedFieldAnnotationExecutors(Annotation annotation) {
        Collection<BrowseFieldAnnotationExecutor> annotationExecutors = getCombinedFieldAnnotationExecutors();
        return (Collection<BrowseFieldAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<BrowseAnnotationExecutor> getCombinedAnnotationExecutors() {
        return AppBeans.getAll(BrowseAnnotationExecutor.class).values();
    }


    protected Collection<BrowseFieldAnnotationExecutor> getCombinedFieldAnnotationExecutors() {
        return AppBeans.getAll(BrowseFieldAnnotationExecutor.class).values();
    }
}

