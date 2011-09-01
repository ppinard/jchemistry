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

import java.util.Set;

import org.apache.commons.math.geometry.Rotation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LaueGroupTest {

    private void allOperationsUnique(Rotation[] ops) {
        for (Rotation op1 : ops) {
            int count = 0;

            for (Rotation op2 : ops)
                if (op1.equals(op2))
                    count += 1;

            if (count > 1)
                fail("Operation (" + op1 + ") is duplicated.");
        }
    }



    @Test
    public void testFromIndex() {
        assertEquals(LaueGroup.LG1, LaueGroup.fromIndex(1));
        assertEquals(LaueGroup.LG2m, LaueGroup.fromIndex(2));
        assertEquals(LaueGroup.LGmmm, LaueGroup.fromIndex(3));
        assertEquals(LaueGroup.LG3, LaueGroup.fromIndex(4));
        assertEquals(LaueGroup.LG3m, LaueGroup.fromIndex(5));
        assertEquals(LaueGroup.LG4m, LaueGroup.fromIndex(6));
        assertEquals(LaueGroup.LG4mmm, LaueGroup.fromIndex(7));
        assertEquals(LaueGroup.LG6m, LaueGroup.fromIndex(8));
        assertEquals(LaueGroup.LG6mmm, LaueGroup.fromIndex(9));
        assertEquals(LaueGroup.LGm3, LaueGroup.fromIndex(10));
        assertEquals(LaueGroup.LGm3m, LaueGroup.fromIndex(11));
    }



    @Test(expected = IllegalArgumentException.class)
    public void testFromIndexException1() {
        LaueGroup.fromIndex(0);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testFromIndexException2() {
        LaueGroup.fromIndex(12);
    }



    @Test
    public void testList() {
        Set<LaueGroup> lgs;

        lgs = LaueGroup.list(CrystalSystem.TRICLINIC);
        assertEquals(1, lgs.size());
        assertTrue(lgs.contains(LaueGroup.LG1));

        lgs = LaueGroup.list(CrystalSystem.MONOCLINIC);
        assertEquals(1, lgs.size());
        assertTrue(lgs.contains(LaueGroup.LG2m));

        lgs = LaueGroup.list(CrystalSystem.ORTHORHOMBIC);
        assertEquals(1, lgs.size());
        assertTrue(lgs.contains(LaueGroup.LGmmm));

        lgs = LaueGroup.list(CrystalSystem.TRIGONAL);
        assertEquals(2, lgs.size());
        assertTrue(lgs.contains(LaueGroup.LG3));
        assertTrue(lgs.contains(LaueGroup.LG3m));

        lgs = LaueGroup.list(CrystalSystem.TETRAGONAL);
        assertEquals(2, lgs.size());
        assertTrue(lgs.contains(LaueGroup.LG4m));
        assertTrue(lgs.contains(LaueGroup.LG4mmm));

        lgs = LaueGroup.list(CrystalSystem.HEXAGONAL);
        assertEquals(2, lgs.size());
        assertTrue(lgs.contains(LaueGroup.LG6m));
        assertTrue(lgs.contains(LaueGroup.LG6mmm));

        lgs = LaueGroup.list(CrystalSystem.CUBIC);
        assertEquals(2, lgs.size());
        assertTrue(lgs.contains(LaueGroup.LGm3));
        assertTrue(lgs.contains(LaueGroup.LGm3m));
    }



    @Test
    public void testLG1() {
        LaueGroup pg = LaueGroup.LG1;
        assertEquals(1, pg.getOperators().length);
        assertEquals("1", pg.getSymbol());
        assertEquals(1, pg.getIndex());
        assertEquals(CrystalSystem.TRICLINIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLG2m() {
        LaueGroup pg = LaueGroup.LG2m;
        assertEquals(2, pg.getOperators().length);
        assertEquals("2/m", pg.getSymbol());
        assertEquals(2, pg.getIndex());
        assertEquals(CrystalSystem.MONOCLINIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLGmmm() {
        LaueGroup pg = LaueGroup.LGmmm;
        assertEquals(4, pg.getOperators().length);
        assertEquals("mmm", pg.getSymbol());
        assertEquals(3, pg.getIndex());
        assertEquals(CrystalSystem.ORTHORHOMBIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLGm3() {
        LaueGroup pg = LaueGroup.LGm3;
        assertEquals(12, pg.getOperators().length);
        assertEquals("m3", pg.getSymbol());
        assertEquals(10, pg.getIndex());
        assertEquals(CrystalSystem.CUBIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLG3() {
        LaueGroup pg = LaueGroup.LG3;
        assertEquals(3, pg.getOperators().length);
        assertEquals("3", pg.getSymbol());
        assertEquals(4, pg.getIndex());
        assertEquals(CrystalSystem.TRIGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLG3m() {
        LaueGroup pg = LaueGroup.LG3m;
        assertEquals(6, pg.getOperators().length);
        assertEquals("3m", pg.getSymbol());
        assertEquals(5, pg.getIndex());
        assertEquals(CrystalSystem.TRIGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLG4m() {
        LaueGroup pg = LaueGroup.LG4m;
        assertEquals(4, pg.getOperators().length);
        assertEquals("4/m", pg.getSymbol());
        assertEquals(6, pg.getIndex());
        assertEquals(CrystalSystem.TETRAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLG4mmm() {
        LaueGroup pg = LaueGroup.LG4mmm;
        assertEquals(8, pg.getOperators().length);
        assertEquals("4/mmm", pg.getSymbol());
        assertEquals(7, pg.getIndex());
        assertEquals(CrystalSystem.TETRAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLGm3m() {
        LaueGroup pg = LaueGroup.LGm3m;
        assertEquals(24, pg.getOperators().length);
        assertEquals("m3m", pg.getSymbol());
        assertEquals(11, pg.getIndex());
        assertEquals(CrystalSystem.CUBIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLG6m() {
        LaueGroup pg = LaueGroup.LG6m;
        assertEquals(6, pg.getOperators().length);
        assertEquals("6/m", pg.getSymbol());
        assertEquals(8, pg.getIndex());
        assertEquals(CrystalSystem.HEXAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testLG6mmm() {
        LaueGroup pg = LaueGroup.LG6mmm;
        assertEquals(12, pg.getOperators().length);
        assertEquals("6/mmm", pg.getSymbol());
        assertEquals(9, pg.getIndex());
        assertEquals(CrystalSystem.HEXAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }
}
