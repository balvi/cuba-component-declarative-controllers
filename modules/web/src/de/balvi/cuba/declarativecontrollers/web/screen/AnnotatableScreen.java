package de.balvi.cuba.declarativecontrollers.web.screen;

import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import com.haulmont.cuba.gui.screen.Subscribe;

import javax.inject.Inject;

public class AnnotatableScreen extends Screen {

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
