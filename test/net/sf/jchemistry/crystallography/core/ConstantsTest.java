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

import org.junit.Test;

import static java.lang.Math.sqrt;
import static net.sf.jchemistry.crystallography.core.Constants.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ConstantsTest {

    private static double h2 = 1.0 / 2.0;

    private static double s3 = sqrt(3) / 2;



    private static void assertDoubleArrayEquals(double[][] a, double[][] b,
            double delta) {
        assertEquals("Array length are unequal", a.length, b.length);

        for (int i = 0; i < a.length; i++)
            assertArrayEquals(a[i], b[i], delta);
    }



    @Test
    public void testBohrRadius() {
        assertEquals(5.29e-11, BOHR_RADIUS, 1e-6);
    }



    @Test
    public void testH2_2xy() {
        double[][] expected =
                new double[][] { { -h2, s3, 0 }, { s3, h2, 0 }, { 0, 0, -1 } };
        double[][] m = H2_2XY.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testH2_x2y() {
        double[][] expected =
                new double[][] { { -h2, -s3, 0 }, { -s3, h2, 0 }, { 0, 0, -1 } };
        double[][] m = H2_X2Y.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testH2_xny() {
        double[][] expected =
                new double[][] { { h2, -s3, 0 }, { -s3, -h2, 0 }, { 0, 0, -1 } };
        double[][] m = H2_XNY.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testH2_xy() {
        double[][] expected =
                new double[][] { { h2, s3, 0 }, { s3, -h2, 0 }, { 0, 0, -1 } };
        double[][] m = H2_XY.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testH3n_z() {
        double[][] expected =
                new double[][] { { -h2, s3, 0 }, { -s3, -h2, 0 }, { 0, 0, 1 } };
        double[][] m = H3N_Z.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testH3p_z() {
        double[][] expected =
                new double[][] { { -h2, -s3, 0 }, { s3, -h2, 0 }, { 0, 0, 1 } };
        double[][] m = H3P_Z.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testH6n_z() {
        double[][] expected =
                new double[][] { { h2, s3, 0 }, { -s3, h2, 0 }, { 0, 0, 1 } };
        double[][] m = H6N_Z.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testH6p_z() {
        double[][] expected =
                new double[][] { { h2, -s3, 0 }, { s3, h2, 0 }, { 0, 0, 1 } };
        double[][] m = H6P_Z.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testHBar() {
        assertEquals(1.054571726e-34, H_BAR, 1e-6);
    }



    @Test
    public void testO1() {
        double[][] expected =
                new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
        double[][] m = O1.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_nxz() {
        double[][] expected =
                new double[][] { { 0, 0, -1 }, { 0, -1, 0 }, { -1, 0, 0 } };
        double[][] m = O2_NXZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_x() {
        double[][] expected =
                new double[][] { { 1, 0, 0 }, { 0, -1, 0 }, { 0, 0, -1 } };
        double[][] m = O2_X.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_xny() {
        double[][] expected =
                new double[][] { { 0, -1, 0 }, { -1, 0, 0 }, { 0, 0, -1 } };
        double[][] m = O2_XNY.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_xy() {
        double[][] expected =
                new double[][] { { 0, 1, 0 }, { 1, 0, 0 }, { 0, 0, -1 } };
        double[][] m = O2_XY.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_xz() {
        double[][] expected =
                new double[][] { { 0, 0, 1 }, { 0, -1, 0 }, { 1, 0, 0 } };
        double[][] m = O2_XZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_y() {
        double[][] expected =
                new double[][] { { -1, 0, 0 }, { 0, 1, 0 }, { 0, 0, -1 } };
        double[][] m = O2_Y.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_ynz() {
        double[][] expected =
                new double[][] { { -1, 0, 0 }, { 0, 0, -1 }, { 0, -1, 0 } };
        double[][] m = O2_YNZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_yz() {
        double[][] expected =
                new double[][] { { -1, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 } };
        double[][] m = O2_YZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO2_z() {
        double[][] expected =
                new double[][] { { -1, 0, 0 }, { 0, -1, 0 }, { 0, 0, 1 } };
        double[][] m = O2_Z.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3n_nxnyz() {
        double[][] expected =
                new double[][] { { 0, 1, 0 }, { 0, 0, -1 }, { -1, 0, 0 } };
        double[][] m = O3N_NXNYZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3n_nxynz() {
        double[][] expected =
                new double[][] { { 0, -1, 0 }, { 0, 0, -1 }, { 1, 0, 0 } };
        double[][] m = O3N_NXYNZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3n_xnynz() {
        double[][] expected =
                new double[][] { { 0, -1, 0 }, { 0, 0, 1 }, { -1, 0, 0 } };
        double[][] m = O3N_XNYNZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3n_xyz() {
        double[][] expected =
                new double[][] { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 0, 0 } };
        double[][] m = O3N_XYZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3p_nxnyz() {
        double[][] expected =
                new double[][] { { 0, 0, -1 }, { 1, 0, 0 }, { 0, -1, 0 } };
        double[][] m = O3P_NXNYZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3p_nxynz() {
        double[][] expected =
                new double[][] { { 0, 0, 1 }, { -1, 0, 0 }, { 0, -1, 0 } };
        double[][] m = O3P_NXYNZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3p_xnynz() {
        double[][] expected =
                new double[][] { { 0, 0, -1 }, { -1, 0, 0 }, { 0, 1, 0 } };
        double[][] m = O3P_XNYNZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO3p_xyz() {
        double[][] expected =
                new double[][] { { 0, 0, 1 }, { 1, 0, 0 }, { 0, 1, 0 } };
        double[][] m = O3P_XYZ.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO4n_x() {
        double[][] expected =
                new double[][] { { 1, 0, 0 }, { 0, 0, 1 }, { 0, -1, 0 } };
        double[][] m = O4N_X.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO4n_y() {
        double[][] expected =
                new double[][] { { 0, 0, -1 }, { 0, 1, 0 }, { 1, 0, 0 } };
        double[][] m = O4N_Y.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO4n_z() {
        double[][] expected =
                new double[][] { { 0, 1, 0 }, { -1, 0, 0 }, { 0, 0, 1 } };
        double[][] m = O4N_Z.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO4p_x() {
        double[][] expected =
                new double[][] { { 1, 0, 0 }, { 0, 0, -1 }, { 0, 1, 0 } };
        double[][] m = O4P_X.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO4p_y() {
        double[][] expected =
                new double[][] { { 0, 0, 1 }, { 0, 1, 0 }, { -1, 0, 0 } };
        double[][] m = O4P_Y.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testO4p_z() {
        double[][] expected =
                new double[][] { { 0, -1, 0 }, { 1, 0, 0 }, { 0, 0, 1 } };
        double[][] m = O4P_Z.getMatrix();
        assertDoubleArrayEquals(expected, m, 1e-7);
    }



    @Test
    public void testPermeability() {
        assertEquals(1.2566370614e-6, PERMEABILITY, 1e-6);
    }



    @Test
    public void testPermittivity() {
        assertEquals(8.854187817620e-12, PERMITTIVITY, 1e-6);
    }

}
