package de.balvi.cuba.declarativecontrollers.web.entitycombined;

import com.haulmont.cuba.gui.components.EntityCombinedScreen;

import javax.inject.Inject;
import java.util.Map;

public class AnnotatableAbstractCombined extends EntityCombinedScreen {

    @Inject
    protected CombinedAnnotationDispatcher combinedAnnotationDispatcher;

    private Map<String, Object> params;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        this.params = params;
        combinedAnnotationDispatcher.executeInit(this, params);
    }

    @Override
    public void ready() {
        combinedAnnotationDispatcher.executeReady(this, params);
    }

}
