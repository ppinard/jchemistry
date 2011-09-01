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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.core.ElementComparator;
import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Input field to select one or many elements. The element(s) are selected using
 * the periodic table dialog. The selection is shown in a label.
 * 
 * @author Philippe T. Pinard
 */
public class ElementSelectionField extends JComponent {

    /**
     * Action listener for the browse button.
     * 
     * @author Philippe T. Pinard
     */
    private class BrowseButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Update
            selections =
                    PeriodicTableDialogFactory.show(null, isMultiSelection,
                            selections);
            List<Element> elements = new ArrayList<Element>(selections);
            Collections.sort(elements, new ElementComparator());

            StringBuilder selectionText = new StringBuilder();
            Iterator<Element> it = elements.iterator();
            while (it.hasNext()) {
                selectionText.append(it.next().symbol());
                if (it.hasNext())
                    selectionText.append(", ");
            }

            selectionField.setText(selectionText.toString());

            fireElementSelectionListeners();
        }
    }

    /** Serial version UID. */
    private static final long serialVersionUID = -8361631677623052083L;

    /** Label to display the selected elements. */
    private final JTextField selectionField;

    /** Button to select the elements from the periodic table dialog. */
    private JButton browseButton;

    /** Flag whether multiple elements can be selected. */
    private boolean isMultiSelection = false;

    /** Selected elements. */
    private Set<Element> selections = new HashSet<Element>();

    /** Listeners. */
    private List<ElementSelectionListener> listeners =
            new ArrayList<ElementSelectionListener>();



    /**
     * Creates a new <code>ElementSelectionField</code>.
     */
    public ElementSelectionField() {
        this(true);
    }



    /**
     * Creates a new <code>ElementSelectionField</code>.
     * 
     * @param showBrowseButton
     *            if <code>false</code> the browse button is not created. It can
     *            be created and position in the parent layout using the method
     *            {@link #getBrowseButton()}.
     */
    public ElementSelectionField(boolean showBrowseButton) {
        setLayout(new MigLayout());

        selectionField = new JTextField("", 20);
        selectionField.setEditable(false);
        add(selectionField, "grow, push");

        if (showBrowseButton) {
            browseButton = getBrowseButton();
            add(browseButton);
        }
    }



    /**
     * Adds an element selection listener.
     * 
     * @param listener
     *            listener
     */
    public void addElementSelectionListener(ElementSelectionListener listener) {
        if (listener == null)
            throw new NullPointerException("listener == null");
        listeners.add(listener);
    }



    /**
     * Fires all the element selection listeners.
     */
    protected void fireElementSelectionListeners() {
        ElementEvent event = new ElementEvent(this, selections);

        for (ElementSelectionListener listener : listeners)
            listener.selectionChanged(event);
    }



    /**
     * Returns a reference to the <code>Browse</code> button beside the field.
     * If the button does not exists, a new one is created and returned. This
     * new one is not shown on the GUI. It can be used to manually put it beside
     * the field in a specific layout
     * 
     * @return a reference to the <code>Browse</code> button
     */
    public JButton getBrowseButton() {
        // If the Browse button already exists, return it
        if (browseButton != null)
            return browseButton;

        // If the Browse button does not exists, create it and return it
        JButton browseButton = new JButton("...");
        browseButton.addActionListener(new BrowseButtonActionListener());

        return browseButton;
    }



    /**
     * Returns the element selection listeners.
     * 
     * @return listeners
     */
    public List<ElementSelectionListener> getElementSelectionListener() {
        return new ArrayList<ElementSelectionListener>(listeners);
    }



    /**
     * Returns the selected element from the dialog.
     * 
     * @return selected element or null if no element is selected
     */
    @CheckForNull
    public Element getSelection() {
        if (selections.isEmpty())
            return null;
        else
            return selections.iterator().next();
    }



    /**
     * Returns the selected element(s) from the dialog.
     * 
     * @return selected element(s)
     */
    public Set<Element> getSelections() {
        return new HashSet<Element>(selections);
    }



    /**
     * Returns whether multiple selection of elements is allowed.
     * 
     * @return <code>true</code> if more than one element can be selected,
     *         <code>false</code> otherwise
     */
    public boolean isMultiSelection() {
        return isMultiSelection;
    }



    /**
     * Removes an element selection listener.
     * 
     * @param listener
     *            listener
     */
    public void removeElementSelectionListener(ElementSelectionListener listener) {
        if (listener == null)
            throw new NullPointerException("listener == null");
        listeners.remove(listener);
    }



    /**
     * Sets whether more than one element can be selected.
     * 
     * @param mode
     *            <code>true</code> if more than one element can be selected,
     *            <code>false</code> otherwise
     */
    public void setMultiSelection(boolean mode) {
        isMultiSelection = mode;
    }



    /**
     * Selects the specified element in this periodic table. Other previously
     * selected elements are unselected.
     * 
     * @param element
     *            element
     */
    public void setSelection(Element element) {
        if (element == null)
            throw new NullPointerException("element == null");

        selections.clear();
        selections.add(element);

        fireElementSelectionListeners();
    }



    /**
     * Sets the specified elements in this periodic table. Other previously
     * selected elements are unselected.
     * 
     * @param elements
     *            elements
     */
    public void setSelection(Iterable<Element> elements) {
        selections.clear();

        for (Element element : elements) {
            if (element == null)
                throw new NullPointerException("One element == null");
            selections.add(element);
        }

        fireElementSelectionListeners();
    }

}
