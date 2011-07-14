package org.sf.jchemistry.gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.sf.jchemistry.core.Element;

/**
 * Dialog to select element(s) from a periodic table.
 * 
 * @author ppinard
 */
public class PeriodicTableDialog extends JDialog {

    /**
     * Action listener for the Cancel button.
     * 
     * @author ppinard
     */
    private class CancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }

    }

    /**
     * Mouse motion listener to update the information label.
     * 
     * @author ppinard
     */
    private class InfoLabelListener implements ElementMouseMotionListener {

        @Override
        public void mouseMoved(ElementEvent e) {
            Element element = e.getElement();
            StringBuilder str = new StringBuilder();

            str.append("<html>");
            str.append(element.fullName() + " (" + element.z() + ")<br>");
            str.append("Atomic mass: " + element.atomicMass() + " g/mol<br>");
            str.append("Density: " + element.density() + " g/cm3<br>");
            str.append("</html>");

            infoLabel.setText(str.toString());
        }

    }

    /**
     * Action listener of the Ok button.
     * 
     * @author ppinard
     */
    private class OkActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            selections = periodicTablePanel.getSelections();
            setVisible(false);
        }

    }

    /** Serial version UID. */
    private static final long serialVersionUID = 3327576207869697154L;

    /** Periodic table panel. */
    private final PeriodicTablePanel periodicTablePanel;

    /** Information label. */
    private final JLabel infoLabel;

    /** Selected elements. */
    private Set<Element> selections;



    /**
     * Creates a new <code>PeriodicTableDialog</code>.
     * 
     * @param owner
     *            parent frame
     */
    public PeriodicTableDialog(Frame owner) {
        super(owner, "Periodic Table", true);

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
     * Returns the selected element from the dialog. If the dialog is closed
     * with the Cancel button, <code>null</code> is returned.
     * 
     * @return selected element or <code>null</code> if the Cancel is pressed
     */
    public Element getSelection() {
        if (selections == null)
            return null;
        else if (selections.isEmpty())
            return null;
        else
            return selections.iterator().next();
    }



    /**
     * Returns the selected element(s) from the dialog. If the dialog is closed
     * with the Cancel button, <code>null</code> is returned.
     * 
     * @return selected element(s) or <code>null</code> if the Cancel is pressed
     */
    public Set<Element> getSelections() {
        return selections;
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



    @Override
    public void setVisible(boolean flag) {
        if (flag)
            selections = null; // reset
        super.setVisible(flag);
    }
}
