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

/**
 * Enumeration of the 7 crystal systems.
 * 
 * @author Philippe T. Pinard
 */
public enum CrystalSystem {

    /** Triclinic crystal system (a != b != c and alpha != beta != gamma). */
    TRICLINIC,

    /** Monoclinic crystal system (a != b != c and alpha = gamma = PI/2). */
    MONOCLINIC,

    /**
     * Orthorhombic crystal system (a != b != c and alpha = beta = gamma =
     * PI/2).
     */
    ORTHORHOMBIC,

    /** Tetragonal crystal system (a = b and alpha = beta = gamma = PI/2). */
    TETRAGONAL,

    /**
     * Tetragonal crystal system (a = b and alpha = beta = PI/2, gamma = 2PI/3).
     */
    HEXAGONAL,

    /** Trigonal crystal system (a = b = c and alpha = beta = gamma). */
    TRIGONAL,

    /** Cubic crystal system (a = b = c and alpha = beta = gamma = PI/2). */
    CUBIC;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
