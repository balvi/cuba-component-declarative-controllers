package de.balvi.cuba.declarativecontrollers.web.editor;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractEditor;
import groovy.transform.CompileStatic;

import javax.inject.Inject;
import java.util.Map;

@CompileStatic
abstract class AnnotatableAbstractEditor<T extends Entity> extends AbstractEditor<T> {

    @Inject
    private EditorAnnotationExecutorService editorAnnotationExecutorService;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        editorAnnotationExecutorService.executeInit(this, params);
    }

    @Override
    protected void postInit() {
        super.postInit();
        editorAnnotationExecutorService.executePostInit(this);
    }

}

