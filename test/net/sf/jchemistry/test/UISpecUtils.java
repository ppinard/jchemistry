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
