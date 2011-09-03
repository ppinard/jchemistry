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
