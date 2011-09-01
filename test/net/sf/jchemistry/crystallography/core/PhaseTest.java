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
package net.sf.jchemistry.crystallography.core;

import net.sf.jchemistry.core.Element;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhaseTest {

    private String name;

    private String citation;

    private Phase phase;

    private UnitCell unitCell;

    private AtomSites atoms;

    private SpaceGroup sg;



    @Before
    public void setUp() throws Exception {
        name = "silicon";
        citation = "Reference 1";
        unitCell = UnitCellFactory.cubic(2.0);
        atoms = AtomSitesFactory.atomSitesFCC(Element.Si);
        sg = SpaceGroups2.SG216;

        phase = new Phase(name, citation, sg, unitCell);
        phase.getAtoms().addAll(atoms);
    }



    @Test
    public void testPhase() {
        assertEquals(2.0, phase.getUnitCell().getA(), 1e-7);
        assertEquals(4, phase.getAtoms().size());
        assertEquals(name, phase.getName());
        assertEquals(citation, phase.getCitation());
    }



    @Test(expected = NullPointerException.class)
    public void testPhaseException1() {
        new Phase(null, citation, sg, unitCell);
    }



    @Test(expected = NullPointerException.class)
    public void testPhaseException2() {
        new Phase(name, null, sg, unitCell);
    }



    @Test(expected = NullPointerException.class)
    public void testPhaseException3() {
        new Phase(name, citation, null, unitCell);
    }



    @Test(expected = NullPointerException.class)
    public void testPhaseException4() {
        new Phase(name, citation, sg, null);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testPhaseException5() {
        new Phase("", citation, sg, unitCell);
    }



    @Test
    public void testToString() {
        assertEquals("silicon", phase.toString());
    }



    @Test
    public void testGetAtoms1() {
        phase.getAtoms().clear();
        assertEquals(0, phase.getAtoms().size());

        phase.getAtoms().add(new AtomSite(Element.Si, Vector3D.ZERO));
        assertEquals(4, phase.getAtoms().size());
    }



    @Test
    public void testGetAtoms2() {
        AtomSites atoms = phase.getAtoms();

        atoms.clear();
        assertEquals(0, phase.getAtoms().size());

        atoms.add(new AtomSite(Element.Si, Vector3D.ZERO));
        assertEquals(4, phase.getAtoms().size());
    }

}
