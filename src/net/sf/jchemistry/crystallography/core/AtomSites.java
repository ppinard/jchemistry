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

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Set of all the atom sites of a phase. Only atoms that are further apart than
 * a specified minimum distance can be added to this set. This distance is set
 * in the constructor.
 * 
 * @author Philippe T. Pinard
 */
public class AtomSites extends AbstractSet<AtomSite> {

    /** Internal set of the atom sites. */
    private final Set<AtomSite> set;

    /** How close two atoms can be next to each other. */
    private static final double DELTA = 1e-6;



    /**
     * Creates a new set of <code>AtomSite</code>'s. The minimum distance
     * between atom positions is set to 1e-6.
     */
    public AtomSites() {
        set = new HashSet<AtomSite>();
    }



    @Override
    public boolean add(AtomSite atom) {
        if (isPositionOccupied(atom))
            return false;

        return set.add(atom);
    }



    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Collection)) // Collection instead of Set
            return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }



    /**
     * Returns the position of the specified new atom is already occupied by
     * another atom in this set.
     * 
     * @param newAtom
     *            new atom
     * @return <code>true</code> if the position is already occupied,
     *         <code>false</code> otherwise
     */
    private boolean isPositionOccupied(AtomSite newAtom) {
        Vector3D newPos = newAtom.getPosition();
        Vector3D pos;

        Iterator<AtomSite> e = iterator();
        while (e.hasNext()) {
            pos = e.next().getPosition();
            if (Math.abs(pos.getX() - newPos.getX()) <= DELTA
                    && Math.abs(pos.getY() - newPos.getY()) <= DELTA
                    && Math.abs(pos.getZ() - newPos.getZ()) <= DELTA)
                return true;
        }

        return false;
    }



    @Override
    public Iterator<AtomSite> iterator() {
        return set.iterator();
    }



    @Override
    public int size() {
        return set.size();
    }

}
