package de.balvi.cuba.declarativecontrollers.web.screen;

import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import com.haulmont.cuba.gui.screen.Subscribe;

import javax.inject.Inject;

public class AnnotatableScreen extends Screen {

    @Inject
    protected ScreenAnnotationDispatcher screenAnnotationDispatcher;
    private ScreenOptions screenOptions;

    public AnnotatableScreen(){
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
