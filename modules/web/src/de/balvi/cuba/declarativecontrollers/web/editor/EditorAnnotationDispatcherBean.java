package de.balvi.cuba.declarativecontrollers.web.editor;

import com.haulmont.cuba.core.global.AppBeans;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.AbstractAnnotationDispatcherBean;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorFieldAnnotationExecutor;
import groovy.transform.CompileStatic;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@CompileStatic
@Component(EditorAnnotationDispatcher.NAME)
class EditorAnnotationDispatcherBean extends AbstractAnnotationDispatcherBean implements EditorAnnotationDispatcher {

    @Override
    public void executeInit(AnnotatableAbstractEditor editor, Map<String, Object> params) {
        executeInitForClassAnnotations(editor, params);
        executeInitForFieldAnnotations(editor, params);
    }

    private void executeInitForClassAnnotations(AnnotatableAbstractEditor editor, Map<String, Object> params) {
        for (Annotation annotation : getClassAnnotations(editor)) {

            Collection<EditorAnnotationExecutor> supportedAnnotationExecutors = getSupportedAnnotationExecutors(getEditorAnnotationExecutors(), annotation);

            for (EditorAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.init(annotation, editor, params);
            }
        }
    }

    private void executeInitForFieldAnnotations(AnnotatableAbstractEditor editor, Map<String, Object> params) {
        for (Field field : getDeclaredFields(editor)) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {

                Collection<EditorFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedAnnotationExecutors(getEditorFieldAnnotationExecutors(), annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(editor, field);
                    for (EditorFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.init(annotation, editor, fieldValue, params);
                    }
                }

            }
        }
    }

    @Override
    public void executePostInit(AnnotatableAbstractEditor editor) {
        executePostInitForClassAnnotations(editor);
        executeInitForFieldAnnotations(editor);
    }

    private void executeInitForFieldAnnotations(AnnotatableAbstractEditor editor) {

        for (Field field : getDeclaredFields(editor)) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {
                Collection<EditorFieldAnnotationExecutor> supportedAnnotationExecutors = getSupportedAnnotationExecutors(getEditorFieldAnnotationExecutors(), annotation);

                if (supportedAnnotationExecutors != null && supportedAnnotationExecutors.size() > 0) {
                    com.haulmont.cuba.gui.components.Component fieldValue = getFieldValue(editor, field);
                    for (EditorFieldAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                        annotationExecutor.postInit(annotation, editor, fieldValue);
                    }
                }
            }
        }
    }

    private void executePostInitForClassAnnotations(AnnotatableAbstractEditor editor) {

        for (Annotation annotation : getClassAnnotations(editor)) {

            Collection<EditorAnnotationExecutor> supportedAnnotationExecutors = getSupportedAnnotationExecutors(getEditorAnnotationExecutors(), annotation);

            for (EditorAnnotationExecutor annotationExecutor : supportedAnnotationExecutors) {
                annotationExecutor.postInit(annotation, editor);
            }
        }
    }

    /*
    protected Collection<EditorAnnotationExecutor> getSupportedEditorAnnotationExecutors(Annotation annotation) {
        Collection<EditorAnnotationExecutor> annotationExecutors = getEditorAnnotationExecutors();
        return (Collection<EditorAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);
    }

    protected Collection<EditorFieldAnnotationExecutor> getSupportedEditorFieldAnnotationExecutors(Annotation annotation) {
        Collection<EditorFieldAnnotationExecutor> annotationExecutors = getEditorFieldAnnotationExecutors();
        return (Collection<EditorFieldAnnotationExecutor>) getSupportedAnnotationExecutors(annotationExecutors, annotation);

    }
    */

    protected Collection<EditorAnnotationExecutor> getEditorAnnotationExecutors() {
        return AppBeans.getAll(EditorAnnotationExecutor.class).values();
    }

    protected Collection<EditorFieldAnnotationExecutor> getEditorFieldAnnotationExecutors() {
        return AppBeans.getAll(EditorFieldAnnotationExecutor.class).values();
    }

}