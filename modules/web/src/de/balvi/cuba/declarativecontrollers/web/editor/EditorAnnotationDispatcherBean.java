package de.balvi.cuba.declarativecontrollers.web.editor;

import com.haulmont.cuba.core.global.AppBeans;
import groovy.transform.CompileStatic;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@CompileStatic
@Component(EditorAnnotationDispatcher.NAME)
class EditorAnnotationDispatcherBean implements EditorAnnotationDispatcher {

    @Override
    public void executeInit(AnnotatableAbstractEditor editor, Map<String, Object> params) {
        Annotation[] annotations = editor.getClass().getAnnotations();

        for (Annotation annotation : annotations) {

            Collection<EditorAnnotationExecutor> supportedAnnotations = getSupportedEditorAnnotationExecutors(annotation);

            for (EditorAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.init(annotation, editor, params);
            }
        }
    }

    @Override
    public void executePostInit(AnnotatableAbstractEditor editor) {
        Annotation[] annotations = editor.getClass().getAnnotations();

        for (Annotation annotation : annotations) {

            Collection<EditorAnnotationExecutor> supportedAnnotations = getSupportedEditorAnnotationExecutors(annotation);

            for (EditorAnnotationExecutor annotationExecutor : supportedAnnotations) {
                annotationExecutor.postInit(annotation, editor);
            }
        }
    }


    protected Collection<EditorAnnotationExecutor> getSupportedEditorAnnotationExecutors(Annotation annotation) {
        Collection<EditorAnnotationExecutor> browseAnnotationExecutors = getEditorAnnotationExecutors();

        Collection<EditorAnnotationExecutor> result = new ArrayList<>();
        for (EditorAnnotationExecutor annotationExecutor : browseAnnotationExecutors) {
            if (annotationExecutor.supports(annotation)) {
                result.add(annotationExecutor);
            }
        }
        return result;
    }

    protected Collection<EditorAnnotationExecutor> getEditorAnnotationExecutors() {
        return AppBeans.getAll(EditorAnnotationExecutor.class).values();
    }
}