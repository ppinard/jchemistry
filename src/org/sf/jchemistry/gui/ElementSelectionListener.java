package org.sf.jchemistry.gui;

import java.util.EventListener;

/**
 * Event listener triggered when the element(s) selected in the periodic table
 * are modified.
 * 
 * @author ppinard
 */
public interface ElementSelectionListener extends EventListener {

    /**
     * Method called after the element(s) selected in the periodic table were
     * changed.
     * 
     * @param event
     *            event
     */
    public void selectionChanged(ElementEvent event);

}
