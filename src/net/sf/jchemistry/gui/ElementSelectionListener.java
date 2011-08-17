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
