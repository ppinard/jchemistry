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

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

public class UnitCellFactoryTest {

    @Test
    public void testCubic() {
        UnitCell cubic = UnitCellFactory.cubic(2.0);

        assertEquals(cubic.getA(), 2.0, 1e-7);
        assertEquals(cubic.getB(), 2.0, 1e-7);
        assertEquals(cubic.getC(), 2.0, 1e-7);
        assertEquals(cubic.getReciprocalA(), 0.5, 1e-7);
        assertEquals(cubic.getReciprocalB(), 0.5, 1e-7);
        assertEquals(cubic.getReciprocalC(), 0.5, 1e-7);
        assertEquals(cubic.getAlpha(), PI / 2, 1e-7);
        assertEquals(cubic.getBeta(), PI / 2, 1e-7);
        assertEquals(cubic.getGamma(), PI / 2, 1e-7);
        assertEquals(cubic.getReciprocalAlpha(), PI / 2, 1e-7);
        assertEquals(cubic.getReciprocalBeta(), PI / 2, 1e-7);
        assertEquals(cubic.getReciprocalGamma(), PI / 2, 1e-7);
        assertEquals(cubic.getVolume(), 8.0, 1e-7);
        assertEquals(cubic.getReciprocalVolume(), 0.125, 1e-7);

        assertEquals(cubic.getReciprocalA(), 1.0 / 2.0, 1e-7);
        assertEquals(cubic.getReciprocalB(), 1.0 / 2.0, 1e-7);
        assertEquals(cubic.getReciprocalC(), 1.0 / 2.0, 1e-7);
        assertEquals(cubic.getVolume(), pow(2.0, 3), 1e-7);
    }



    @Test
    public void testHexagonal() {
        UnitCell hexagonal = UnitCellFactory.hexagonal(2.0, 3.0);

        assertEquals(hexagonal.getA(), 2.0, 1e-7);
        assertEquals(hexagonal.getB(), 2.0, 1e-7);
        assertEquals(hexagonal.getC(), 3.0, 1e-7);
        assertEquals(hexagonal.getReciprocalA(), 0.5773502691896257, 1e-7);
        assertEquals(hexagonal.getReciprocalB(), 0.5773502691896257, 1e-7);
        assertEquals(hexagonal.getReciprocalC(), 0.333333333, 1e-7);
        assertEquals(hexagonal.getAlpha(), PI / 2, 1e-7);
        assertEquals(hexagonal.getBeta(), PI / 2, 1e-7);
        assertEquals(hexagonal.getGamma(), 120.0 / 180 * PI, 1e-7);
        assertEquals(hexagonal.getReciprocalAlpha(), PI / 2, 1e-7);
        assertEquals(hexagonal.getReciprocalBeta(), PI / 2, 1e-7);
        assertEquals(hexagonal.getReciprocalGamma(), 60.0 / 180 * PI, 1e-7);
        assertEquals(hexagonal.getVolume(), 10.392304845413264, 1e-7);
        assertEquals(hexagonal.getReciprocalVolume(), 0.09622504486493763, 1e-7);

        assertEquals(hexagonal.getReciprocalA(), 2.0 / 3.0 * sqrt(3) / 2.0,
                1e-7);
        assertEquals(hexagonal.getReciprocalB(), 2.0 / 3.0 * sqrt(3) / 2.0,
                1e-7);
        assertEquals(hexagonal.getReciprocalC(), 1.0 / 3.0, 1e-7);
        assertEquals(hexagonal.getVolume(), 0.5 * sqrt(3) * pow(2.0, 2) * 3.0,
                1e-7);
    }



