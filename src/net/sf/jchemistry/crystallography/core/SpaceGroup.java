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

import org.apache.commons.math.geometry.Rotation;

/**
 * Crystallographic space group as defined in the International Tables for
 * Crystallography.
 * 
 * @author Philippe T. Pinard
 */
public final class SpaceGroup implements Comparable<SpaceGroup> {

    /** Array of generators. */
    private final Generator[] generators;

    /** Index/number of the space group (1 to 203). */
    private final int index;

    /** Laue group. */
    private final LaueGroup laueGroup;

    /** Symbol of the space group. */
    private final String symbol;



    /**
     * Creates a new <code>SpaceGroup</code>.
     * 
     * @param index
     *            index/number of the space group (1 to 230)
     * @param symbol
     *            Hermann-Mauguin symbol of the space group
     * @param laueGroup
     *            Laue group
     * @param generators
     *            array of generators
     */
    protected SpaceGroup(int index, String symbol, LaueGroup laueGroup,
            Generator[] generators) {
        this.index = index;
        this.symbol = symbol;
        this.laueGroup = laueGroup;
        this.generators = generators.clone();
    }



    @Override
    public int compareTo(SpaceGroup o) {
        if (index < o.index)
            return -1;
        else if (index > o.index)
            return 1;
        else
            return 0;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        SpaceGroup other = (SpaceGroup) obj;
        if (index != other.index)
            return false;

        return true;
    }



    /**
     * Returns the crystallographic system of this space group.
     * 
     * @return crystallographic system
     * @see CrystalSystem
     */
    public CrystalSystem getCrystalSystem() {
        return laueGroup.getCrystalSystem();
    }



    /**
     * Returns the array of generators associated with the space group.
     * 
     * @return generators
     */
    public Generator[] getGenerators() {
        return generators;
    }



    /**
     * Returns the index of this space group as defined in the International
     * Tables for Crystallography.
     * 
     * @return index (1 to 230)
     */
    public int getIndex() {
        return index;
    }



    /**
     * Returns the Laue group of this space group.
     * 
     * @return Laue group
     * @see LaueGroup
     */
    public LaueGroup getLaueGroup() {
        return laueGroup;
    }



    /**
     * Returns the symmetry operators of the Laue group.
     * 
     * @return symmetry operators
     */
    public Rotation[] getOperators() {
        return laueGroup.getOperators();
    }



    /**
     * Returns the Hermann-Mauguin symbol of this space group.
     * 
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        return result;
    }



    @Override
    public String toString() {
        return symbol;
    }
}
