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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScatteringFactorsFactoryTest {

    @Test
    public void testElectronMottBethe() {
        ScatteringFactors scatter =
                ScatteringFactorsFactory.ELECTRON_MOTT_BETHE;

        double factor;
        double expectedFactor;

        /* Silicon */
        // s = 0
        factor = scatter.getIntensity(Element.Si, 0, 0);
        expectedFactor = 6.6576378;
        assertEquals(expectedFactor, factor, 1e-6);

        // s = 0.5
        factor = scatter.getIntensity(Element.Si, 0, 0.5);
        expectedFactor = 5.551166158;
        assertEquals(expectedFactor, factor, 1e-6);

        // s = 3.0
        factor = scatter.getIntensity(Element.Si, 0, 3.0);
        expectedFactor = 2.089266059;
        assertEquals(expectedFactor, factor, 1e-6);

        // s = 6.1
        factor = scatter.getIntensity(Element.Si, 0, 6.1);
        expectedFactor = 0.7740717721;
        assertEquals(expectedFactor, factor, 1e-6);

        /* Copper */
        // s = 0.5
        factor = scatter.getIntensity(Element.Cu, 0, 0.5);
        expectedFactor = 5.38125559;
        assertEquals(expectedFactor, factor, 1e-6);

        // s = 3.0
        factor = scatter.getIntensity(Element.Cu, 0, 3.0);
        expectedFactor = 2.89437299;
        assertEquals(expectedFactor, factor, 1e-6);

        // s = 6.1
        factor = scatter.getIntensity(Element.Cu, 0, 6.1);
        expectedFactor = 1.516441342;
        assertEquals(expectedFactor, factor, 1e-6);

        /* Gold */
        // s = 0.5
        factor = scatter.getIntensity(Element.Au, 0, 0.5);
        expectedFactor = 10.509565857;
        assertEquals(expectedFactor, factor, 1e-6);

        // s = 3.0
        factor = scatter.getIntensity(Element.Au, 0, 3.0);
        expectedFactor = 6.254022736;
        assertEquals(expectedFactor, factor, 1e-6);

        // s = 6.1
        factor = scatter.getIntensity(Element.Au, 0, 6.1);
        expectedFactor = 3.1821314148;
        assertEquals(expectedFactor, factor, 1e-6);
    }



    @Test
    public void testElectronMottBetheReadAll() {
        ScatteringFactors scatter =
                ScatteringFactorsFactory.ELECTRON_MOTT_BETHE;

        for (int atomicNumber = 2; atomicNumber <= 98; atomicNumber++) {
            scatter.getIntensity(Element.fromZ(atomicNumber), 0, 1.0);
            scatter.getIntensity(Element.fromZ(atomicNumber), 0, 4.0);
        }
    }



    @Test
    public void testElectronTabulated() {
        ScatteringFactors scatter = ScatteringFactorsFactory.ELECTRON_TABULATED;

        double factor;
        double expectedFactor;

        /* Silicon */
        // s = 0.5
        factor = scatter.getIntensity(Element.Si, 0, 0.5);
        expectedFactor = 0.742519788187;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 3.0
        factor = scatter.getIntensity(Element.Si, 0, 3.0);
        expectedFactor = 0.0349033998305;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 6.1
        factor = scatter.getIntensity(Element.Si, 0, 6.1);
        expectedFactor = 0.00650227565622;
        assertEquals(expectedFactor, factor, 1e-7);

        /* Copper */
        // s = 0.5
        factor = scatter.getIntensity(Element.Cu, 0, 0.5);
        expectedFactor = 1.46390369208;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 3.0
        factor = scatter.getIntensity(Element.Cu, 0, 3.0);
        expectedFactor = 0.0652299842623;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 6.1
        factor = scatter.getIntensity(Element.Cu, 0, 6.1);
        expectedFactor = 0.000336590516491;
        assertEquals(expectedFactor, factor, 1e-7);

        /* Gold */
        // s = 0.5
        factor = scatter.getIntensity(Element.Au, 0, 0.5);
        expectedFactor = 3.07239344718;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 3.0
        factor = scatter.getIntensity(Element.Au, 0, 3.0);
        expectedFactor = 0.186031146844;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 6.1
        factor = scatter.getIntensity(Element.Au, 0, 6.1);
        expectedFactor = 0.0332559532;
        assertEquals(expectedFactor, factor, 1e-7);
    }



    @Test
    public void testElectronTabulatedReadAll() {
        ScatteringFactors scatter = ScatteringFactorsFactory.ELECTRON_TABULATED;

        for (int atomicNumber = 1; atomicNumber <= 98; atomicNumber++) {
            scatter.getIntensity(Element.fromZ(atomicNumber), 0, 1.0);
            scatter.getIntensity(Element.fromZ(atomicNumber), 0, 4.0);
        }
    }



    @Test
    public void testXrayTabulated() {
        ScatteringFactors scatter = ScatteringFactorsFactory.XRAY_TABULATED;
        double factor;
        double expectedFactor;

        /* Silicon */
        // s = 0.5
        factor = scatter.getIntensity(Element.Si, 0, 0.5 * 4 * Math.PI);
        expectedFactor = 6.2400885119901508;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 3.0
        factor = scatter.getIntensity(Element.Si, 0, 3.0 * 4 * Math.PI);
        expectedFactor = 0.72266443008234738;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 6.1
        factor = scatter.getIntensity(Element.Si, 0, 6.1 * 4 * Math.PI);
        expectedFactor = 0.10091802161492447;
        assertEquals(expectedFactor, factor, 1e-7);

        /* Copper */
        // s = 0.5
        factor = scatter.getIntensity(Element.Cu, 0, 0.5 * 4 * Math.PI);
        expectedFactor = 13.70070197002754;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 3.0
        factor = scatter.getIntensity(Element.Cu, 0, 3.0 * 4 * Math.PI);
        expectedFactor = 2.0104488387433492;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 6.1
        factor = scatter.getIntensity(Element.Cu, 0, 6.1 * 4 * Math.PI);
        expectedFactor = 0.9191770170219552;
        assertEquals(expectedFactor, factor, 1e-7);

        /* Gold */
        // s = 0.5
        factor = scatter.getIntensity(Element.Au, 0, 0.5 * 4 * Math.PI);
        expectedFactor = 46.906655657698153;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 3.0
        factor = scatter.getIntensity(Element.Au, 0, 3.0 * 4 * Math.PI);
        expectedFactor = 9.7301060945727169;
        assertEquals(expectedFactor, factor, 1e-7);

        // s = 6.1
        factor = scatter.getIntensity(Element.Au, 0, 6.1 * 4 * Math.PI);
        expectedFactor = 4.2485964205405162;
        assertEquals(expectedFactor, factor, 1e-7);
    }



    @Test
    public void testXrayTabulatedReadAll() {
        ScatteringFactors scatter = ScatteringFactorsFactory.XRAY_TABULATED;

        for (int atomicNumber = 2; atomicNumber <= 98; atomicNumber++) {
            scatter.getIntensity(Element.fromZ(atomicNumber), 0, 1.0);
            scatter.getIntensity(Element.fromZ(atomicNumber), 0, 4.0);
        }
    }



    @Test
    public void testGetIntensity() {

    }
}
