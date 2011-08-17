/*
 * jChemistry
 * Copyright (C) 2011 Philippe T. Pinard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jchemistry.gui;

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
