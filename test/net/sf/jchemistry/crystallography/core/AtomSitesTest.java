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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.sf.jchemistry.core.Element;

import org.apache.commons.math.geometry.Vector3D;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AtomSitesTest {

    private AtomSites atoms;

    private AtomSite atom1;

    private AtomSite atom2;

    private AtomSite atom3;



    @Before
    public void setUp() throws Exception {
        atom1 = new AtomSite(Element.Al, Vector3D.ZERO);
        atom2 = new AtomSite(Element.Si, new Vector3D(0.1, 0.2, 0.3));
        atom3 = new AtomSite(Element.P, new Vector3D(0.4, 0.5, 0.6));

        atoms = new AtomSites();
        atoms.add(atom1);
    }



    @Test
    public void testSize() {
        assertEquals(1, atoms.size());
    }



    @Test
    public void testAddAtomSite() {
        assertFalse(atoms.add(atom1));
        assertTrue(atoms.contains(atom1));

        assertTrue(atoms.add(atom2));
        assertTrue(atoms.contains(atom2));

        assertTrue(atoms.add(atom3));
        assertTrue(atoms.contains(atom3));
    }



    @Test
    public void testIterator() {
        Iterator<AtomSite> it = atoms.iterator();

        int count = 0;
        while (it.hasNext()) {
            it.next();
            count += 1;
        }

        assertEquals(count, atoms.size());
    }



    @Test
    public void testEquals() {
        assertFalse(atoms.equals(null));
        assertTrue(atoms.equals(atoms));

        Collection<AtomSite> other = new ArrayList<AtomSite>();
        other.add(atom1);
        assertTrue(atoms.equals(other));
    }



    @Test
    public void testRemoveAll() {
        Collection<AtomSite> c = new ArrayList<AtomSite>();
        c.add(atom2);
        c.add(atom3);

        assertFalse(atoms.removeAll(c));
        assertTrue(atoms.contains(atom1));

        c.add(atom1);
        assertTrue(atoms.removeAll(c));
        assertFalse(atoms.contains(atom1));
    }



    @Test
    public void testIsEmpty() {
        assertFalse(atoms.isEmpty());
    }



    @Test
    public void testContains() {
        assertTrue(atoms.contains(atom1));
        assertFalse(atoms.contains(atom2));
        assertFalse(atoms.contains(atom3));
    }



    @Test
    public void testToArray() {
        Object[] a = atoms.toArray();
        assertEquals(1, a.length);
        assertEquals(atom1, a[0]);
    }



    @Test
    public void testToArrayTArray() {
        AtomSite[] a = atoms.toArray(new AtomSite[0]);
        assertEquals(1, a.length);
        assertEquals(atom1, a[0]);
    }



    @Test
    public void testRemove() {
        assertFalse(atoms.remove(atom2));
        assertFalse(atoms.remove(atom3));
        assertTrue(atoms.remove(atom1));
        assertFalse(atoms.remove(atom1));
    }



    @Test
    public void testContainsAll() {
        Collection<AtomSite> c = new ArrayList<AtomSite>();
        c.add(atom1);
        c.add(atom2);

        assertFalse(atoms.containsAll(c));

        atoms.add(atom2);
        assertTrue(atoms.containsAll(c));
    }



    @Test
    public void testAddAll() {
        Collection<AtomSite> c = new ArrayList<AtomSite>();
        c.add(atom1);

        assertFalse(atoms.addAll(c));

        c.add(atom2);
        assertTrue(atoms.addAll(c));
        assertTrue(atoms.contains(atom1));
        assertTrue(atoms.contains(atom2));
    }



    @Test
    public void testRetainAll() {
        Collection<AtomSite> c = new ArrayList<AtomSite>();
        c.add(atom1);
        c.add(atom2);

        assertFalse(atoms.retainAll(c));
        assertTrue(atoms.contains(atom1));
        assertFalse(atoms.contains(atom2));

        assertTrue(atoms.retainAll(new ArrayList<AtomSite>()));
        assertFalse(atoms.contains(atom1));
    }



    @Test
    public void testClear() {
        atoms.clear();
        assertTrue(atoms.isEmpty());
    }

}
