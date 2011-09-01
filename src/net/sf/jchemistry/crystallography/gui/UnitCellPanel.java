package net.sf.jchemistry.crystallography.gui;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.crystallography.core.CrystalSystem;
import net.sf.jchemistry.crystallography.core.UnitCell;

/**
 * Panel for the unit cell fields.
 * 
 * @author ppinard
 */
public class UnitCellPanel extends JPanel {

    /** Serial version UID. */
    private static final long serialVersionUID = 3386677191488808212L;

    /** Field for lattice constant a. */
    protected final JFormattedTextField aField;

    /** Field for lattice constant b. */
    protected final JFormattedTextField bField;

    /** Field for lattice constant c. */
    protected final JFormattedTextField cField;

    /** Field for lattice angle alpha. */
    protected final JFormattedTextField alphaField;

    /** Field for lattice angle beta. */
    protected final JFormattedTextField betaField;

    /** Field for lattice angle gamma. */
    protected final JFormattedTextField gammaField;



    /**
     * Sets whether the unit cell fields can be edited.
     * 
     * @param editable
     *            <code>true</code> or <code>false</code>
     */
    public void setEditable(boolean editable) {
        aField.setEditable(editable);
        bField.setEditable(editable);
        cField.setEditable(editable);
        alphaField.setEditable(editable);
        betaField.setEditable(editable);
        gammaField.setEditable(editable);
    }



    /**
     * Creates a new <code>UnitCellPanel</code>.
     * 
     * @param unitCell
     *            default values
     */
    public UnitCellPanel(UnitCell unitCell) {
        setLayout(new MigLayout());

        add(new JLabel("a"));
        aField = new JFormattedTextField(NumberFormat.getNumberInstance());
        aField.setColumns(5);
        aField.setName("a");
        aField.setValue(unitCell.getA());
        add(aField, "growx, pushx");
        add(new JLabel("\u212b")); // angstroms

        add(new JLabel("b"), "gapleft 25");
        bField = new JFormattedTextField(NumberFormat.getNumberInstance());
        bField.setColumns(5);
        bField.setName("b");
        bField.setValue(unitCell.getB());
        add(bField, "growx, pushx");
        add(new JLabel("\u212b")); // angstroms

        add(new JLabel("c"), "gapleft 25");
        cField = new JFormattedTextField(NumberFormat.getNumberInstance());
        cField.setColumns(5);
        cField.setName("c");
        cField.setValue(unitCell.getC());
        add(cField, "growx, pushx");
        add(new JLabel("\u212b")); // angstroms

        add(new JLabel("\u03b1"), "gapleft 25"); // alpha
        alphaField = new JFormattedTextField(NumberFormat.getNumberInstance());
        alphaField.setColumns(5);
        alphaField.setName("alpha");
        alphaField.setValue(Math.toDegrees(unitCell.getAlpha()));
        add(alphaField, "growx, pushx");
        add(new JLabel("\u00b0")); // deg

        add(new JLabel("\u03b2"), "gapleft 25"); // beta
        betaField = new JFormattedTextField(NumberFormat.getNumberInstance());
        betaField.setColumns(5);
        betaField.setName("beta");
        betaField.setValue(Math.toDegrees(unitCell.getBeta()));
        add(betaField, "growx, pushx");
        add(new JLabel("\u00b0")); // deg

        add(new JLabel("\u03b3"), "gapleft 25"); // gamma
        gammaField = new JFormattedTextField(NumberFormat.getNumberInstance());
        gammaField.setColumns(5);
        gammaField.setName("gamma");
        gammaField.setValue(Math.toDegrees(unitCell.getGamma()));
        add(gammaField, "growx, pushx");
        add(new JLabel("\u00b0")); // deg
    }



    /**
     * Refreshes unit cell fields based on crystal system.
     * 
     * @param cs
     *            crystal system
     */
    public void refresh(CrystalSystem cs) {
        switch (cs) {
        case TRICLINIC:
            aField.setEnabled(true);
            bField.setEnabled(true);
            cField.setEnabled(true);

            alphaField.setEnabled(true);
            betaField.setEnabled(true);
            gammaField.setEnabled(true);

            break;
        case MONOCLINIC:
            aField.setEnabled(true);
            bField.setEnabled(true);
            cField.setEnabled(true);

            alphaField.setEnabled(false);
            alphaField.setValue(90);
            gammaField.setEnabled(false);
            gammaField.setValue(90);

            break;
        case ORTHORHOMBIC:
            aField.setEnabled(true);
            bField.setEnabled(true);
            cField.setEnabled(true);

            alphaField.setEnabled(false);
            alphaField.setValue(90);
            betaField.setEnabled(false);
            betaField.setValue(90);
            gammaField.setEnabled(false);
            gammaField.setValue(90);

            break;
        case TRIGONAL:
            aField.setEnabled(true);
            bField.setEnabled(false);
            bField.setValue(aField.getValue());
            cField.setEnabled(false);
            cField.setValue(aField.getValue());

            alphaField.setEnabled(true);
            betaField.setEnabled(false);
            betaField.setValue(alphaField.getValue());
            gammaField.setEnabled(false);
            gammaField.setValue(alphaField.getValue());

            break;
        case TETRAGONAL:
            aField.setEnabled(true);
            bField.setEnabled(false);
            bField.setValue(aField.getValue());
            cField.setEnabled(true);

            alphaField.setEnabled(false);
            alphaField.setValue(90);
            betaField.setEnabled(false);
            betaField.setValue(90);
            gammaField.setEnabled(false);
            gammaField.setValue(90);

            break;
        case HEXAGONAL:
            aField.setEnabled(true);
            bField.setEnabled(false);
            bField.setValue(aField.getValue());
            cField.setEnabled(true);

            alphaField.setEnabled(false);
            alphaField.setValue(90);
            betaField.setEnabled(false);
            betaField.setValue(90);
            gammaField.setEnabled(false);
            gammaField.setValue(120);

            break;
        case CUBIC:
            aField.setEnabled(true);
            bField.setEnabled(false);
            bField.setValue(aField.getValue());
            cField.setEnabled(false);
            cField.setValue(aField.getValue());

            alphaField.setEnabled(false);
            alphaField.setValue(90);
            betaField.setEnabled(false);
            betaField.setValue(90);
            gammaField.setEnabled(false);
            gammaField.setValue(90);

            break;
        default:
            throw new RuntimeException("Unknown crystal system (" + cs + ")");
        }
    }

}