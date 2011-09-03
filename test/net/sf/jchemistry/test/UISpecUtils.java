/*
 * jChemistry
 * Copyright (C) 2011 Philippe T. Pinard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jchemistry.test;

import javax.swing.JComboBox;

import org.uispec4j.ComboBox;
import org.uispec4j.Panel;
import org.uispec4j.TextBox;
import org.uispec4j.finder.ComponentMatchers;

public class UISpecUtils {

    /**
     * Returns the text box with the specified inner name.
     * 
     * @param panel
     *            panel
     * @param innerName
     *            inner name
     * @return text box
     */
    public static TextBox getTextBox(Panel panel, String innerName) {
        return panel.getTextBox(ComponentMatchers.innerNameIdentity(innerName));
    }



    /**
     * Returns the combo box with the specified inner name.
     * 
     * @param panel
     *            panel
     * @param innerName
     *            inner name
     * @return combo box
     */
    public static ComboBox getComboBox(Panel panel, String innerName) {
        return panel.getComboBox(ComponentMatchers.innerNameIdentity(innerName));
    }



    /**
     * Returns the selected object of the combo box with the specified name.
     * 
     * @param panel
     *            panel
     * @param innerName
     *            inner name
     * @return selected object
     */
    public static Object getComboBoxSelection(Panel panel, String innerName) {
        JComboBox comboBox = getComboBox(panel, innerName).getAwtComponent();
        return comboBox.getSelectedItem();
    }
}
