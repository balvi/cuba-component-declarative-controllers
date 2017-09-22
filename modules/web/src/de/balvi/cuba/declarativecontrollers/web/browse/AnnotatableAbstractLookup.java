package de.balvi.cuba.declarativecontrollers.web.browse;

import com.haulmont.cuba.gui.components.AbstractLookup;

import javax.inject.Inject;
import java.util.Map;

public class AnnotatableAbstractLookup extends AbstractLookup {

    @Inject
    protected BrowseAnnotationDispatcher browseAnnotationDispatcher;

    private Map<String, Object> params;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        this.params = params;
        browseAnnotationDispatcher.executeInit(this, params);
    }

    @Override
    public void ready() {
        browseAnnotationDispatcher.executeReady(this, params);
    }

}
