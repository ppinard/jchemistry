package net.sf.jchemistry.crystallography.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.crystallography.core.AtomSite;
import net.sf.jchemistry.crystallography.core.AtomSites;
import net.sf.jchemistry.crystallography.core.Calculations;
import net.sf.jchemistry.crystallography.core.CrystalSystem;
import net.sf.jchemistry.crystallography.core.LaueGroup;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflector;
import net.sf.jchemistry.crystallography.core.Reflectors;
import net.sf.jchemistry.crystallography.core.ScatteringFactors;
import net.sf.jchemistry.crystallography.core.ScatteringFactorsFactory;
import net.sf.jchemistry.crystallography.core.SpaceGroup;
import net.sf.jchemistry.crystallography.core.SpaceGroups;
import net.sf.jchemistry.crystallography.core.UnitCell;
import net.sf.jchemistry.crystallography.core.UnitCellFactory;
import net.sf.jchemistry.gui.ElementSelectionField;
import net.sf.jchemistry.util.gui.OkCancelDialog;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Dialog to edit a phase.
 * 
 * @author ppinard
 */
public class PhaseEditDialog extends OkCancelDialog {

    /**
     * Dialog to create a new atom site.
     * 
     * @author ppinard
     */
    private static class AtomDialog extends OkCancelDialog {

        /** Main dialog panel. */
        private final JPanel mainPanel;

        /** Field for the element. */
        private final ElementSelectionField elementField;

        /** Field for the electron charge. */
        private final JFormattedTextField chargeField;

        /** Field for the x position. */
        private final JFormattedTextField xField;

        /** Field for the y position. */
        private final JFormattedTextField yField;

        /** Field for the z position. */
        private final JFormattedTextField zField;

        /** Field for the occupancy. */
        private final JFormattedTextField occupancyField;



        /**
         * Creates a new <code>AtomDialog</code>.
         * 
         * @param owner
         *            parent window
         * @param atom
         *            default values
         * @throws NullPointerException
         *             if atom is <code>null</code>
         */
        public AtomDialog(Window owner, AtomSite atom) {
            super(owner);
            setTitle("New atom position");
            setResizable(false);

            if (atom == null)
                throw new NullPointerException("atom == null");

            mainPanel = new JPanel(new MigLayout());

            // Element
            mainPanel.add(new JLabel("Element"));
            elementField = new ElementSelectionField();
            elementField.setName("element");
            elementField.setMultiSelection(false);
            elementField.setSelection(atom.getElement());
            mainPanel.add(elementField, "growx, pushx, wrap");

            // Charge
            mainPanel.add(new JLabel("Charge"));
            chargeField =
                    new JFormattedTextField(NumberFormat.getIntegerInstance());
            chargeField.setName("charge");
            chargeField.setColumns(10);
            chargeField.setValue(atom.getCharge());
            chargeField.setToolTipText("Electron charge of the atom");
            mainPanel.add(chargeField, "growx, pushx, wrap");

            // Position
            JLabel positionLabel = new JLabel("Position");
            positionLabel.setToolTipText("Position relative to the unit cell");
            mainPanel.add(positionLabel);

            mainPanel.add(new JLabel("x"), "split 6");
            xField = new JFormattedTextField(NumberFormat.getNumberInstance());
            xField.setName("x");
            xField.setColumns(5);
            xField.setValue(atom.getPosition().getX());
            xField.setToolTipText("Fraction of lattice constant a");
            mainPanel.add(xField, "growx, pushx");

            mainPanel.add(new JLabel("y"));
            yField = new JFormattedTextField(NumberFormat.getNumberInstance());
            yField.setName("y");
            yField.setColumns(5);
            yField.setValue(atom.getPosition().getY());
            yField.setToolTipText("Fraction of lattice constant b");
            mainPanel.add(yField, "growx, pushx");

            mainPanel.add(new JLabel("z"));
            zField = new JFormattedTextField(NumberFormat.getNumberInstance());
            zField.setName("z");
            zField.setColumns(5);
            zField.setValue(atom.getPosition().getZ());
            zField.setToolTipText("Fraction of lattice constant c");
            mainPanel.add(zField, "growx, pushx, wrap");

            // Occupancy
            mainPanel.add(new JLabel("Occupancy"));
            occupancyField =
                    new JFormattedTextField(NumberFormat.getNumberInstance());
            occupancyField.setName("occupancy");
            occupancyField.setColumns(10);
            occupancyField.setValue(atom.getOccupancy());
            occupancyField.setToolTipText("Occupancy of the atom at this position (=1.0 if the atom is always there)");
            mainPanel.add(occupancyField, "growx, pushx, wrap");
        }



