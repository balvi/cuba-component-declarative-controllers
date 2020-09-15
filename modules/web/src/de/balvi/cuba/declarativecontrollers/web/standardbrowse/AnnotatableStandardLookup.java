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

    @Subscribe
    public void onInit(InitEvent event) {
        this.screenOptions = event.getOptions();
        screenAnnotationDispatcher.executeInit(this, screenOptions);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        screenAnnotationDispatcher.executeBeforeShow(this, screenOptions);
    }
}
