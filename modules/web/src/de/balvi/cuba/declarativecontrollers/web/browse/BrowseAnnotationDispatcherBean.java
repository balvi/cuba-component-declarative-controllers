package de.balvi.cuba.declarativecontrollers.web.browse;

import com.haulmont.cuba.core.global.AppBeans;
import groovy.transform.CompileStatic;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@CompileStatic
@Component(BrowseAnnotationDispatcher.NAME)
class BrowseAnnotationDispatcherBean implements BrowseAnnotationDispatcher {

    @Override
    public void executeInit(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        Annotation[] annotations = browse.getClass().getAnnotations();

        for (Annotation annotation : annotations) {

            Collection<BrowseAnnotationExecutor> supportedAnnotations = getSupportedBrowseAnnotationExecutors(annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.init(annotation, browse, params);
            }
        }
    }


    @Override
    public void executeReady(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        Annotation[] annotations = browse.getClass().getAnnotations();

        for (Annotation annotation : annotations) {

            Collection<BrowseAnnotationExecutor> supportedAnnotations = getSupportedBrowseAnnotationExecutors(annotation);

            for (BrowseAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.ready(annotation, browse, params);
            }
        }
    }


    protected Collection<BrowseAnnotationExecutor> getSupportedBrowseAnnotationExecutors(Annotation annotation) {
        Collection<BrowseAnnotationExecutor> browseAnnotationExecutors = getBrowseAnnotationExecutors();

        Collection<BrowseAnnotationExecutor> result = new ArrayList<>();
        for (BrowseAnnotationExecutor annotationExecutor : browseAnnotationExecutors) {
            if (annotationExecutor.supports(annotation)) {
                result.add(annotationExecutor);
            }
        }
        return result;

    }

    protected Collection<BrowseAnnotationExecutor> getBrowseAnnotationExecutors() {
        return AppBeans.getAll(BrowseAnnotationExecutor.class).values();
    }
}