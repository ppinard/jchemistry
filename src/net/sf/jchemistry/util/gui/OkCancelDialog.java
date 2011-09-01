package net.sf.jchemistry.util.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * Simple implementation of a Ok/Cancel dialog.
 * 
 * @author ppinard
 */
public abstract class OkCancelDialog extends OkDialog {

    /**
     * Action listener for the ok button.
     * 
     * @author ppinard
     */
    private class OkButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isCorrect()) {
                returnValue = JOptionPane.OK_OPTION;
                close();
            }
        }
    }

    /**
     * Action listener for the cancel button.
     * 
     * @author ppinard
     */
    private class CancelButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            returnValue = JOptionPane.CANCEL_OPTION;
            close();
        }

    }

    /**
     * Return value of the dialog (either {@link JOptionPane#CLOSED_OPTION},
     * {@link JOptionPane#CANCEL_OPTION} or {@link JOptionPane#OK_OPTION}.
     */
    private int returnValue = JOptionPane.CLOSED_OPTION;



    /**
     * Creates a new <code>OkCancelDialog</code>.
     * 
     * @param owner
     *            parent window
     */
    public OkCancelDialog(Window owner) {
        super(owner);
    }



    @Override
    protected JComponent createContentPane() {
        JPanel mainPanel = new JPanel(new MigLayout());

        mainPanel.add(getMainComponent(), "grow, push, wrap");

        // Ok & cancel
        JButton okButton = new JButton("Ok");
        okButton.setName("ok");
        okButton.addActionListener(new OkButtonActionListener());
        mainPanel.add(okButton, "split 2, align right");

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setName("cancel");
        cancelButton.addActionListener(new CancelButtonActionListener());
        mainPanel.add(cancelButton, "align left");

        return mainPanel;
    }



    /**
     * Returns window of internal dialog.
     * 
     * @return window of internal dialog
     */
    @Override
    public Window getDialogWindow() {
        return dialog;
    }



    /**
     * Returns the main component of the dialog.
     * 
     * @return main component
     */
    @Override
    protected abstract JComponent getMainComponent();



    /**
     * Checks the values in the dialog and shows error dialog if some values are
     * incorrect.
     * 
     * @return <code>true</code> if all values are correct, <code>false</code>
     *         otherwise
     */
    @Override
    protected boolean isCorrect() {
        return true;
    }



    /**
     * Shows the dialog. Returns either {@link JOptionPane#CLOSED_OPTION},
     * {@link JOptionPane#CANCEL_OPTION} or {@link JOptionPane#OK_OPTION} based
     * on how the dialog was closed.
     * 
     * @return {@link JOptionPane#CLOSED_OPTION},
     *         {@link JOptionPane#CANCEL_OPTION} or
     *         {@link JOptionPane#OK_OPTION}
     */
    @Override
    public int show() {
        super.show();
        return returnValue;
    }

}
