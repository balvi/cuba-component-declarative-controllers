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

    public AnnotatableStandardEditor(){
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
