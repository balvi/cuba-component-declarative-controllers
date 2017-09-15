package de.balvi.cuba.declarativecontrollers.web.browse

import com.haulmont.cuba.core.global.AppBeans
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import java.lang.annotation.Annotation

@CompileStatic
@Component(BrowseAnnotationExecutorService.NAME)
class BrowseAnnotationExecutorServiceBean implements BrowseAnnotationExecutorService {

    @Override
    void executeInit(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        browse.class.annotations.each { Annotation annotation ->
            getSupportedBrowseAnnotationExecutors(annotation).each { BrowseAnnotationExecutor annotationExecutor ->
                annotationExecutor.init(annotation, browse, params)
            }
        }
    }


    @Override
    void executeReady(AnnotatableAbstractLookup browse, Map<String, Object> params) {
        browse.class.annotations.each { Annotation annotation ->
            getSupportedBrowseAnnotationExecutors(annotation).each { BrowseAnnotationExecutor annotationExecutor ->
                annotationExecutor.ready(annotation, browse, params)
            }
        }
    }


    protected Collection<BrowseAnnotationExecutor> getSupportedBrowseAnnotationExecutors(Annotation annotation) {
        browseAnnotationExecutors.findAll { it.supports(annotation) }
    }

    protected Collection<BrowseAnnotationExecutor> getBrowseAnnotationExecutors() {
        AppBeans.getAll(BrowseAnnotationExecutor).values()
    }
}