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
package net.sf.jchemistry.gui;

import java.awt.Window;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.core.ElementProperties;
import net.sf.jchemistry.util.gui.OkCancelDialog;

/**
 * Dialog to select element(s) from a periodic table.
 * 
 * @author Philippe T. Pinard
 */
public final class PeriodicTableDialogFactory {

    /**
     * Shows a periodic table dialog. The selected element(s) are returned. A
     * few notes:
     * <ul>
     * <li>If no elements are selected, an empty set is returned.</li>
     * <li>If the cancel button is pressed, the original selection is returned.</li>
     * <li>If <code>isMultiSelection</code> is false (single selection mode),
     * the set contains only one item.</li>
     * </ul>
     * 
     * @param owner
     *            parent frame
     * @param isMultiSelection
     *            single or multi-selection mode
     * @param selections
     *            (optional) already selected elements
     * @return selected element(s). An empty set is returned if no element were
     *         selected or if the cancel button was pressed
     */
    public static Set<Element> show(Window owner, boolean isMultiSelection,
            Element... selections) {
        return show(owner, isMultiSelection,
                new HashSet<Element>(Arrays.asList(selections)));
    }



    /**
     * Shows a periodic table dialog. The selected element(s) are returned. A
     * few notes:
     * <ul>
     * <li>If no elements are selected, an empty set is returned.</li>
     * <li>If the cancel button is pressed, the original selection is returned.</li>
     * <li>If <code>isMultiSelection</code> is false (single selection mode),
     * the set contains only one item.</li>
     * </ul>
     * 
     * @param owner
     *            parent frame
     * @param isMultiSelection
     *            single or multi-selection mode
     * @param selections
     *            (optional) already selected elements
     * @return selected element(s). An empty set is returned if no element were
     *         selected or if the cancel button was pressed
     */
    public static Set<Element> show(Window owner, boolean isMultiSelection,
            Set<Element> selections) {
        Dialog dialog = new Dialog(owner, isMultiSelection, selections);
        if (dialog.show() != JOptionPane.OK_OPTION) {
            return selections;
        } else {
            return dialog.getSelections();
        }
    }

    /**
     * Internal class to construct the periodic table dialog.
     * 
     * @author ppinard
     */
    private static class Dialog extends OkCancelDialog {

        /**
         * Mouse motion listener to update the information label.
         * 
         * @author Philippe T. Pinard
         */
        private class InfoLabelListener implements ElementMouseMotionListener {

            @Override
            public void mouseMoved(ElementEvent e) {
                Element element = e.getElement();
                if (element == null) {
                    infoLabel.setText("");
                    return;
                }

                StringBuilder str = new StringBuilder();

                str.append("<html>");
                str.append(element.fullName() + " (" + element.z() + ")<br>");

                if (ElementProperties.hasAtomicMass(element))
                    str.append("Atomic mass: "
                            + ElementProperties.getAtomicMass(element)
                            + " g/mol<br>");

                if (ElementProperties.hasDensity(element))
                    str.append("Density: "
                            + ElementProperties.getDensity(element)
                            + " g/cm3<br>");
                str.append("</html>");

                infoLabel.setText(str.toString());
                getDialogWindow().pack();
            }
        }

        /** Main dialog panel. */
        private final JPanel mainPanel;

        /** Periodic table panel. */
        private final PeriodicTablePanel periodicTablePanel;

        /** Information label. */
        private final JLabel infoLabel;



        /**
         * Creates a new <code>PeriodicTableDialog</code>.
         * 
         * @param owner
         *            parent frame
         * @param isMultiSelection
         *            whether more than one element can be selected
         * @param selections
         *            (optional) selected elements
         */
        public Dialog(Window owner, boolean isMultiSelection,
                Set<Element> selections) {
            super(owner);
            setTitle("Periodic Table");
            setResizable(false);

            mainPanel = new JPanel(new MigLayout());

            periodicTablePanel = new PeriodicTablePanel();
            periodicTablePanel.setName("periodic table");
            periodicTablePanel.setMultiSelection(isMultiSelection);
            periodicTablePanel.setSelection(selections);
            periodicTablePanel.addElementMouseMotionListener(new InfoLabelListener());
            mainPanel.add(periodicTablePanel, "wrap");

            infoLabel = new JLabel("<Move the mouse over an element>");
            infoLabel.setName("info");
            infoLabel.setBorder(BorderFactory.createLoweredBevelBorder());
            mainPanel.add(infoLabel, "grow");
        }



        /**
         * Returns the element selected. The set is empty if no element were
         * selected or if the cancel button is pressed.
         * 
         * @return selected element
         */
        public Set<Element> getSelections() {
            return periodicTablePanel.getSelections();
        }



        @Override
        protected JComponent getMainComponent() {
            return mainPanel;
        }
    }

}
