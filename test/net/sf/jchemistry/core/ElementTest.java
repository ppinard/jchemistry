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
package net.sf.jchemistry.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementTest {

    @Test
    public void testFromZ() {
        for (int i = 1; i <= 112; i++)
            assertEquals(i, Element.fromZ(i).z());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testFromZException() {
        Element.fromZ(113);
    }



    @Test
    public void testFromSymbol() {
        for (Element element : Element.values())
            assertEquals(element.symbol(),
                    Element.fromSymbol(element.symbol()).symbol());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testFromSymbolException() {
        Element.fromSymbol("Aa");
    }
}
