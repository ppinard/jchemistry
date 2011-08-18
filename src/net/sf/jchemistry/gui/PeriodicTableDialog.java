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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.core.ElementProperties;

/**
 * Dialog to select element(s) from a periodic table.
 * 
 * @author Philippe T. Pinard
 */
public class PeriodicTableDialog extends JDialog {

    /**
     * Action listener for the Cancel button.
     * 
     * @author Philippe T. Pinard
     */
    private class CancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            returnValue = JOptionPane.CANCEL_OPTION;
            periodicTablePanel.clearSelection();
            setVisible(false);
        }

    }

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
                str.append("Density: " + ElementProperties.getDensity(element)
                        + " g/cm3<br>");
            str.append("</html>");

            infoLabel.setText(str.toString());
        }
    }

    /**
     * Internal window listener to duplicate cancel operation if the window is
     * closed by the user.
     * 
     * @author Philippe T. Pinard
     */
    private class InternalWindowListener implements WindowListener {

        @Override
        public void windowActivated(WindowEvent e) {
        }



        @Override
        public void windowClosed(WindowEvent e) {
        }



        @Override
        public void windowClosing(WindowEvent e) {
            returnValue = JOptionPane.CANCEL_OPTION;
            periodicTablePanel.clearSelection();
        }



        @Override
        public void windowDeactivated(WindowEvent e) {
        }



        @Override
        public void windowDeiconified(WindowEvent e) {
        }



        @Override
        public void windowIconified(WindowEvent e) {
        }



        @Override
        public void windowOpened(WindowEvent e) {
        }

    }

    /**
     * Action listener of the Ok button.
     * 
     * @author Philippe T. Pinard
     */
    private class OkActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            returnValue = JOptionPane.OK_OPTION;
            setVisible(false);
        }

    }

    /** Serial version UID. */
    private static final long serialVersionUID = 3327576207869697154L;

    /** Periodic table panel. */
    private final PeriodicTablePanel periodicTablePanel;

    /** Information label. */
    private final JLabel infoLabel;

    /** Return value from the dialog (OK or CANCEL). */
    private int returnValue;



    /**
     * Creates a new <code>PeriodicTableDialog</code>.
     * 
     * @param owner
     *            parent frame
     */
    public PeriodicTableDialog(Frame owner) {
        super(owner, "Periodic Table", true);

        addWindowListener(new InternalWindowListener());

        setLayout(new MigLayout("", "[grow][50]", ""));

        periodicTablePanel = new PeriodicTablePanel();
        periodicTablePanel.addElementMouseMotionListener(new InfoLabelListener());
        add(periodicTablePanel, "span 2, wrap");

        infoLabel = new JLabel("blah");
        infoLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        add(infoLabel, "cell 0 1 1 2, grow");

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new OkActionListener());
        add(okButton, "cell 1 1, grow, align right");

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelActionListener());
        add(cancelButton, "cell 1 2, grow, align right");

        pack();
        setResizable(false);
    }



    /**
     * Returns whether the dialog was closed with OK or CANCEL button.
     * 
     * @return {@link JOptionPane#OK_OPTION} or
     *         {@link JOptionPane#CANCEL_OPTION}
     */
    public int getReturnValue() {
        return returnValue;
    }



    /**
     * Returns the selected element from the dialog.
     * 
     * @return selected element
     */
    public Element getSelection() {
        return periodicTablePanel.getSelection();
    }



    /**
     * Returns the selected element(s) from the dialog.
     * 
     * @return selected element(s)
     */
    public Set<Element> getSelections() {
        return periodicTablePanel.getSelections();
    }



    /**
     * Returns whether multiple selection of elements is allowed.
     * 
     * @return <code>true</code> if more than one element can be selected,
     *         <code>false</code> otherwise
     */
    public boolean isMultiSelection() {
        return periodicTablePanel.isMultiSelection();
    }



    /**
     * Sets whether more than one element can be selected.
     * 
     * @param mode
     *            <code>true</code> if more than one element can be selected,
     *            <code>false</code> otherwise
     */
    public void setMultiSelection(boolean mode) {
        periodicTablePanel.setMultiSelection(mode);
    }



    /**
     * Selects the specified element in this periodic table. Other previously
     * selected elements are unselected.
     * 
     * @param element
     *            element
     */
    public void setSelection(Element element) {
        periodicTablePanel.setSelection(element);
    }



    /**
     * Sets the specified elements in this periodic table. Other previously
     * selected elements are unselected.
     * 
     * @param elements
     *            elements
     */
    public void setSelection(Iterable<Element> elements) {
        periodicTablePanel.setSelection(elements);
    }

}
