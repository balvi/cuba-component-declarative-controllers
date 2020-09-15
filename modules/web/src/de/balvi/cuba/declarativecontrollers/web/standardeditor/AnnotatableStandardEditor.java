package de.balvi.cuba.declarativecontrollers.web.standardeditor;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import de.balvi.cuba.declarativecontrollers.web.screen.ScreenAnnotationDispatcher;

import javax.inject.Inject;

public class AnnotatableStandardEditor<T extends Entity> extends StandardEditor<T> {

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
