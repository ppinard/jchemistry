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

public class AtomSiteTest {

    private AtomSite atom1;

    private AtomSite atom2;

    private AtomSite atom3;



    @Before
    public void setUp() throws Exception {
        atom1 = new AtomSite(Element.Al, new Vector3D(0.0, 0.5, 1.5));
        atom2 = new AtomSite(Element.P, new Vector3D(-0.1, 0.2, 0.3), 0.5);
        atom3 = new AtomSite(Element.S, -2, new Vector3D(0.1, 1.2, 2.3), 0.1);
    }



    @Test
    public void testToString() {
        assertEquals("Al -> {0; 0.5; 0.5} [100%]", atom1.toString());
        assertEquals("P -> {0.9; 0.2; 0.3} [50%]", atom2.toString());
        assertEquals("S2- -> {0.1; 0.2; 0.3} [10%]", atom3.toString());
    }



    @Test
    public void testAtomSiteElementVector3D() {
        assertEquals(Element.Al, atom1.getElement());
        assertEquals(0, atom1.getCharge());
        assertEquals(0.0, atom1.getPosition().getX(), 1e-6);
        assertEquals(0.5, atom1.getPosition().getY(), 1e-6);
        assertEquals(0.5, atom1.getPosition().getZ(), 1e-6);
        assertEquals(1.0, atom1.getOccupancy(), 1e-6);
    }



    @Test
    public void testAtomSiteElementVector3DDouble() {
        assertEquals(Element.P, atom2.getElement());
        assertEquals(0, atom2.getCharge());
        assertEquals(0.9, atom2.getPosition().getX(), 1e-6);
        assertEquals(0.2, atom2.getPosition().getY(), 1e-6);
        assertEquals(0.3, atom2.getPosition().getZ(), 1e-6);
        assertEquals(0.5, atom2.getOccupancy(), 1e-6);
    }



    @Test
    public void testAtomSiteElementIntVector3DDouble() {
        assertEquals(Element.S, atom3.getElement());
        assertEquals(-2, atom3.getCharge());
        assertEquals(0.1, atom3.getPosition().getX(), 1e-6);
        assertEquals(0.2, atom3.getPosition().getY(), 1e-6);
        assertEquals(0.3, atom3.getPosition().getZ(), 1e-6);
        assertEquals(0.1, atom3.getOccupancy(), 1e-6);
    }



    @Test(expected = NullPointerException.class)
    public void testAtomSiteElementIntVector3DDoubleException1() {
        new AtomSite(null, -2, Vector3D.ZERO, 0.1);
    }



    @Test(expected = NullPointerException.class)
    public void testAtomSiteElementIntVector3DDoubleException2() {
        new AtomSite(Element.S, -2, null, 0.1);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testAtomSiteElementIntVector3DDoubleException3() {
        new AtomSite(Element.S, -2, Vector3D.ZERO, -0.1);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testAtomSiteElementIntVector3DDoubleException4() {
        new AtomSite(Element.S, -2, Vector3D.ZERO, 1.1);
    }

}
