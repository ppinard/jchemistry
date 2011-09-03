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

import net.sf.jchemistry.crystallography.core.CrystalSystem;
import net.sf.jchemistry.crystallography.core.UnitCell;
import net.sf.jchemistry.crystallography.test.PhaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Panel;
import org.uispec4j.UISpecTestCase;

import static net.sf.jchemistry.test.UISpecUtils.getTextBox;

public class UnitCellPanelTest extends UISpecTestCase {

    public static void testUnitCellPanel(Panel panel, UnitCell unitCell) {
        double actual = Double.parseDouble(getTextBox(panel, "a").getText());
        assertEquals(unitCell.getA(), actual, 1e-3);

        actual = Double.parseDouble(getTextBox(panel, "b").getText());
        assertEquals(unitCell.getB(), actual, 1e-3);

        actual = Double.parseDouble(getTextBox(panel, "c").getText());
        assertEquals(unitCell.getC(), actual, 1e-3);

        actual = Double.parseDouble(getTextBox(panel, "alpha").getText());
        assertEquals(unitCell.getAlpha(), Math.toRadians(actual), 1e-3);

        actual = Double.parseDouble(getTextBox(panel, "beta").getText());
        assertEquals(unitCell.getBeta(), Math.toRadians(actual), 1e-3);

        actual = Double.parseDouble(getTextBox(panel, "gamma").getText());
        assertEquals(unitCell.getGamma(), Math.toRadians(actual), 1e-3);
    }

    private UnitCell unitCell;

    private UnitCellPanel unitCellPanel;

    private Panel uiPanel;



    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        unitCell = PhaseFactory.kryptonite().getUnitCell();
        unitCellPanel = new UnitCellPanel(unitCell);
        uiPanel = new Panel(unitCellPanel);
    }



    @Test
    public void testRefreshCubic() {
        unitCellPanel.refresh(CrystalSystem.CUBIC);

        assertTrue(getTextBox(uiPanel, "a").isEnabled());
        assertFalse(getTextBox(uiPanel, "b").isEnabled());
        assertFalse(getTextBox(uiPanel, "c").isEnabled());
        assertFalse(getTextBox(uiPanel, "alpha").isEnabled());
        assertFalse(getTextBox(uiPanel, "beta").isEnabled());
        assertFalse(getTextBox(uiPanel, "gamma").isEnabled());

        double actual =
                Double.parseDouble(getTextBox(uiPanel, "alpha").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "beta").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "gamma").getText());
        assertEquals(90.0, actual, 1e-3);
    }



    @Test
    public void testRefreshHexagonal() {
        unitCellPanel.refresh(CrystalSystem.HEXAGONAL);

        assertTrue(getTextBox(uiPanel, "a").isEnabled());
        assertFalse(getTextBox(uiPanel, "b").isEnabled());
        assertTrue(getTextBox(uiPanel, "c").isEnabled());
        assertFalse(getTextBox(uiPanel, "alpha").isEnabled());
        assertFalse(getTextBox(uiPanel, "beta").isEnabled());
        assertFalse(getTextBox(uiPanel, "gamma").isEnabled());

        double actual =
                Double.parseDouble(getTextBox(uiPanel, "alpha").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "beta").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "gamma").getText());
        assertEquals(120.0, actual, 1e-3);
    }



    public void testRefreshMonoclinic() {
        unitCellPanel.refresh(CrystalSystem.MONOCLINIC);

        assertTrue(getTextBox(uiPanel, "a").isEnabled());
        assertTrue(getTextBox(uiPanel, "b").isEnabled());
        assertTrue(getTextBox(uiPanel, "c").isEnabled());
        assertFalse(getTextBox(uiPanel, "alpha").isEnabled());
        assertTrue(getTextBox(uiPanel, "beta").isEnabled());
        assertFalse(getTextBox(uiPanel, "gamma").isEnabled());

        double actual =
                Double.parseDouble(getTextBox(uiPanel, "alpha").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "gamma").getText());
        assertEquals(90.0, actual, 1e-3);
    }



    public void testRefreshOrthorhombic() {
        unitCellPanel.refresh(CrystalSystem.ORTHORHOMBIC);

        assertTrue(getTextBox(uiPanel, "a").isEnabled());
        assertTrue(getTextBox(uiPanel, "b").isEnabled());
        assertTrue(getTextBox(uiPanel, "c").isEnabled());
        assertFalse(getTextBox(uiPanel, "alpha").isEnabled());
        assertFalse(getTextBox(uiPanel, "beta").isEnabled());
        assertFalse(getTextBox(uiPanel, "gamma").isEnabled());

        double actual =
                Double.parseDouble(getTextBox(uiPanel, "alpha").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "beta").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "gamma").getText());
        assertEquals(90.0, actual, 1e-3);
    }



    @Test
    public void testRefreshTetragonal() {
        unitCellPanel.refresh(CrystalSystem.TETRAGONAL);

        assertTrue(getTextBox(uiPanel, "a").isEnabled());
        assertFalse(getTextBox(uiPanel, "b").isEnabled());
        assertTrue(getTextBox(uiPanel, "c").isEnabled());
        assertFalse(getTextBox(uiPanel, "alpha").isEnabled());
        assertFalse(getTextBox(uiPanel, "beta").isEnabled());
        assertFalse(getTextBox(uiPanel, "gamma").isEnabled());

        double actual =
                Double.parseDouble(getTextBox(uiPanel, "alpha").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "beta").getText());
        assertEquals(90.0, actual, 1e-3);

        actual = Double.parseDouble(getTextBox(uiPanel, "gamma").getText());
        assertEquals(90.0, actual, 1e-3);
    }



    public void testRefreshTriclinic() {
        unitCellPanel.refresh(CrystalSystem.TRICLINIC);

        assertTrue(getTextBox(uiPanel, "a").isEnabled());
        assertTrue(getTextBox(uiPanel, "b").isEnabled());
        assertTrue(getTextBox(uiPanel, "c").isEnabled());
        assertTrue(getTextBox(uiPanel, "alpha").isEnabled());
        assertTrue(getTextBox(uiPanel, "beta").isEnabled());
        assertTrue(getTextBox(uiPanel, "gamma").isEnabled());
    }



    public void testRefreshTrigonal() {
        unitCellPanel.refresh(CrystalSystem.TRIGONAL);

        assertTrue(getTextBox(uiPanel, "a").isEnabled());
        assertFalse(getTextBox(uiPanel, "b").isEnabled());
        assertFalse(getTextBox(uiPanel, "c").isEnabled());
        assertTrue(getTextBox(uiPanel, "alpha").isEnabled());
        assertFalse(getTextBox(uiPanel, "beta").isEnabled());
        assertFalse(getTextBox(uiPanel, "gamma").isEnabled());
    }



    @Test
    public void testSetEditable() {
        unitCellPanel.setEditable(false);

        assertFalse(getTextBox(uiPanel, "a").isEditable());
        assertFalse(getTextBox(uiPanel, "b").isEditable());
        assertFalse(getTextBox(uiPanel, "c").isEditable());
        assertFalse(getTextBox(uiPanel, "alpha").isEditable());
        assertFalse(getTextBox(uiPanel, "beta").isEditable());
        assertFalse(getTextBox(uiPanel, "gamma").isEditable());
    }



    @Test
    public void testUnitCellPanel() {
        testUnitCellPanel(uiPanel, unitCell);
    }

}
