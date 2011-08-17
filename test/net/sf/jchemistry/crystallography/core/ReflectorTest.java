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

import net.sf.jchemistry.crystallography.test.PhaseFactory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReflectorTest {

    private Reflector refl;



    @Before
    public void setUp() throws Exception {
        refl = new Reflector(1, 2, 3, 0.03512);
    }



    @Test
    public void testEquals() {
        assertTrue(refl.equals(refl));

        assertFalse(refl.equals(null));

        assertFalse(refl.equals(new Object()));

        Reflector refl2 = new Reflector(1, 2, 3, 0.5);
        assertEquals(refl, refl2);
    }



    @Test
    public void testGetBravaisIndices() {
        // Reflector 1
        Reflector refl = new Reflector(1, -2, 0, 1.0);

        int[] expected = new int[] { 1, -2, 1, 0 };
        int[] actual = refl.getBravaisIndices();
        assertArrayEquals(expected, actual);

        // Reflector 2
        refl = new Reflector(1, 1, 0, 1.0);

        expected = new int[] { 1, 1, -2, 0 };
        actual = refl.getBravaisIndices();
        assertArrayEquals(expected, actual);
    }



    @Test
    public void testGetMillerIndices() {
        int[] expected = new int[] { 1, 2, 3 };
        int[] actual = refl.getMillerIndices();
        assertArrayEquals(expected, actual);
    }



    @Test
    public void testHasCode() {
        assertEquals(new Reflector(1, 2, 3, 1.0).hashCode(), refl.hashCode());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testReflectorException1() {
        new Reflector(0, 0, 0, 0.03512);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testReflectorException2() {
        new Reflector(1, 2, 3, -1.0);
    }



    @Test
    public void testReflectorPlaneDoubleDoubleDouble() {
        assertEquals(1, refl.getH());
        assertEquals(2, refl.getK());
        assertEquals(3, refl.getL());
        assertEquals(0.03512, refl.getIntensity(), 1e-7);
    }



    @Test
    public void testGetIntensity() {
        assertEquals(0.03512, refl.getIntensity(), 1e-7);
    }



    @Test
    public void testGetH() {
        assertEquals(1, refl.getH());
    }



    @Test
    public void testGetK() {
        assertEquals(2, refl.getK());
    }



    @Test
    public void testGetL() {
        assertEquals(3, refl.getL());
    }



    @Test
    public void testToString() {
        assertEquals("[1, 2, 3]\t0.03512", refl.toString());
    }



    @Test
    public void testCreate() {
        Reflector refl =
                Reflector.create(1, 2, 3, PhaseFactory.silicon(),
                        ScatteringFactorsFactory.XRAY_TABULATED);

        assertEquals(1, refl.getH());
        assertEquals(2, refl.getK());
        assertEquals(3, refl.getL());
        assertEquals(1.44183091089e-29, refl.getIntensity(), 1e-35);
    }

}
