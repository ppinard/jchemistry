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
package net.sf.jchemistry.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CollectionsTest {

    @Test
    public void testJoinEmpty() {
        List<String> collection = new ArrayList<String>();

        assertEquals("", Collections.join(collection, ","));
    }



    @Test
    public void testJoinOneItem() {
        List<String> collection = new ArrayList<String>();
        collection.add("item1");

        assertEquals("item1", Collections.join(collection, ","));
    }



    @Test
    public void testJoinTwoItems() {
        List<String> collection = new ArrayList<String>();
        collection.add("item1");
        collection.add("item2");

        assertEquals("item1,item2", Collections.join(collection, ","));
    }
}
