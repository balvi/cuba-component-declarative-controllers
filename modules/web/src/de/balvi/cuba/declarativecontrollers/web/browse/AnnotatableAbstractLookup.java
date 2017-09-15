package de.balvi.cuba.declarativecontrollers.web.browse;

import com.haulmont.cuba.gui.components.AbstractLookup;

import javax.inject.Inject;
import java.util.Map;

class AnnotatableAbstractLookup extends AbstractLookup {

    @Inject
    private BrowseAnnotationExecutorService browseAnnotationExecutorService;

    private Map<String, Object> params;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        this.params = params;
        browseAnnotationExecutorService.executeInit(this, params);
    }

    @Override
    public void ready() {
        browseAnnotationExecutorService.executeReady(this, params);
    }

}
