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

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator based on the atomic number of the element.
 * 
 * @author Philippe T. Pinard
 */
public class ElementComparator implements Comparator<Element>, Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -236437422763230177L;



    @Override
    public int compare(Element o1, Element o2) {
        int z1 = o1.z();
        int z2 = o2.z();

        if (z1 < z2)
            return -1;
        else if (z1 > z2)
            return 1;
        else
            return 0;
    }

}