        /**
         * Returns the <code>AtomSite</code> from the values in the dialog. This
         * method should be called after the dialog is closed.
         * 
         * @return atom site
         */
        public AtomSite getAtom() {
            Element element = elementField.getSelection();
            if (element == null)
                throw new RuntimeException("element == null");

            int charge = ((Number) chargeField.getValue()).intValue();

            double x = ((Number) xField.getValue()).doubleValue();
            double y = ((Number) yField.getValue()).doubleValue();
            double z = ((Number) zField.getValue()).doubleValue();

            double occupancy =
                    ((Number) occupancyField.getValue()).doubleValue();

            return new AtomSite(element, charge, new Vector3D(x, y, z),
                    occupancy);
        }



        @Override
        protected JComponent getMainComponent() {
            return mainPanel;
        }



        /**
         * Checks the values in the dialog and shows error dialog if some values
         * are incorrect.
         * 
         * @return <code>true</code> if all values are correct,
         *         <code>false</code> otherwise
         */
        @Override
        protected boolean isCorrect() {
            if (elementField.getSelection() == null) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Select an element", "Invalid element",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            double occupancy =
                    ((Number) occupancyField.getValue()).doubleValue();
            if (occupancy < 0.0 || occupancy > 1.0) {
                JOptionPane.showMessageDialog(getDialogWindow(), "Occupancy ("
                        + occupancy + ") must be between [0.0, 1.0].",
                        "Invalid occupancy", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }

    }

    /**
     * Action listener for the add button of the atoms table.
     * 
     * @author ppinard
     */
    private class AtomsAddButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            add(new AtomSite(Element.H, 0, Vector3D.ZERO, 1.0));
        }



