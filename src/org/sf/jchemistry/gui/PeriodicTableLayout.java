package org.sf.jchemistry.gui;

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
