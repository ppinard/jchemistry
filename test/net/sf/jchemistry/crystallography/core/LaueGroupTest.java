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

import org.apache.commons.math.geometry.Rotation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
    public void testFromLaueGroup() {
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
    public void testFromLaueGroupException1() {
        LaueGroup.fromIndex(0);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testFromLaueGroupException2() {
        LaueGroup.fromIndex(12);
    }



    @Test
    public void testPG1() {
        LaueGroup pg = LaueGroup.LG1;
        assertEquals(1, pg.getOperators().length);
        assertEquals("1", pg.getSymbol());
        assertEquals(1, pg.getIndex());
        assertEquals(CrystalSystem.TRICLINIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG2() {
        LaueGroup pg = LaueGroup.LG2m;
        assertEquals(2, pg.getOperators().length);
        assertEquals("2/m", pg.getSymbol());
        assertEquals(2, pg.getIndex());
        assertEquals(CrystalSystem.MONOCLINIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG222() {
        LaueGroup pg = LaueGroup.LGmmm;
        assertEquals(4, pg.getOperators().length);
        assertEquals("mmm", pg.getSymbol());
        assertEquals(3, pg.getIndex());
        assertEquals(CrystalSystem.ORTHORHOMBIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG23() {
        LaueGroup pg = LaueGroup.LGm3;
        assertEquals(12, pg.getOperators().length);
        assertEquals("m3", pg.getSymbol());
        assertEquals(10, pg.getIndex());
        assertEquals(CrystalSystem.CUBIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG3() {
        LaueGroup pg = LaueGroup.LG3;
        assertEquals(3, pg.getOperators().length);
        assertEquals("3", pg.getSymbol());
        assertEquals(4, pg.getIndex());
        assertEquals(CrystalSystem.TRIGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG32() {
        LaueGroup pg = LaueGroup.LG3m;
        assertEquals(6, pg.getOperators().length);
        assertEquals("3m", pg.getSymbol());
        assertEquals(5, pg.getIndex());
        assertEquals(CrystalSystem.TRIGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG4() {
        LaueGroup pg = LaueGroup.LG4m;
        assertEquals(4, pg.getOperators().length);
        assertEquals("4/m", pg.getSymbol());
        assertEquals(6, pg.getIndex());
        assertEquals(CrystalSystem.TETRAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG422() {
        LaueGroup pg = LaueGroup.LG4mmm;
        assertEquals(8, pg.getOperators().length);
        assertEquals("4/mmm", pg.getSymbol());
        assertEquals(7, pg.getIndex());
        assertEquals(CrystalSystem.TETRAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG432() {
        LaueGroup pg = LaueGroup.LGm3m;
        assertEquals(24, pg.getOperators().length);
        assertEquals("m3m", pg.getSymbol());
        assertEquals(11, pg.getIndex());
        assertEquals(CrystalSystem.CUBIC, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG6() {
        LaueGroup pg = LaueGroup.LG6m;
        assertEquals(6, pg.getOperators().length);
        assertEquals("6/m", pg.getSymbol());
        assertEquals(8, pg.getIndex());
        assertEquals(CrystalSystem.HEXAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }



    @Test
    public void testPG622() {
        LaueGroup pg = LaueGroup.LG6mmm;
        assertEquals(12, pg.getOperators().length);
        assertEquals("6/mmm", pg.getSymbol());
        assertEquals(9, pg.getIndex());
        assertEquals(CrystalSystem.HEXAGONAL, pg.getCrystalSystem());
        allOperationsUnique(pg.getOperators());
    }
}
