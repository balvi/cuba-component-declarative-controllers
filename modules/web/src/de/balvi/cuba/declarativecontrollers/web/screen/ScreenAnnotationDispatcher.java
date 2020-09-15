package de.balvi.cuba.declarativecontrollers.web.screen;

import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenOptions;
import de.balvi.cuba.declarativecontrollers.web.standardbrowse.AnnotatableStandardLookup;

public interface ScreenAnnotationDispatcher {

    String NAME = "dbcdc_ScreenAnnotationExecutorService";

    void executeInit(Screen screen, ScreenOptions screenOptions);
    void executeBeforeShow(Screen screen, ScreenOptions screenOptions);
}
