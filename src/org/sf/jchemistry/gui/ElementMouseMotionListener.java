package org.sf.jchemistry.gui;

import java.util.EventListener;

/**
 * Event listener triggered when the mouse moves over an element in the periodic
 * table.
 * 
 * @author ppinard
 */
public interface ElementMouseMotionListener extends EventListener {

    /**
     * Method called when the moves moves over an element in the periodic table.
     * Use {@link ElementEvent#getElement()} to retrieve the element over which
     * the mouse is located.
     * 
     * @param e
     *            event
     */
    public void mouseMoved(ElementEvent e);
}
