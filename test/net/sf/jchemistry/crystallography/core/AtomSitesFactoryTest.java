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

public class AtomSitesFactoryTest {

    @Test
    public void testAtomSitesBCC() {
        assertEquals(AtomSitesFactory.atomSitesBCC(Element.Al).size(), 2);
    }



    @Test
    public void testAtomSitesFCC() {
        assertEquals(AtomSitesFactory.atomSitesFCC(Element.Al).size(), 4);
    }



    @Test
    public void testAtomSitesHCP() {
        assertEquals(AtomSitesFactory.atomSitesHCP(Element.Al).size(), 2);
    }



    @Test
    public void testAtomSitesSingleAtom() {
        assertEquals(AtomSitesFactory.atomSitesSingleAtom(Element.Al).size(), 1);
    }

}
