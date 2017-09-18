package de.balvi.cuba.declarativecontrollers.web.editor;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractEditor;

import javax.inject.Inject;
import java.util.Map;

public class AnnotatableAbstractEditor<T extends Entity> extends AbstractEditor<T> {

    @Inject
    private EditorAnnotationDispatcher editorAnnotationDispatcher;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        editorAnnotationDispatcher.executeInit(this, params);
    }

    @Override
    protected void postInit() {
        super.postInit();
        editorAnnotationDispatcher.executePostInit(this);
    }

}