    @Test
    public void testMonoclinic() {
        UnitCell monoclinic =
                UnitCellFactory.monoclinic(1.0, 2.0, 3.0, 55.0 / 180 * PI);

        assertEquals(monoclinic.getA(), 1.0, 1e-7);
        assertEquals(monoclinic.getB(), 2.0, 1e-7);
        assertEquals(monoclinic.getC(), 3.0, 1e-7);
        assertEquals(monoclinic.getReciprocalA(), 1.220774588761456, 1e-7);
        assertEquals(monoclinic.getReciprocalB(), 0.5, 1e-7);
        assertEquals(monoclinic.getReciprocalC(), 0.40692486292048535, 1e-7);
        assertEquals(monoclinic.getAlpha(), PI / 2, 1e-7);
        assertEquals(monoclinic.getBeta(), 55.0 / 180 * PI, 1e-7);
        assertEquals(monoclinic.getGamma(), PI / 2, 1e-7);
        assertEquals(monoclinic.getReciprocalAlpha(), PI / 2, 1e-7);
        assertEquals(monoclinic.getReciprocalBeta(), 125.0 / 180 * PI, 1e-7);
        assertEquals(monoclinic.getReciprocalGamma(), PI / 2, 1e-7);
        assertEquals(monoclinic.getVolume(), 4.914912265733951, 1e-7);
        assertEquals(monoclinic.getReciprocalVolume(), 0.20346243146024268,
                1e-7);

        assertEquals(monoclinic.getReciprocalA(),
                1.0 / (1 * sin(125.0 / 180 * PI)), 1e-7);
        assertEquals(monoclinic.getReciprocalB(), 1.0 / 2.0, 1e-7);
        assertEquals(monoclinic.getReciprocalC(),
                1.0 / (3 * sin(125.0 / 180 * PI)), 1e-7);
        assertEquals(monoclinic.getVolume(),
                1.0 * 2.0 * 3.0 * sin(125.0 / 180 * PI), 1e-7);
    }



    @Test
    public void testOrthorhombic() {
        UnitCell orthorhombic = UnitCellFactory.orthorhombic(1.0, 2.0, 3.0);

        assertEquals(orthorhombic.getA(), 1.0, 1e-7);
        assertEquals(orthorhombic.getB(), 2.0, 1e-7);
        assertEquals(orthorhombic.getC(), 3.0, 1e-7);
        assertEquals(orthorhombic.getReciprocalA(), 1.0, 1e-7);
        assertEquals(orthorhombic.getReciprocalB(), 0.5, 1e-7);
        assertEquals(orthorhombic.getReciprocalC(), 0.33333333, 1e-7);
        assertEquals(orthorhombic.getAlpha(), PI / 2, 1e-7);
        assertEquals(orthorhombic.getBeta(), PI / 2, 1e-7);
        assertEquals(orthorhombic.getGamma(), PI / 2, 1e-7);
        assertEquals(orthorhombic.getReciprocalAlpha(), PI / 2, 1e-7);
        assertEquals(orthorhombic.getReciprocalBeta(), PI / 2, 1e-7);
        assertEquals(orthorhombic.getReciprocalGamma(), PI / 2, 1e-7);
        assertEquals(orthorhombic.getVolume(), 6.0, 1e-7);
        assertEquals(orthorhombic.getReciprocalVolume(), 0.166666666, 1e-7);

        assertEquals(orthorhombic.getReciprocalA(), 1.0 / 1.0, 1e-7);
        assertEquals(orthorhombic.getReciprocalB(), 1.0 / 2.0, 1e-7);
        assertEquals(orthorhombic.getReciprocalC(), 1.0 / 3.0, 1e-7);
        assertEquals(orthorhombic.getVolume(), 1.0 * 2.0 * 3.0, 1e-7);
    }



