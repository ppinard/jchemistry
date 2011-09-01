package net.sf.jchemistry.util.gui;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * Simple implementation of a Ok dialog.
 * 
 * @author ppinard
 */
public abstract class OkDialog {

    /**
     * Action listener for the ok button.
     * 
     * @author ppinard
     */
    private class OkButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isCorrect()) {
                close();
            }
        }
    }

    /** Internal dialog. */
    protected final JDialog dialog;



    /**
     * Creates a new <code>OkCancelDialog</code>.
     * 
     * @param owner
     *            parent window
     */
    public OkDialog(Window owner) {
        dialog = new JDialog(owner, ModalityType.APPLICATION_MODAL);
        dialog.pack();
    }



    /**
     * Closes the dialog.
     */
    protected void close() {
        dialog.setVisible(false);
        dialog.dispose();
    }



    /**
     * Creates the content pane of the dialog. The dialog is made out of the
     * main component (see {@link #getMainComponent()}) and the OK button.
     * 
     * @return content pane of dialog
     */
    protected JComponent createContentPane() {
        JPanel mainPanel = new JPanel(new MigLayout());

        mainPanel.add(getMainComponent(), "grow, push, wrap");

        JButton okButton = new JButton("Ok");
        okButton.setName("ok");
        okButton.addActionListener(new OkButtonActionListener());
        mainPanel.add(okButton, "align right");
        dialog.getRootPane().setDefaultButton(okButton);

        return mainPanel;
    }



    /**
     * Returns window of internal dialog.
     * 
     * @return window of internal dialog
     */
    public Window getDialogWindow() {
        return dialog;
    }



    /**
     * Returns the main component of the dialog.
     * 
     * @return main component
     */
    protected abstract JComponent getMainComponent();



    /**
     * Returns the maximum size of the dialog.
     * 
     * @return an instance of <code>Dimension</code> that represents the maximum
     *         size of the dialog.
     */
    public Dimension getMaximumSize() {
        return dialog.getMaximumSize();
    }



    /**
     * Returns the minimum size of the dialog.
     * 
     * @return an instance of <code>Dimension</code> that represents the minimum
     *         size of the dialog.
     */
    public Dimension getMinimumSize() {
        return dialog.getMinimumSize();
    }



    /**
     * Gets the title of the dialog. The title is displayed in the dialog's
     * border.
     * 
     * @return the title of this dialog window. The title may be
     *         <code>null</code>.
     * @see #setTitle
     */
    public String getTitle() {
        return dialog.getTitle();
    }



    /**
     * Checks the values in the dialog and shows error dialog if some values are
     * incorrect.
     * 
     * @return <code>true</code> if all values are correct, <code>false</code>
     *         otherwise
     */
    protected boolean isCorrect() {
        return true;
    }



    /**
     * Indicates whether this dialog is resizable by the user. By default, all
     * dialogs are initially resizable.
     * 
     * @return <code>true</code> if the user can resize the dialog;
     *         <code>false</code> otherwise.
     * @see #setResizable
     */
    public boolean isResizable() {
        return dialog.isResizable();
    }



    /**
     * Sets the maximum size of the dialog to a constant value.
     * 
     * @param size
     *            the new maximum size of the dialog
     */
    public void setMaximumSize(Dimension size) {
        dialog.setMaximumSize(size);
    }



    /**
     * Sets the minimum size of the dialog to a constant value.
     * 
     * @param size
     *            the new minimum size of the dialog
     */
    public void setMinimumSize(Dimension size) {
        dialog.setMinimumSize(size);
    }



    /**
     * Sets whether this dialog is resizable by the user.
     * 
     * @param r
     *            <code>true</code> if the user can resize this dialog;
     *            <code>false</code> otherwise.
     * @see #isResizable
     */
    public void setResizable(boolean r) {
        dialog.setResizable(r);
    }



    /**
     * Sets the title of the dialog.
     * 
     * @param title
     *            the title displayed in the dialog's border; a null value
     *            results in an empty title
     * @see #getTitle
     */
    public void setTitle(String title) {
        dialog.setTitle(title);
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
    public int show() {
        // Create content pane
        dialog.setContentPane(createContentPane());
        dialog.pack();

        // Center on parent or screen
        // Code from http://javapractices.com
        int xCoord, yCoord;

        if (dialog.getParent() != null) {
            Dimension parent = dialog.getParent().getSize();
            Dimension window = dialog.getSize();
            xCoord =
                    dialog.getParent().getLocationOnScreen().x
                            + (parent.width / 2 - window.width / 2);
            yCoord =
                    dialog.getParent().getLocationOnScreen().y
                            + (parent.height / 2 - window.height / 2);

            // Ensure that no part of aWindow will be off-screen
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int xOffScreenExcess = xCoord + window.width - screen.width;
            if (xOffScreenExcess > 0) {
                xCoord = xCoord - xOffScreenExcess;
            }
            if (xCoord < 0) {
                xCoord = 0;
            }
            int yOffScreenExcess = yCoord + window.height - screen.height;
            if (yOffScreenExcess > 0) {
                yCoord = yCoord - yOffScreenExcess;
            }
            if (yCoord < 0) {
                yCoord = 0;
            }
        } else {
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension window = dialog.getSize();
            // ensure that no parts of aWindow will be off-screen
            if (window.height > screen.height) {
                window.height = screen.height;
            }
            if (window.width > screen.width) {
                window.width = screen.width;
            }
            xCoord = (screen.width / 2 - window.width / 2);
            yCoord = (screen.height / 2 - window.height / 2);
        }

        dialog.setLocation(xCoord, yCoord);

        // Show
        dialog.setVisible(true);
        return JOptionPane.OK_OPTION;
    }

}
