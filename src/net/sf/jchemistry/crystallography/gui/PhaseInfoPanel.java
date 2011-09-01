package net.sf.jchemistry.crystallography.gui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.crystallography.core.AtomSite;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflector;
import net.sf.jchemistry.crystallography.core.SpaceGroup;

/**
 * Panel to display phase information.
 * 
 * @author ppinard
 */
public class PhaseInfoPanel extends JPanel {

    /** Serial version UID. */
    private static final long serialVersionUID = -4435029123838264167L;

    /** Field for name. */
    private final JTextField nameField;

    /** Field for citation. */
    private final JTextField citationField;

    /** Combo box for crystal system. */
    private final JTextField crystalSystemField;

    /** Combo box for Laue group. */
    private final JTextField laueGroupField;

    /** Combo box for space group. */
    private final JTextField spaceGroupField;

    /** Panel for the unit cell fields. */
    private final UnitCellPanel unitCellPanel;

    /** Table for atom positions. */
    private final JTable atomsTable;

    /** Table for reflectors. */
    private final JTable reflectorsTable;



    /**
     * Creates a new <code>PhaseInfoPanel</code>.
     * 
     * @param phase
     *            phase to display
     */
    public PhaseInfoPanel(Phase phase) {
        setLayout(new MigLayout());

        // Name
        add(new JLabel("Name"), "split 4");
        nameField = new JTextField(20);
        nameField.setName("name");
        nameField.setToolTipText("Mineral or common name");
        nameField.setEditable(false);
        add(nameField, "growx, pushx");

        nameField.setText(phase.getName());

        // Reference
        add(new JLabel("Citation"), "split 2");
        citationField = new JTextField(30);
        citationField.setName("citation");
        citationField.setToolTipText("Reference to journal or book");
        citationField.setEditable(false);
        add(citationField, "growx, pushx, wrap");

        citationField.setText(phase.getCitation());

        // Crystal system
        SpaceGroup sg = phase.getSpaceGroup();

        add(new JLabel("Crystal system"), "split 6");
        crystalSystemField = new JTextField(10);
        crystalSystemField.setName("crystal system");
        crystalSystemField.setEditable(false);
        add(crystalSystemField, "growx, pushx");

        crystalSystemField.setText(sg.getCrystalSystem().toString());

        // Laue group
        add(new JLabel("Laue group"));
        laueGroupField = new JTextField(10);
        laueGroupField.setName("Laue group");
        laueGroupField.setEditable(false);
        add(laueGroupField, "growx, pushx");

        laueGroupField.setText(sg.getLaueGroup().toString());

        // Space group
        add(new JLabel("Space group"));
        spaceGroupField = new JTextField(10);
        spaceGroupField.setName("space group");
        spaceGroupField.setEditable(false);
        add(spaceGroupField, "growx, pushx, wrap");

        spaceGroupField.setText(sg.getSymbol() + " (" + sg.getIndex() + ")");

        // Unit cell
        unitCellPanel = new UnitCellPanel(phase.getUnitCell());
        unitCellPanel.setName("unit cell");
        unitCellPanel.setBorder(new TitledBorder("Unit Cell"));
        unitCellPanel.setEditable(false);
        add(unitCellPanel, "growx, pushx, wrap");

        unitCellPanel.refresh(sg.getCrystalSystem());

        // Atoms
        JPanel atomsPanel = new JPanel(new MigLayout());
        atomsPanel.setBorder(new TitledBorder("Atoms"));
        add(atomsPanel, "grow, push, split 2");

        AtomsTableModel atomsModel = new AtomsTableModel();
        for (AtomSite atom : phase.getAtoms())
            atomsModel.append(atom);

        atomsTable = new JTable(atomsModel);
        atomsTable.setName("atoms");
        JScrollPane scrollPane = new JScrollPane(atomsTable);
        scrollPane.setPreferredSize(new Dimension(250, 150));
        atomsTable.setFillsViewportHeight(true);
        atomsPanel.add(scrollPane, "grow, push, wrap");

        // Reflectors
        JPanel reflectorsPanel = new JPanel(new MigLayout());
        reflectorsPanel.setBorder(new TitledBorder("Reflectors"));
        add(reflectorsPanel, "grow, push, wrap");

        ReflectorsTableModel reflsModel = new ReflectorsTableModel();
        for (Reflector refl : phase.getReflectors())
            reflsModel.append(refl);

        reflectorsTable = new JTable(reflsModel);
        reflectorsTable.setName("reflectors");
        scrollPane = new JScrollPane(reflectorsTable);
        scrollPane.setPreferredSize(new Dimension(250, 150));
        reflectorsTable.setFillsViewportHeight(true);
        reflectorsPanel.add(scrollPane, "grow, push, wrap");
    }

}
