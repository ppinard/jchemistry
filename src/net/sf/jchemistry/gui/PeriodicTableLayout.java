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

import java.awt.Color;

import javax.swing.border.Border;

/**
 * Layout of the periodic table. It is used to customize the appearance of the
 * periodic table.
 * 
 * @author ppinard
 */
public interface PeriodicTableLayout {

    /**
     * Returns the color for the alkali metals.
     * 
     * @return color
     */
    public Color getAlkaliMetalsColor();



    /**
     * Returns the color for the alkaline earth metals.
     * 
     * @return color
     */
    public Color getAlkalineEarthMetalsColor();



    /**
     * Returns the color for the transition metals.
     * 
     * @return color
     */
    public Color getTransitionMetalsColor();



    /**
     * Returns the color for the post-transition metals.
     * 
     * @return color
     */
    public Color getPostTransitionMetalsColor();



    /**
     * Returns the color for the metalloids.
     * 
     * @return color
     */
    public Color getMetalloidsColor();



    /**
     * Returns the color for the non-metals.
     * 
     * @return color
     */
    public Color getNonMetalsColor();



    /**
     * Returns the color for the halogens.
     * 
     * @return color
     */
    public Color getHalogensColor();



    /**
     * Returns the color for the noble gases.
     * 
     * @return color
     */
    public Color getNobleGasesColor();



    /**
     * Returns the color for the lanthanides.
     * 
     * @return color
     */
    public Color getLanthanidesColor();



    /**
     * Returns the color for the actinides.
     * 
     * @return color
     */
    public Color getActinidesColor();



    /**
     * Returns the type of border around an element when it is selected.
     * 
     * @return border
     */
    public Border getSelectedBorder();
}
