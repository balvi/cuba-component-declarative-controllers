package de.balvi.cuba.declarativecontrollers.web.editor

import com.haulmont.cuba.core.global.AppBeans
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import java.lang.annotation.Annotation

@CompileStatic
@Component(EditorAnnotationExecutorService.NAME)
class EditorAnnotationExecutorServiceBean implements EditorAnnotationExecutorService {

    @Override
    void executeInit(AnnotatableAbstractEditor editor, Map<String, Object> params) {
        editor.class.annotations.each { Annotation annotation ->
            getSupportedEditorAnnotationExecutors(annotation).each { EditorAnnotationExecutor executor ->
                executor.init(annotation, editor, params)
            }
        }
    }

    @Override
    void executePostInit(AnnotatableAbstractEditor editor) {
        editor.class.annotations.each { Annotation annotation ->
            getSupportedEditorAnnotationExecutors(annotation).each { EditorAnnotationExecutor executor ->
                executor.postInit(annotation, editor)
            }
        }
    }


    protected Collection<EditorAnnotationExecutor> getSupportedEditorAnnotationExecutors(Annotation annotation) {
        editorAnnotationExecutors.findAll { it.supports(annotation) }
    }

    protected Collection<EditorAnnotationExecutor> getEditorAnnotationExecutors() {
        AppBeans.getAll(EditorAnnotationExecutor).values()
    }
}