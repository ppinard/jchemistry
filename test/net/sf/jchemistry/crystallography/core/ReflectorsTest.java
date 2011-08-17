/*
 * jChemistry
 * Copyright (C) 2011 Philippe T. Pinard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jchemistry.crystallography.core;

import java.util.ArrayList;
import java.util.Iterator;

import net.sf.jchemistry.core.Element;

import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReflectorsTest {

    private Reflectors refls;



    @Before
    public void setUp() throws Exception {
        refls = new Reflectors();
        refls.add(new Reflector(1, 1, 1, 0.5));
    }



    @Test(expected = IllegalArgumentException.class)
    public void testGetException() {
        refls.get(0, 1, 1);
    }



    @Test
    public void testGenerateBCC() {
        // Create reflectors
        UnitCell unitCell = UnitCellFactory.cubic(2.87);
        AtomSites atoms = AtomSitesFactory.atomSitesBCC(Element.Al);

        Phase phase = new Phase("BCC", SpaceGroups2.SG229, unitCell);
        phase.getAtoms().addAll(atoms);

        Reflectors refls =
                Reflectors.generate(phase,
                        ScatteringFactorsFactory.ELECTRON_MOTT_BETHE, 2, 1e-14);

        // Expected indices
        ArrayList<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[] { 0, 1, 1 });
        expected.add(new Integer[] { 1, 1, 0 });
        expected.add(new Integer[] { 1, 0, 1 });
        expected.add(new Integer[] { 1, -1, 0 });
        expected.add(new Integer[] { 1, 0, -1 });
        expected.add(new Integer[] { 0, 1, -1 });
        expected.add(new Integer[] { 0, 2, 0 });
        expected.add(new Integer[] { 2, 0, 0 });
        expected.add(new Integer[] { 0, 0, 2 });
        expected.add(new Integer[] { 1, 1, 2 });
        expected.add(new Integer[] { 1, 2, 1 });
        expected.add(new Integer[] { 2, 1, 1 });
        expected.add(new Integer[] { 1, -2, -1 });
        expected.add(new Integer[] { 2, 1, -1 });
        expected.add(new Integer[] { 2, -1, -1 });
        expected.add(new Integer[] { 1, 1, -2 });
        expected.add(new Integer[] { 1, -1, -2 });
        expected.add(new Integer[] { 1, 2, -1 });
        expected.add(new Integer[] { 2, -1, 1 });
        expected.add(new Integer[] { 1, -2, 1 });
        expected.add(new Integer[] { 1, -1, 2 });
        expected.add(new Integer[] { 0, 2, 2 });
        expected.add(new Integer[] { 2, 0, 2 });
        expected.add(new Integer[] { 2, 2, 0 });
        expected.add(new Integer[] { 2, -2, 0 });
        expected.add(new Integer[] { 2, 0, -2 });
        expected.add(new Integer[] { 0, 2, -2 });
        expected.add(new Integer[] { 2, 2, 2 });
        expected.add(new Integer[] { 2, -2, -2 });
        expected.add(new Integer[] { 2, -2, 2 });
        expected.add(new Integer[] { 2, 2, -2 });

        // Check count
        assertEquals(expected.size(), refls.size());

        // Check indices
        for (Integer[] plane : expected) {
            assertTrue(refls.contains(plane[0], plane[1], plane[2]));
        }

        // Check indices rule(s)
        for (Reflector refl : refls) {
            int modulo = ((refl.getH() + refl.getK() + refl.getL()) % 2);
            assertEquals(0, modulo);
        }
    }



    @Test
    public void testGenerateFCC() {
        // Create reflectors
        UnitCell unitCell = UnitCellFactory.cubic(5.43);
        AtomSites atoms = AtomSitesFactory.atomSitesFCC(Element.Al);

        Phase phase = new Phase("FCC", SpaceGroups2.SG216, unitCell);
        phase.getAtoms().addAll(atoms);

        Reflectors refls =
                Reflectors.generate(phase,
                        ScatteringFactorsFactory.ELECTRON_MOTT_BETHE, 2, 0.01);

        // Expected indices
        ArrayList<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[] { 1, 1, 1 });
        expected.add(new Integer[] { 1, -1, -1 });
        expected.add(new Integer[] { 1, 1, -1 });
        expected.add(new Integer[] { 1, -1, 1 });
        expected.add(new Integer[] { 0, 2, 0 });
        expected.add(new Integer[] { 0, 0, 2 });
        expected.add(new Integer[] { 2, 0, 0 });
        expected.add(new Integer[] { 0, 2, 2 });
        expected.add(new Integer[] { 2, 0, 2 });
        expected.add(new Integer[] { 2, 2, 0 });
        expected.add(new Integer[] { 0, 2, -2 });
        expected.add(new Integer[] { 2, 0, -2 });
        expected.add(new Integer[] { 2, -2, 0 });
        expected.add(new Integer[] { 2, 2, 2 });
        expected.add(new Integer[] { 2, -2, 2 });
        expected.add(new Integer[] { 2, -2, -2 });
        expected.add(new Integer[] { 2, 2, -2 });

        // Check count
        assertEquals(expected.size(), refls.size());

        // Check indices
        for (Integer[] plane : expected) {
            assertTrue(refls.contains(plane[0], plane[1], plane[2]));
        }

        // Check indices rule(s)
        for (Reflector refl : refls) {
            if (refl.getH() % 2 == 0) {
                assertEquals(refl.getK() % 2, 0, 1e-7);
                assertEquals(refl.getL() % 2, 0, 1e-7);
            } else {// plane[0] % 2 == 1
                assertEquals(abs(refl.getK() % 2), 1, 1e-7);
                assertEquals(abs(refl.getL() % 2), 1, 1e-7);
            }
        }
    }



    @Test
    public void testGenerateHCP() {
        // Create reflectors
        UnitCell unitCell = UnitCellFactory.hexagonal(3.21, 5.21);
        AtomSites atoms = AtomSitesFactory.atomSitesHCP(Element.Al);

        Phase phase = new Phase("HCP", SpaceGroups2.SG168, unitCell);
        phase.getAtoms().addAll(atoms);

        ScatteringFactors scatter =
                ScatteringFactorsFactory.ELECTRON_MOTT_BETHE;
        Reflectors refls = Reflectors.generate(phase, scatter, 2, 0.01);

        // From Rollett 2008
        boolean condition1, condition2;
        for (Reflector refl : refls) {
            condition1 = abs((refl.getH() + 2 * refl.getK()) % 3) < 0;
            condition2 = abs(refl.getL() % 2) == 1;
            assertFalse(condition1 && condition2);
        }
    }



    @Test
    public void testIntensityIterator() {
        refls.add(new Reflector(1, 2, 3, 1.0));
        refls.add(new Reflector(4, 5, 6, 0.1));

        Iterator<Reflector> it = refls.intensityIterator(true);

        Reflector refl = it.next();
        assertEquals(1, refl.getH());
        assertEquals(2, refl.getK());
        assertEquals(3, refl.getL());
        assertEquals(1.0, refl.getIntensity(), 1e-7);

        refl = it.next();
        assertEquals(1, refl.getH());
        assertEquals(1, refl.getK());
        assertEquals(1, refl.getL());
        assertEquals(0.5, refl.getIntensity(), 1e-7);

        refl = it.next();
        assertEquals(4, refl.getH());
        assertEquals(5, refl.getK());
        assertEquals(6, refl.getL());
        assertEquals(0.1, refl.getIntensity(), 1e-7);
    }



    @Test
    public void testAdd() {
        assertTrue(refls.add(new Reflector(1, 2, 3, 0.5)));
        assertFalse(refls.add(new Reflector(1, 1, 1, 0.5)));
    }



    @Test
    public void testContains() {
        assertTrue(refls.contains(1, 1, 1));
        assertFalse(refls.contains(1, 2, 3));
    }



    @Test
    public void testGet() {
        Reflector refl = refls.get(1, 1, 1);
        assertEquals(1, refl.getH());
        assertEquals(1, refl.getK());
        assertEquals(1, refl.getL());
    }



    @Test
    public void testRemove() {
        assertTrue(refls.remove(1, 1, 1));
        assertEquals(0, refls.size());
        assertFalse(refls.remove(1, 1, 1));
    }



    @Test
    public void testSize() {
        assertEquals(1, refls.size());
    }

}
