package de.balvi.cuba.declarativecontrollers.web.helper;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ButtonsPanel;
import com.haulmont.cuba.gui.components.ComponentContainer;

import java.util.List;

public interface ButtonsPanelHelper {


    String NAME = "dbcdc_ButtonsPanelHelper";

    /**
     * Creates or determines a button from a button panel
     *
     * @param container the UI container that should be searched
     * @param buttonId the buttonId to search for
     * @param buttonsPanelId the buttonsPanelId to search for
     * @return the Button, which either got created or has been found
     */
    Button getOrCreateButton(ComponentContainer container, String buttonId, String buttonsPanelId);


    /**
     * Creates a button
     *
     * @param id the buttonId to search for
     * @param buttonsPanel the buttonsPanel where the button gets added
     * @param beforeButtonIds Buttons, that should be placed before the new button
     * @return the Button which either got created
     */
    Button createButton(String id, ButtonsPanel buttonsPanel, List<String> beforeButtonIds);

}
