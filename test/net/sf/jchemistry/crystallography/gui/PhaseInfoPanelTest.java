package net.sf.jchemistry.crystallography.gui;

import net.sf.jchemistry.crystallography.core.AtomSites;
import net.sf.jchemistry.crystallography.core.CrystalSystem;
import net.sf.jchemistry.crystallography.core.LaueGroup;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflectors;
import net.sf.jchemistry.crystallography.core.SpaceGroup;
import net.sf.jchemistry.crystallography.test.PhaseFactory;

import org.junit.Before;
import org.uispec4j.Panel;
import org.uispec4j.TextBox;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.finder.ComponentMatchers;

public class PhaseInfoPanelTest extends UISpecTestCase {

    private Phase phase;

    private PhaseInfoPanel phaseInfoPanel;

    private Panel uiPanel;



    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        phase = PhaseFactory.kryptonite();
        phaseInfoPanel = new PhaseInfoPanel(phase);
        uiPanel = new Panel(phaseInfoPanel);
    }



    private static TextBox getTextBox(Panel panel, String innerName) {
        return panel.getTextBox(ComponentMatchers.innerNameIdentity(innerName));
    }



    public void testPhaseInfoPanel() {
        testPhaseInfoPanel(uiPanel, phase);
    }



    public static void testPhaseInfoPanel(Panel panel, Phase phase) {
        assertEquals(phase.getName(), getTextBox(panel, "name").getText());
        assertEquals(phase.getCitation(),
                getTextBox(panel, "citation").getText());

        // Crystal system
        CrystalSystem cs = phase.getSpaceGroup().getCrystalSystem();
        assertEquals(cs.toString(),
                getTextBox(panel, "crystal system").getText());

        // Laue group
        LaueGroup lg = phase.getSpaceGroup().getLaueGroup();
        assertEquals(lg.toString(), getTextBox(panel, "Laue group").getText());

        // Space group
        SpaceGroup sg = phase.getSpaceGroup();
        String expected = sg.toString() + " (" + sg.getIndex() + ")";
        assertEquals(expected, getTextBox(panel, "space group").getText());

        // Unit cell
        UnitCellPanelTest.testUnitCellPanel(panel, phase.getUnitCell());

        // Atoms
        AtomSites atoms = phase.getAtoms();
        assertEquals(atoms.size(), panel.getTable("atoms").getRowCount());

        // Reflectors
        Reflectors refls = phase.getReflectors();
        assertEquals(refls.size(), panel.getTable("reflectors").getRowCount());
    }
}
