package net.sf.jchemistry.crystallography.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.util.gui.OkDialog;

/**
 * Dialog to edit a phase.
 * 
 * @author ppinard
 */
public class PhaseInfoDialog extends OkDialog {

    /** Main dialog panel. */
    private final JPanel mainPanel;



    /**
     * Creates a new <code>PhaseInfoDialog</code>.
     * <p/>
     * <b>NOTE:</b> The dialog factory ({@link PhaseDialogFactory}) should be
     * used instead of this constructor.
     * 
     * @param owner
     *            parent frame
     * @param title
     *            title of dialog
     * @param phases
     *            phases to display
     */
    public PhaseInfoDialog(Window owner, String title, final Phase... phases) {
        super(owner);
        setTitle(title);
        setMinimumSize(new Dimension(600, 400));

        switch (phases.length) {
        case 0:
            throw new IllegalArgumentException(
                    "Please specify at least one phase");
        case 1:
            mainPanel = new PhaseInfoPanel(phases[0]);
            break;
        default:
            mainPanel = new JPanel(new MigLayout());

            String[] names = new String[phases.length];
            for (int i = 0; i < phases.length; i++)
                names[i] = phases[i].getName();

            mainPanel.add(new JLabel("Phase"), "split 2");
            final JComboBox phaseComboBox = new JComboBox(names);
            phaseComboBox.setName("phase");
            phaseComboBox.setSelectedIndex(0);
            mainPanel.add(phaseComboBox, "wrap");

            mainPanel.add(new JSeparator(JSeparator.HORIZONTAL), "growx, wrap");

            final JPanel cardPanel = new JPanel(new CardLayout());
            for (int i = 0; i < phases.length; i++)
                cardPanel.add(new PhaseInfoPanel(phases[i]),
                        Integer.toString(i));
            mainPanel.add(cardPanel, "grow, push, wrap");

            phaseComboBox.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout) (cardPanel.getLayout());
                    cl.show(cardPanel,
                            Integer.toString(phaseComboBox.getSelectedIndex()));
                }
            });

            break;
        }
    }



    @Override
    protected JComponent getMainComponent() {
        return mainPanel;
    }

}
