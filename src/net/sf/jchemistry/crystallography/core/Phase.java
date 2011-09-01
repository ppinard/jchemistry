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

import net.sf.jchemistry.util.Citable;

/**
 * Phase.
 * 
 * @author Philippe T. Pinard
 */
public class Phase implements Citable {

    /** Common or mineral name. */
    private final String name;

    /** Reference to the literature. */
    private final String citation;

    /** Space group. */
    private final SpaceGroup spaceGroup;

    /** Unit cell. */
    private final UnitCell unitCell;

    /** Atom positions. */
    private final AtomSites atoms;

    /** Reflectors. */
    private final Reflectors reflectors;



    /**
     * Creates a new <code>Phase</code>.
     * 
     * @param name
     *            common name or mineral name
     * @param citation
     *            reference to the literature
     * @param spaceGroup
     *            space group
     * @param unitCell
     *            unit cell
     */
    public Phase(String name, String citation, SpaceGroup spaceGroup,
            UnitCell unitCell) {
        if (name == null)
            throw new NullPointerException("name == null");
        if (name.isEmpty())
            throw new IllegalArgumentException("Name is an empty string.");
        this.name = name;

        if (citation == null)
            throw new NullPointerException("citation == null");
        this.citation = citation;

        if (spaceGroup == null)
            throw new NullPointerException("space group == null");
        this.spaceGroup = spaceGroup;

        if (unitCell == null)
            throw new NullPointerException("unit cell == null");
        this.unitCell = unitCell;

        atoms = new AtomSites();
        reflectors = new Reflectors();
    }



    @Override
    public String toString() {
        return name;
    }



    /**
     * Creates a new <code>Phase</code>.
     * 
     * @param name
     *            common name or mineral name
     * @param spaceGroup
     *            space group
     * @param unitCell
     *            unit cell
     */
    public Phase(String name, SpaceGroup spaceGroup, UnitCell unitCell) {
        this(name, "No reference", spaceGroup, unitCell);
    }



    /**
     * Returns the name of this phase. It can either be the common name or
     * mineral name.
     * 
     * @return name of this phase.
     */
    public String getName() {
        return name;
    }



    @Override
    public String getCitation() {
        return citation;
    }



    /**
     * Returns the unit cell of this phase.
     * 
     * @return unit cell
     */
    public UnitCell getUnitCell() {
        return unitCell;
    }



    /**
     * Returns the atom positions of this phase. The equivalent positions based
     * on the space group are automatically calculated when this method is
     * called.
     * 
     * @return atom positions
     */
    public AtomSites getAtoms() {
        // Calculate equivalent positions
        for (AtomSite atom : new ArrayList<AtomSite>(atoms))
            for (Generator generator : spaceGroup.getGenerators())
                atoms.add(generator.apply(atom));

        return atoms;
    }



    /**
     * Returns the space group of this phase.
     * 
     * @return space group
     */
    public SpaceGroup getSpaceGroup() {
        return spaceGroup;
    }



    /**
     * Returns the reflectors of this phase. To automatically compute the
     * reflectors based on {@link ScatteringFactors}, refer to
     * {@link #computeReflectors(ScatteringFactors, int, double)}.
     * 
     * @return reflectors
     */
    public Reflectors getReflectors() {
        return reflectors;
    }



    /**
     * Computes the reflectors based on the given scattering factors and maximum
     * index.
     * 
     * @param scatter
     *            scattering factors
     * @param maxIndex
     *            maximum index of the planes to compute
     * @param minRelativeIntensity
     *            minimum intensity relative to the most intense reflector, i.e.
     *            percentage of the maximum intensity
     */
    public void computeReflectors(ScatteringFactors scatter, int maxIndex,
            double minRelativeIntensity) {
        reflectors.clear();
        reflectors.addAll(Reflectors.generate(getUnitCell(), getAtoms(),
                scatter, maxIndex, minRelativeIntensity));
    }

}