        /**
         * Recursive call to create an atom.
         * 
         * @param atom
         *            previous value
         */
        private void add(AtomSite atom) {
            AtomDialog atomDialog = new AtomDialog(getDialogWindow(), atom);
            if (atomDialog.show() != JOptionPane.OK_OPTION)
                return;

            atom = atomDialog.getAtom();
            if (!((AtomsTableModel) atomsTable.getModel()).append(atom)) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "An atom already exists at this position",
                        "Invalid atom", JOptionPane.ERROR_MESSAGE);
                add(atom);
            }
        }

    }

    /**
     * Action listener for the calculate button of the atoms table.
     * 
     * @author ppinard
     */
    private class AtomsCalcButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AtomsTableModel model = (AtomsTableModel) atomsTable.getModel();
            AtomSites atoms = model.getAtomSites();
            SpaceGroup sg = (SpaceGroup) spaceGroupComboBox.getSelectedItem();

            atoms = Calculations.equivalentPositions(atoms, sg);

            model.clear();
            model.appendAll(atoms);
        }

    }

    /**
     * Action listener for the clear button of the atoms table.
     * 
     * @author ppinard
     */
    private class AtomsClearButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ((AtomsTableModel) atomsTable.getModel()).clear();
        }

    }

    /**
     * Action listener for the remove button of the atoms table.
     * 
     * @author ppinard
     */
    private class AtomsRemoveButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (atomsTable.getModel().getRowCount() == 0)
                return;

            int row = atomsTable.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Please select a row", "Invalid selection",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ((AtomsTableModel) atomsTable.getModel()).remove(row);
            }
        }

    }

    /**
     * Listener of the crystal system combo box.
     */
    private class CrystalSystemComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshLaueGroup();
            refreshUnitCell();
        }
    }

    /**
     * Listener of the Laue group combo box.
     */
    private class LaueGroupCBListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshSpaceGroup();
        }
    }

    /**
     * Dialog to calculate the reflectors.
     * 
     * @author ppinard
     */
    private static class ReflectorCalculateDialog extends OkCancelDialog {

        /** Main dialog panel. */
        private final JPanel mainPanel;

        /** Combo box for scattering factors. */
        private final JComboBox scatterComboBox;

        /** Field for maximum index. */
        private final JFormattedTextField maxIndexField;

        /** Field for minimum intensity. */
        private final JFormattedTextField minIntensityField;



        /**
         * Creates a new <code>ReflectorCalculateDialog</code>.
         * 
         * @param owner
         *            parent window
         */
        public ReflectorCalculateDialog(Window owner) {
            super(owner);
            setTitle("Calculate reflectors");
            setResizable(false);

            mainPanel = new JPanel(new MigLayout());

            // Scattering factors
            mainPanel.add(new JLabel("Scattering factors"));
            scatterComboBox = new JComboBox(ScatteringFactorsFactory.values());
            scatterComboBox.setName("scattering factors");
            scatterComboBox.setSelectedItem(ScatteringFactorsFactory.XRAY_TABULATED);
            mainPanel.add(scatterComboBox, "growx, pushx, wrap");

            // Max index
            mainPanel.add(new JLabel("Maximum index"));
            maxIndexField =
                    new JFormattedTextField(NumberFormat.getIntegerInstance());
            maxIndexField.setName("max index");
            maxIndexField.setColumns(5);
            maxIndexField.setValue(Integer.valueOf(4));
            maxIndexField.setToolTipText("maximum index of the reflcetors to compute");
            mainPanel.add(maxIndexField, "growx, pushx, wrap");

            // Minimum relative value
            mainPanel.add(new JLabel("Minimum intensity"));
            minIntensityField =
                    new JFormattedTextField(NumberFormat.getNumberInstance());
            minIntensityField.setName("minimum intensity");
            minIntensityField.setColumns(5);
            minIntensityField.setValue(Double.valueOf(1));
            minIntensityField.setToolTipText("minimum intensity relative to the most intense reflector, i.e. percentage of the maximum intensity");
            mainPanel.add(minIntensityField, "growx, pushx, split 2");
            mainPanel.add(new JLabel("%"), "wrap");
        }



        @Override
        protected JComponent getMainComponent() {
            return mainPanel;
        }



        /**
         * Returns the maximum index of the reflectors to generate. This method
         * should be called after the dialog is closed.
         * 
         * @return maximum index
         */
        public int getMaxIndex() {
            return ((Number) maxIndexField.getValue()).intValue();
        }



        /**
         * Returns the minimum intensity of the reflectors to generate. This
         * method should be called after the dialog is closed.
         * 
         * @return minimum intensity
         */
        public double getMinIntensity() {
            return ((Number) minIntensityField.getValue()).doubleValue() / 100.0;
        }



        /**
         * Returns the selected scattering factors. This method should be called
         * after the dialog is closed.
         * 
         * @return scattering factors
         */
        public ScatteringFactors getScatteringFactors() {
            return (ScatteringFactors) scatterComboBox.getSelectedItem();
        }



        @Override
        protected boolean isCorrect() {
            if (scatterComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Please select a scattering factors",
                        "Invalid scattering factors", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            int maxIndex = ((Number) maxIndexField.getValue()).intValue();
            if (maxIndex < 1) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Maximum index (" + maxIndex
                                + ") must be greater or equal to 1",
                        "Invalid maximum index", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            double minIntensity =
                    ((Number) minIntensityField.getValue()).doubleValue() / 100.0;
            if (minIntensity <= 0 || minIntensity >= 1) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Minimum intensity (" + minIntensity
                                + ") must be between ]0.0, 1.0[",
                        "Invalid minimum intensity", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }

    }

    /**
     * Dialog to create a new reflector.
     * 
     * @author ppinard
     */
    private static class ReflectorDialog extends OkCancelDialog {

        /** Main dialog panel. */
        private final JPanel mainPanel;

        /** Field for the h index. */
        private final JFormattedTextField hField;

        /** Field for the k index. */
        private final JFormattedTextField kField;

        /** Field for the l index. */
        private final JFormattedTextField lField;

        /** Field for the intensity. */
        private final JFormattedTextField intensityField;



        /**
         * Creates a new <code>ReflectorDialog</code>.
         * 
         * @param owner
         *            parent window
         * @param refl
         *            default values
         * @throws NullPointerException
         *             if refl is <code>null</code>
         */
        public ReflectorDialog(Window owner, Reflector refl) {
            super(owner);
            setTitle("New reflector");
            setResizable(false);

            if (refl == null)
                throw new NullPointerException("reflector == null");

            mainPanel = new JPanel(new MigLayout());

            // Indices
            JLabel indicesLabel = new JLabel("Indices");
            indicesLabel.setToolTipText("Crystallographic plane indices");
            mainPanel.add(indicesLabel);

            mainPanel.add(new JLabel("h"), "split 6");
            hField = new JFormattedTextField(NumberFormat.getIntegerInstance());
            hField.setName("h");
            hField.setColumns(5);
            hField.setValue(refl.getH());
            hField.setToolTipText("h index of the crystallographic plane");
            mainPanel.add(hField, "growx, pushx");

            mainPanel.add(new JLabel("k"));
            kField = new JFormattedTextField(NumberFormat.getIntegerInstance());
            kField.setName("k");
            kField.setColumns(5);
            kField.setValue(refl.getK());
            kField.setToolTipText("k index of the crystallographic plane");
            mainPanel.add(kField, "growx, pushx");

            mainPanel.add(new JLabel("l"));
            lField = new JFormattedTextField(NumberFormat.getIntegerInstance());
            lField.setName("l");
            lField.setColumns(5);
            lField.setValue(refl.getL());
            lField.setToolTipText("l index of the crystallographic plane");
            mainPanel.add(lField, "growx, pushx, wrap");

            // Intensity
            mainPanel.add(new JLabel("Intensity"));
            intensityField =
                    new JFormattedTextField(NumberFormat.getNumberInstance());
            intensityField.setName("intensity");
            intensityField.setColumns(10);
            intensityField.setValue(refl.getIntensity());
            intensityField.setToolTipText("diffraction intensity");
            mainPanel.add(intensityField, "growx, pushx, wrap");
        }



        @Override
        protected JComponent getMainComponent() {
            return mainPanel;
        }



        /**
         * Returns the <code>Reflector</code> from the values in the dialog.
         * This method should be called after the dialog is closed.
         * 
         * @return reflector
         */
        public Reflector getReflector() {
            int h = ((Number) hField.getValue()).intValue();
            int k = ((Number) kField.getValue()).intValue();
            int l = ((Number) lField.getValue()).intValue();
            double intensity =
                    ((Number) intensityField.getValue()).doubleValue();

            return new Reflector(h, k, l, intensity);
        }



        @Override
        protected boolean isCorrect() {
            int h = ((Number) hField.getValue()).intValue();
            int k = ((Number) kField.getValue()).intValue();
            int l = ((Number) lField.getValue()).intValue();
            if (h == 0 && k == 0 && l == 0) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Invalid indices (h=0, k=0, l=0)", "Invalid indices",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            double intensity =
                    ((Number) intensityField.getValue()).doubleValue();
            if (intensity < 0) {
                JOptionPane.showMessageDialog(getDialogWindow(), "Intensity ("
                        + intensity + ") must be greater or equal to 0.0.",
                        "Invalid intensity", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }

    }

    /**
     * Action listener for the add button of the reflectors table.
     * 
     * @author ppinard
     */
    private class ReflectorsAddButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            add(new Reflector(1, 0, 0, 0.0));
        }



        /**
         * Recursive call to create a reflector.
         * 
         * @param refl
         *            previous value
         */
        private void add(Reflector refl) {
            ReflectorDialog reflDialog =
                    new ReflectorDialog(getDialogWindow(), refl);
            if (reflDialog.show() != JOptionPane.OK_OPTION)
                return;

            refl = reflDialog.getReflector();
            if (!((ReflectorsTableModel) reflectorsTable.getModel()).append(refl)) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "A reflector with the same indices already exists",
                        "Invalid reflector", JOptionPane.ERROR_MESSAGE);
                add(refl);
            }
        }

    }

    /**
     * Action listener for the calculate button of the reflectors table.
     * 
     * @author ppinard
     */
    private class ReflectorsCalcButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check atoms
            if (atomsTable.getModel().getRowCount() == 0) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Please add at least one atom site",
                        "Atom site required", JOptionPane.ERROR_MESSAGE);
                return;
            }
            AtomSites atoms =
                    ((AtomsTableModel) atomsTable.getModel()).getAtomSites();

            // Check unit cell
            if (crystalSystemComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Please select a crystal system",
                        "Invalid crystal system", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CrystalSystem cs =
                    (CrystalSystem) crystalSystemComboBox.getSelectedItem();
            if (!unitCellPanel.isCorrect(cs))
                return;
            UnitCell unitCell = unitCellPanel.getUnitCell(cs);

            // Get extra options via dialog
            ReflectorCalculateDialog calcDialog =
                    new ReflectorCalculateDialog(getDialogWindow());
            if (calcDialog.show() != JOptionPane.OK_OPTION)
                return;

            ScatteringFactors scatter = calcDialog.getScatteringFactors();
            int maxIndex = calcDialog.getMaxIndex();
            double minIntensity = calcDialog.getMinIntensity();

            // Generate reflectors
            Reflectors refls;
            try {
                refls =
                        Reflectors.generate(unitCell, atoms, scatter, maxIndex,
                                minIntensity);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        ex.getMessage(), "Error in calculation",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add reflectors to table
            ReflectorsTableModel model =
                    (ReflectorsTableModel) reflectorsTable.getModel();
            model.clear();
            model.appendAll(refls);
        }
    }

    /**
     * Action listener for the clear button of the reflectors table.
     * 
     * @author ppinard
     */
    private class ReflectorsClearButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ((ReflectorsTableModel) reflectorsTable.getModel()).clear();
        }

    }

    /**
     * Action listener for the remove button of the reflectors table.
     * 
     * @author ppinard
     */
    private class ReflectorsRemoveButtonActionListener implements
            ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (reflectorsTable.getModel().getRowCount() == 0)
                return;

            int row = reflectorsTable.getSelectedRow();

            if (row < 0) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        "Please select a row", "Invalid selection",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ((ReflectorsTableModel) reflectorsTable.getModel()).remove(row);
            }
        }

    }

    /**
     * Renderer for the space group combo box.
     * 
     * @author ppinard
     */
    private static class SpaceGroupComboBoxRenderer extends
            BasicComboBoxRenderer {

        /** Serial version UID . */
        private static final long serialVersionUID = 951544563056113863L;



        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);

            SpaceGroup sg = (SpaceGroup) value;

            setText(sg.getSymbol() + " (" + sg.getIndex() + ")");

            return this;
        }
    }

    /**
     * Internal panel for the unit cell fields.
     * 
     * @author ppinard
     */
    private class UnitCellEditPanel extends UnitCellPanel {

        /** Serial version UID. */
        private static final long serialVersionUID = 523770412596422070L;



        /**
         * Creates a new <code>UnitCellPanel</code>.
         * 
         * @param unitCell
         *            default values
         */
        public UnitCellEditPanel(UnitCell unitCell) {
            super(unitCell);
        }



        /**
         * Returns the unit cell.
         * 
         * @param cs
         *            crystal system
         * @return unit cell or <code>null</code> if one of the values is
         *         incorrect
         */
        public UnitCell getUnitCell(CrystalSystem cs) {
            double a = ((Number) aField.getValue()).doubleValue();
            double b = ((Number) bField.getValue()).doubleValue();
            double c = ((Number) cField.getValue()).doubleValue();

            double alpha =
                    Math.toRadians(((Number) alphaField.getValue()).doubleValue());
            double beta =
                    Math.toRadians(((Number) betaField.getValue()).doubleValue());
            double gamma =
                    Math.toRadians(((Number) gammaField.getValue()).doubleValue());

            switch (cs) {
            case TRICLINIC:
                return UnitCellFactory.triclinic(a, b, c, alpha, beta, gamma);
            case MONOCLINIC:
                return UnitCellFactory.monoclinic(a, b, c, beta);
            case ORTHORHOMBIC:
                return UnitCellFactory.orthorhombic(a, b, c);
            case TRIGONAL:
                return UnitCellFactory.trigonal(a, alpha);
            case TETRAGONAL:
                return UnitCellFactory.tetragonal(a, c);
            case HEXAGONAL:
                return UnitCellFactory.hexagonal(a, c);
            case CUBIC:
                return UnitCellFactory.cubic(a);
            default:
                throw new IllegalArgumentException("Unknown crystal system: "
                        + cs);
            }
        }



        /**
         * Checks whether all the lattice constants and angles of the unit cell
         * are correct.
         * 
         * @param cs
         *            crystal system
         * @return <code>true</code> if all parameters are correct,
         *         <code>false</code> otherwise
         */
        public boolean isCorrect(CrystalSystem cs) {
            double a = ((Number) aField.getValue()).doubleValue();
            double b = ((Number) bField.getValue()).doubleValue();
            double c = ((Number) cField.getValue()).doubleValue();

            double alpha =
                    Math.toRadians(((Number) alphaField.getValue()).doubleValue());
            double beta =
                    Math.toRadians(((Number) betaField.getValue()).doubleValue());
            double gamma =
                    Math.toRadians(((Number) gammaField.getValue()).doubleValue());

            try {
                switch (cs) {
                case TRICLINIC:
                    UnitCellFactory.triclinic(a, b, c, alpha, beta, gamma);
                    break;
                case MONOCLINIC:
                    UnitCellFactory.monoclinic(a, b, c, beta);
                    break;
                case ORTHORHOMBIC:
                    UnitCellFactory.orthorhombic(a, b, c);
                    break;
                case TRIGONAL:
                    UnitCellFactory.trigonal(a, alpha);
                    break;
                case TETRAGONAL:
                    UnitCellFactory.tetragonal(a, c);
                    break;
                case HEXAGONAL:
                    UnitCellFactory.hexagonal(a, c);
                    break;
                case CUBIC:
                    UnitCellFactory.cubic(a);
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Unknown crystal system: " + cs);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getDialogWindow(),
                        ex.getMessage(), "Invalid unit cell",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }

    }

    /** Main dialog panel. */
    private final JPanel mainPanel;

    /** Field for name. */
    private final JTextField nameField;

    /** Field for citation. */
    private final JTextField citationField;

    /** Combo box for crystal system. */
    private final JComboBox crystalSystemComboBox;

    /** Combo box for Laue group. */
    private final JComboBox laueGroupComboBox;

    /** Combo box for space group. */
    private final JComboBox spaceGroupComboBox;

    /** Panel for the unit cell fields. */
    private final UnitCellEditPanel unitCellPanel;

    /** Table for atom positions. */
    private final JTable atomsTable;

    /** Table for reflectors. */
    private final JTable reflectorsTable;



    /**
     * Creates a new <code>PhaseEditDialog</code>.
     * <p/>
     * <b>NOTE:</b> The dialog factory ({@link PhaseDialogFactory}) should be
     * used instead of this constructor.
     * 
     * @param owner
     *            parent frame
     * @param title
     *            title of dialog
     * @param phase
     *            phase to edit
     * @param addIcon
     *            icon for add button
     * @param removeIcon
     *            icon for remove button
     * @param clearIcon
     *            icon for clear button
     * @param calcIcon
     *            icon for calculate button
     */
    public PhaseEditDialog(Window owner, String title, Phase phase,
            ImageIcon addIcon, ImageIcon removeIcon, ImageIcon clearIcon,
            ImageIcon calcIcon) {
        super(owner);
        setTitle(title);
        setMinimumSize(new Dimension(600, 400));

        if (phase == null)
            throw new NullPointerException("phase == null");
        if (addIcon == null)
            throw new NullPointerException("add icon == null");
        if (removeIcon == null)
            throw new NullPointerException("remove icon == null");
        if (clearIcon == null)
            throw new NullPointerException("clear icon == null");
        if (calcIcon == null)
            throw new NullPointerException("calc icon == null");

        mainPanel = new JPanel(new MigLayout());

        // Name
        mainPanel.add(new JLabel("Name"), "split 4");
        nameField = new JTextField(20);
        nameField.setName("name");
        nameField.setToolTipText("Mineral or common name");
        mainPanel.add(nameField, "growx, pushx");

        nameField.setText(phase.getName());

        // Reference
        mainPanel.add(new JLabel("Citation"), "split 2");
        citationField = new JTextField(30);
        citationField.setName("citation");
        citationField.setToolTipText("Reference to journal or book");
        mainPanel.add(citationField, "growx, pushx, wrap");

        citationField.setText(phase.getCitation());

        // Crystal system
        mainPanel.add(new JLabel("Crystal system"), "split 6");
        crystalSystemComboBox = new JComboBox(CrystalSystem.values());
        crystalSystemComboBox.setName("crystal system");
        crystalSystemComboBox.addActionListener(new CrystalSystemComboBoxListener());
        mainPanel.add(crystalSystemComboBox, "growx, pushx");

        // Laue group
        mainPanel.add(new JLabel("Laue group"));
        laueGroupComboBox = new JComboBox();
        laueGroupComboBox.setName("Laue group");
        laueGroupComboBox.addActionListener(new LaueGroupCBListener());
        mainPanel.add(laueGroupComboBox, "growx, pushx");

        // Space group
        mainPanel.add(new JLabel("Space group"));
        spaceGroupComboBox = new JComboBox();
        spaceGroupComboBox.setName("space group");
        spaceGroupComboBox.setRenderer(new SpaceGroupComboBoxRenderer());
        mainPanel.add(spaceGroupComboBox, "growx, pushx, wrap");

        // Unit cell
        unitCellPanel = new UnitCellEditPanel(phase.getUnitCell());
        unitCellPanel.setName("unit cell");
        unitCellPanel.setBorder(new TitledBorder("Unit Cell"));
        mainPanel.add(unitCellPanel, "growx, pushx, wrap");

        // *** Update crystal system, Laue group, space group and unit cell
        crystalSystemComboBox.setSelectedItem(phase.getSpaceGroup().getCrystalSystem());
        laueGroupComboBox.setSelectedItem(phase.getSpaceGroup().getLaueGroup());
        spaceGroupComboBox.setSelectedItem(phase.getSpaceGroup());

        refreshLaueGroup();
        refreshUnitCell();

        // Atoms
        JPanel atomsPanel = new JPanel(new MigLayout());
        atomsPanel.setBorder(new TitledBorder("Atoms"));
        mainPanel.add(atomsPanel, "grow, push, split 2");

        AtomsTableModel atomsModel = new AtomsTableModel();
        for (AtomSite atom : phase.getAtoms())
            atomsModel.append(atom);

        atomsTable = new JTable(atomsModel);
        atomsTable.setName("atoms");
        JScrollPane scrollPane = new JScrollPane(atomsTable);
        scrollPane.setPreferredSize(new Dimension(250, 150));
        atomsTable.setFillsViewportHeight(true);
        atomsPanel.add(scrollPane, "grow, push, wrap");

        JButton atomsAddButton = new JButton(addIcon);
        atomsAddButton.setName("atoms add");
        atomsAddButton.setMargin(new Insets(1, 1, 1, 1));
        atomsAddButton.addActionListener(new AtomsAddButtonActionListener());
        atomsPanel.add(atomsAddButton, "split 4, align right");

        JButton atomsRemoveButton = new JButton(removeIcon);
        atomsRemoveButton.setName("atoms remove");
        atomsRemoveButton.setMargin(new Insets(1, 1, 1, 1));
        atomsRemoveButton.addActionListener(new AtomsRemoveButtonActionListener());
        atomsPanel.add(atomsRemoveButton);

        JButton atomsClearButton = new JButton(clearIcon);
        atomsClearButton.setName("atoms clear");
        atomsClearButton.setMargin(new Insets(1, 1, 1, 1));
        atomsClearButton.addActionListener(new AtomsClearButtonActionListener());
        atomsPanel.add(atomsClearButton);

        JButton atomsCalcButton = new JButton(calcIcon);
        atomsCalcButton.setName("atoms calculate");
        atomsCalcButton.setMargin(new Insets(1, 1, 1, 1));
        atomsCalcButton.addActionListener(new AtomsCalcButtonActionListener());
        atomsPanel.add(atomsCalcButton);

        // Reflectors
        JPanel reflectorsPanel = new JPanel(new MigLayout());
        reflectorsPanel.setBorder(new TitledBorder("Reflectors"));
        mainPanel.add(reflectorsPanel, "grow, push, wrap");

        ReflectorsTableModel reflsModel = new ReflectorsTableModel();
        for (Reflector refl : phase.getReflectors())
            reflsModel.append(refl);

        reflectorsTable = new JTable(reflsModel);
        reflectorsTable.setName("reflectors");
        scrollPane = new JScrollPane(reflectorsTable);
        scrollPane.setPreferredSize(new Dimension(250, 150));
        reflectorsTable.setFillsViewportHeight(true);
        reflectorsPanel.add(scrollPane, "grow, push, wrap");

        JButton reflectorsAddButton = new JButton(addIcon);
        reflectorsAddButton.setName("reflectors add");
        reflectorsAddButton.setMargin(new Insets(1, 1, 1, 1));
        reflectorsAddButton.addActionListener(new ReflectorsAddButtonActionListener());
        reflectorsPanel.add(reflectorsAddButton, "split 4, align left");

        JButton reflectorsRemoveButton = new JButton(removeIcon);
        reflectorsRemoveButton.setName("reflectors remove");
        reflectorsRemoveButton.setMargin(new Insets(1, 1, 1, 1));
        reflectorsRemoveButton.addActionListener(new ReflectorsRemoveButtonActionListener());
        reflectorsPanel.add(reflectorsRemoveButton);

        JButton reflectorsClearButton = new JButton(clearIcon);
        reflectorsClearButton.setName("reflectors clear");
        reflectorsClearButton.setMargin(new Insets(1, 1, 1, 1));
        reflectorsClearButton.addActionListener(new ReflectorsClearButtonActionListener());
        reflectorsPanel.add(reflectorsClearButton);

        JButton reflectorsCalcButton = new JButton(calcIcon);
        reflectorsCalcButton.setName("reflectors calculate");
        reflectorsCalcButton.setMargin(new Insets(1, 1, 1, 1));
        reflectorsCalcButton.addActionListener(new ReflectorsCalcButtonActionListener());
        reflectorsPanel.add(reflectorsCalcButton);
    }



    @Override
    protected JComponent getMainComponent() {
        return mainPanel;
    }



    /**
     * Returns the phase defined in this dialog. This method should be called
     * after the dialog is closed.
     * 
     * @return phase
     */
    public Phase getPhase() {
        String name = nameField.getText();
        String citation = citationField.getText();
        SpaceGroup sg = (SpaceGroup) spaceGroupComboBox.getSelectedItem();
        CrystalSystem cs =
                (CrystalSystem) crystalSystemComboBox.getSelectedItem();
        UnitCell unitCell = unitCellPanel.getUnitCell(cs);
        AtomSites atoms =
                ((AtomsTableModel) atomsTable.getModel()).getAtomSites();
        Reflectors refls =
                ((ReflectorsTableModel) reflectorsTable.getModel()).getReflectors();

        Phase phase = new Phase(name, citation, sg, unitCell);

        phase.getAtoms().clear();
        phase.getAtoms().addAll(atoms);

        phase.getReflectors().clear();
        phase.getReflectors().addAll(refls);

        return phase;
    }



    @Override
    protected boolean isCorrect() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(getDialogWindow(), "Empty name",
                    "Invalid name", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (spaceGroupComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(getDialogWindow(),
                    "Please select a space group", "Invalid space group",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (crystalSystemComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(getDialogWindow(),
                    "Please select a crystal system", "Invalid crystal system",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        CrystalSystem cs =
                (CrystalSystem) crystalSystemComboBox.getSelectedItem();
        if (!unitCellPanel.isCorrect(cs))
            return false;

        return true;
    }



    /**
     * Refreshes the display of the dialog based on the crystal symmetry combo
     * box selection.
     */
    private void refreshLaueGroup() {
        if (crystalSystemComboBox.getSelectedItem() == null)
            return;

        LaueGroup tmpLaueGroup =
                (LaueGroup) laueGroupComboBox.getSelectedItem();

        List<LaueGroup> lgs = new ArrayList<LaueGroup>();
        lgs.addAll(LaueGroup.list((CrystalSystem) crystalSystemComboBox.getSelectedItem()));
        Collections.sort(lgs);

        laueGroupComboBox.removeAllItems();

        for (LaueGroup lg : lgs)
            laueGroupComboBox.addItem(lg);

        if (lgs.contains(tmpLaueGroup))
            laueGroupComboBox.setSelectedItem(tmpLaueGroup);
        else
            laueGroupComboBox.setSelectedItem(lgs.get(0));

        refreshSpaceGroup();
    }



    /**
     * Refreshes the space group combo box.
     */
    private void refreshSpaceGroup() {
        if (laueGroupComboBox.getSelectedItem() == null)
            return;

        SpaceGroup tmpSpaceGroup =
                (SpaceGroup) spaceGroupComboBox.getSelectedItem();

        List<SpaceGroup> sgs = new ArrayList<SpaceGroup>();
        sgs.addAll(SpaceGroups.list((LaueGroup) laueGroupComboBox.getSelectedItem()));
        Collections.sort(sgs);

        spaceGroupComboBox.removeAllItems();

        for (SpaceGroup sg : sgs)
            spaceGroupComboBox.addItem(sg);

        if (sgs.contains(tmpSpaceGroup))
            spaceGroupComboBox.setSelectedItem(tmpSpaceGroup);
        else
            spaceGroupComboBox.setSelectedItem(sgs.get(0));
    }



    /**
     * Refreshes the unit cell panel.
     */
    private void refreshUnitCell() {
        if (crystalSystemComboBox.getSelectedItem() == null)
            return;

        CrystalSystem cs =
                (CrystalSystem) crystalSystemComboBox.getSelectedItem();
        unitCellPanel.refresh(cs);
    }

}
