package de.balvi.cuba.declarativecontrollers.web.standardbrowse;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import de.balvi.cuba.declarativecontrollers.web.screen.ScreenAnnotationDispatcher;

import javax.inject.Inject;

public class AnnotatableStandardLookup<T extends Entity> extends StandardLookup<T> {

    @Inject
    protected ScreenAnnotationDispatcher screenAnnotationDispatcher;
    private ScreenOptions screenOptions;

    public AnnotatableStandardLookup(){
        addInitListener(this::onInitEvent);
        addBeforeShowListener(this::onBeforeShowEvent);
    }

    public void onInitEvent(InitEvent event) {
        this.screenOptions = event.getOptions();
        screenAnnotationDispatcher.executeInit(this, screenOptions);
    }

    public void onBeforeShowEvent(BeforeShowEvent event) {
        screenAnnotationDispatcher.executeBeforeShow(this, screenOptions);
    }
}
