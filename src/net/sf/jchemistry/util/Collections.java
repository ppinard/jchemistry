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
package net.sf.jchemistry.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility with <code>Collection</code>.
 * 
 * @author Philippe T. Pinard
 */
public final class Collections {

    /**
     * Constructor to prevent the class to be instantiated.
     */
    private Collections() {

    }



    /**
     * Returns a string with the concatenation of the element inside the
     * collection. Each element is separated by the specified separator.
     * 
     * @param <E>
     *            type of the collection
     * @param c
     *            collection
     * @param sep
     *            separator
     * @return string
     */
    public static <E> String join(Collection<E> c, String sep) {
        StringBuilder sb = new StringBuilder();

        Iterator<E> i = c.iterator();
        if (!i.hasNext())
            return "[]";

        while (true) {
            E e = i.next();
            sb.append(e == c ? "(this Collection)" : e);
            if (!i.hasNext())
                return sb.toString();
            sb.append(sep);
        }
    }

}
