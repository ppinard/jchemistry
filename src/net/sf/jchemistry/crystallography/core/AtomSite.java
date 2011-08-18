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

import net.jcip.annotations.Immutable;
import net.sf.jchemistry.core.Element;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Stores an atom site. An atom site is defined by a position (x, y, z) along
 * the crystal axes (a, b, c) and an atomic number. The position is always
 * refined to be between [0, 1[. The position is saved as a {@link Vector3D} to
 * be used in calculations. The atomic number is between 1 (H) and 111 (Rg).
 * 
 * @author Philippe T. Pinard
 */
@Immutable
public final class AtomSite {

    /**
     * Refines the position of the atom to be between 0.0 and 1.0.
     * 
     * @param position
     *            position of the atom
     * @return refine position vector
     */
    private static Vector3D refinePosition(Vector3D position) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        while (x < 0)
            x += 1;
        while (y < 0)
            y += 1;
        while (z < 0)
            z += 1;

        while (x >= 1)
            x -= 1;
        while (y >= 1)
            y -= 1;
        while (z >= 1)
            z -= 1;

        return new Vector3D(x, y, z);
    }

    /** Atomic number of the atom. */
    private final Element element;

    /** Electron charge of the atom. */
    private final int charge;

    /** Occupancy of the atom on this position. */
    private final double occupancy;

    /** Position of the atom in the unit cell. */
    private final Vector3D position;



    /**
     * Creates a new <code>AtomSite</code> from the atomic number, charge,
     * position of an atom and occupancy.
     * 
     * @param element
     *            element of the atom
     * @param charge
     *            electron charge of the atom
     * @param position
     *            position relative to the unit cell
     * @param occupancy
     *            occupancy of the atom at this position (=1.0 if the atom is
     *            always there)
     * @throws IllegalArgumentException
     *             if the occupancy is outside [0, 1]
     * @throws NullPointerException
     *             if the position vector is null
     * @throws NullPointerException
     *             if the element is null
     */
    public AtomSite(Element element, int charge, Vector3D position,
            double occupancy) {
        if (element == null)
            throw new NullPointerException("element == null");
        if (position == null)
            throw new NullPointerException("position == null");
        if (occupancy < 0 || occupancy > 1)
            throw new IllegalArgumentException("The occupancy (" + occupancy
                    + ") must be between [0,1].");

        this.element = element;
        this.charge = charge;
        this.position = refinePosition(position);
        this.occupancy = occupancy;
    }



    /**
     * Creates a new <code>AtomSite</code> from the atomic number and position
     * of an atom. The occurrence is set to 1.0.
     * 
     * @param element
     *            element of the atom
     * @param position
     *            position relative to the unit cell
     * @throws IllegalArgumentException
     *             if the atomic number is outside [1,111].
     * @throws NullPointerException
     *             if the position vector is null.
     */
    public AtomSite(Element element, Vector3D position) {
        this(element, position, 1.0);
    }



    /**
     * Creates a new <code>AtomSite</code> from the atomic number, position of
     * an atom and occupancy.
     * 
     * @param element
     *            element of the atom
     * @param position
     *            position relative to the unit cell
     * @param occupancy
     *            occupancy of the atom at this position (=1.0 if the atom is
     *            always there)
     * @throws IllegalArgumentException
     *             if the atomic number is outside [1,111].
     * @throws IllegalArgumentException
     *             if the occupancy is outside [0, 1].
     * @throws NullPointerException
     *             if the position vector is null.
     */
    public AtomSite(Element element, Vector3D position, double occupancy) {
        this(element, 0, position, occupancy);
    }



    /**
     * Returns the electric charge of this atom.
     * 
     * @return electric charge
     */
    public int getCharge() {
        return charge;
    }



    /**
     * Returns the type of this atom.
     * 
     * @return element
     */
    public Element getElement() {
        return element;
    }



    /**
     * Returns the occupancy of this atom. The occupancy is expressed by a
     * number between [0.0, 1.0].
     * 
     * @return occupancy
     */
    public double getOccupancy() {
        return occupancy;
    }



    /**
     * Returns the position of the atom. The position is expressed as a fraction
     * along the <code>a</code>, <code>b</code> and <code>c</code> axes of the
     * {@link UnitCell}.
     * 
     * @return position
     */
    public Vector3D getPosition() {
        return position;
    }



    /**
     * Returns a representation of the <code>AtomSite</code> , suitable for
     * debugging.
     * 
     * @return information about the <code>AtomSite</code>
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(element.symbol());

        if (charge != 0) {
            str.append(Math.abs(charge));

            if (charge > 0)
                str.append("+");
            else
                str.append("-");
        }

        str.append(" -> ");
        str.append(position);
        str.append(" [");
        str.append((int) (occupancy * 100));
        str.append("%]");

        return str.toString();
    }
}