    @Test
    public void testTetragonal() {
        UnitCell tetragonal = UnitCellFactory.tetragonal(2.0, 3.0);

        assertEquals(tetragonal.getA(), 2.0, 1e-7);
        assertEquals(tetragonal.getB(), 2.0, 1e-7);
        assertEquals(tetragonal.getC(), 3.0, 1e-7);
        assertEquals(tetragonal.getReciprocalA(), 0.5, 1e-7);
        assertEquals(tetragonal.getReciprocalB(), 0.5, 1e-7);
        assertEquals(tetragonal.getReciprocalC(), 0.333333333, 1e-7);
        assertEquals(tetragonal.getAlpha(), PI / 2, 1e-7);
        assertEquals(tetragonal.getBeta(), PI / 2, 1e-7);
        assertEquals(tetragonal.getGamma(), PI / 2, 1e-7);
        assertEquals(tetragonal.getReciprocalAlpha(), PI / 2, 1e-7);
        assertEquals(tetragonal.getReciprocalBeta(), PI / 2, 1e-7);
        assertEquals(tetragonal.getReciprocalGamma(), PI / 2, 1e-7);
        assertEquals(tetragonal.getVolume(), 12.0, 1e-7);
        assertEquals(tetragonal.getReciprocalVolume(), 0.083333333, 1e-7);

        assertEquals(tetragonal.getReciprocalA(), 1.0 / 2.0, 1e-7);
        assertEquals(tetragonal.getReciprocalB(), 1.0 / 2.0, 1e-7);
        assertEquals(tetragonal.getReciprocalC(), 1.0 / 3.0, 1e-7);
        assertEquals(tetragonal.getVolume(), pow(2.0, 2) * 3.0, 1e-7);
    }



    @Test
    public void testTriclinic() {
        UnitCell triclinic =
                UnitCellFactory.triclinic(1.0, 2.0, 3.0, 75.0 / 180 * PI,
                        55.0 / 180 * PI, 35.0 / 180 * PI);

        assertEquals(triclinic.getA(), 1.0, 1e-7);
        assertEquals(triclinic.getB(), 2.0, 1e-7);
        assertEquals(triclinic.getC(), 3.0, 1e-7);
        assertEquals(triclinic.getReciprocalA(), 2.3009777700230383, 1e-7);
        assertEquals(triclinic.getReciprocalB(), 0.9756704877739889, 1e-7);
        assertEquals(triclinic.getReciprocalC(), 0.45544788689872767, 1e-7);
        assertEquals(triclinic.getAlpha(), 75.0 / 180 * PI, 1e-7);
        assertEquals(triclinic.getBeta(), 55.0 / 180 * PI, 1e-7);
        assertEquals(triclinic.getGamma(), 35.0 / 180 * PI, 1e-7);
        assertEquals(triclinic.getReciprocalAlpha(), 1.1049925940211875, 1e-7);
        assertEquals(triclinic.getReciprocalBeta(), 2.281813838221562, 1e-7);
        assertEquals(triclinic.getReciprocalGamma(), 2.582348070021294, 1e-7);
        assertEquals(triclinic.getVolume(), 2.518735744968272, 1e-7);
        assertEquals(triclinic.getReciprocalVolume(), 0.3970245794929935, 1e-7);
    }



    @Test
    public void testTrigonal() {
        UnitCell trigonal = UnitCellFactory.trigonal(2.0, 35.0 / 180 * PI);

        assertEquals(trigonal.getA(), 2.0, 1e-7);
        assertEquals(trigonal.getB(), 2.0, 1e-7);
        assertEquals(trigonal.getC(), 2.0, 1e-7);
        assertEquals(trigonal.getReciprocalA(), 0.9763044673403796, 1e-7);
        assertEquals(trigonal.getReciprocalB(), 0.9763044673403796, 1e-7);
        assertEquals(trigonal.getReciprocalC(), 0.9763044673403796, 1e-7);
        assertEquals(trigonal.getAlpha(), 35.0 / 180 * PI, 1e-7);
        assertEquals(trigonal.getBeta(), 35.0 / 180 * PI, 1e-7);
        assertEquals(trigonal.getGamma(), 35.0 / 180 * PI, 1e-7);
        assertEquals(trigonal.getReciprocalAlpha(), 2.0378901672656156, 1e-7);
        assertEquals(trigonal.getReciprocalBeta(), 2.0378901672656156, 1e-7);
        assertEquals(trigonal.getReciprocalGamma(), 2.0378901672656156, 1e-7);
        assertEquals(trigonal.getVolume(), 2.349990010446501, 1e-7);
        assertEquals(trigonal.getReciprocalVolume(), 0.42553372378378695, 1e-7);
    }

}
