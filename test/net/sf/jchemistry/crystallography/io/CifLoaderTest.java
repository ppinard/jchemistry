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
package net.sf.jchemistry.crystallography.io;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.crystallography.core.AtomSite;
import net.sf.jchemistry.crystallography.core.AtomSites;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflectors;
import net.sf.jchemistry.crystallography.core.SpaceGroups;
import net.sf.jchemistry.crystallography.core.SpaceGroups1;
import net.sf.jchemistry.util.IOUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CifLoaderTest {

    private Reader reader1;

    private Reader reader2;

    private CifLoader loader;



    @Before
    public void setUp() throws Exception {
        reader1 =
                IOUtils.getReader("net/sf/jchemistry/crystallography/testdata/forsterite.cif");
        reader2 =
                IOUtils.getReader("net/sf/jchemistry/crystallography/testdata/ga2o3.cif");
        loader = new CifLoader();
    }



    @Test
    public void testLoad1() throws IOException {
        Phase phase = loader.load(reader1);

        // Name
        assertEquals("Forsterite", phase.getName());

        // Reference
        String expected =
                "Smyth, J. R., Hazen, R. M. (1973), "
                        + "The crystal structures of forsterite and hortonolite at "
                        + "several temperatures  up to 900 C  T = 25 C, "
                        + "American Mineralogist, 58, 588-593";
        assertEquals(expected, phase.getCitation());

        // Space group
        assertEquals(SpaceGroups1.SG62, phase.getSpaceGroup());

        // Unit cell
        assertEquals(4.756, phase.getUnitCell().getA(), 1e-3);
        assertEquals(10.207, phase.getUnitCell().getB(), 1e-3);
        assertEquals(5.980, phase.getUnitCell().getC(), 1e-3);
        assertEquals(90, Math.toDegrees(phase.getUnitCell().getAlpha()), 1e-3);
        assertEquals(91, Math.toDegrees(phase.getUnitCell().getBeta()), 1e-3);
        assertEquals(92, Math.toDegrees(phase.getUnitCell().getGamma()), 1e-3);

        // Atoms
        AtomSites atoms = phase.getAtoms();
        assertEquals(164, atoms.size());

        Map<Element, Integer> elementMap = createElementCountMap(atoms);
        assertEquals(36, (int) elementMap.get(Element.Mg)); // Mg
        assertEquals(32, (int) elementMap.get(Element.Si)); // Si
        assertEquals(96, (int) elementMap.get(Element.O)); // O

        // Reflectors
        Reflectors refls = phase.getReflectors();
        assertEquals(3, refls.size());

        assertTrue(refls.contains(1, 2, 3));
        assertEquals(1.0, refls.get(1, 2, 3).getIntensity(), 1e-6);

        assertTrue(refls.contains(1, 2, 4));
        assertEquals(0.5, refls.get(1, 2, 4).getIntensity(), 1e-6);

        assertTrue(refls.contains(1, 3, 5));
        assertEquals(0.1, refls.get(1, 3, 5).getIntensity(), 1e-6);
    }



    @Test
    public void testLoad2() throws IOException {
        Phase phase = loader.load(reader2);

        // Name
        assertEquals("Gallium Oxide - Beta", phase.getName());

        // Reference
        String expected =
                "Litimein, F.;Rached, D.;Khenata, R.;Baltache,H. (2010), "
                        + "FPLAPW study of the structural, electronic, and optical "
                        + "properties of  Ga2 O3: monoclinic and hexagonal phases, "
                        + "Journal of Alloys Compd., 488, 148-156";
        assertEquals(expected, phase.getCitation());

        // Space group
        assertEquals(SpaceGroups.SG12, phase.getSpaceGroup());

        // Unit cell
        assertEquals(12.208, phase.getUnitCell().getA(), 1e-3);
        assertEquals(3.031, phase.getUnitCell().getB(), 1e-3);
        assertEquals(5.751, phase.getUnitCell().getC(), 1e-3);
        assertEquals(90, Math.toDegrees(phase.getUnitCell().getAlpha()), 1e-3);
        assertEquals(103.63, Math.toDegrees(phase.getUnitCell().getBeta()),
                1e-3);
        assertEquals(90, Math.toDegrees(phase.getUnitCell().getGamma()), 1e-3);

        // Atoms
        AtomSites atoms = phase.getAtoms();
        assertEquals(20, atoms.size());

        Map<Element, Integer> elementMap = createElementCountMap(atoms);
        assertEquals(8, (int) elementMap.get(Element.Ga)); // Ga3+
        assertEquals(12, (int) elementMap.get(Element.O)); // O

        // Reflectors
        assertEquals(0, phase.getReflectors().size());
    }



    private Map<Element, Integer> createElementCountMap(AtomSites atoms) {
        HashMap<Element, Integer> map = new HashMap<Element, Integer>();

        Element element;
        for (AtomSite atom : atoms) {
            element = atom.getElement();

            if (map.containsKey(element))
                map.put(element, map.get(element) + 1);
            else
                map.put(element, 1);
        }

        return map;
    }
}
