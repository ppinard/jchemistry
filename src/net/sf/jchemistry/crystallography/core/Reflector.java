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

import java.util.Arrays;

import net.jcip.annotations.Immutable;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Defines a reflector.
 * 
 * @author Philippe T. Pinard
 */
@Immutable
public final class Reflector {

    /**
     * Creates a new <code>Reflector</code>. Note that the h, k, l coordinates
     * are converted to positive coordinates.
     * 
     * @param h
     *            h index of the crystallographic plane
     * @param k
     *            k index of the crystallographic plane
     * @param l
     *            l index of the crystallographic plane
     * @param phase
     *            crystal phase of the reflector
     * @param scatter
     *            scattering factors
     * @return new reflector
     */
    public static Reflector create(int h, int k, int l, Phase phase,
            ScatteringFactors scatter) {
        if (h == 0 && k == 0 & l == 0)
            throw new IllegalArgumentException("No null reflector.");

        UnitCell unitCell = phase.getUnitCell();
        AtomSites atoms = phase.getAtoms();

        Vector3D p = new Vector3D(h, k, l);
        double intensity =
                Calculations.diffractionIntensity(p, unitCell, atoms, scatter);

        return new Reflector(h, k, l, intensity);
    }



    /**
     * Calculates the hash code (unique integer representation) for the indices.
     * 
     * @param h
     *            h index of the crystallographic plane
     * @param k
     *            k index of the crystallographic plane
     * @param l
     *            l index of the crystallographic plane
     * @return hash code
     */
    protected static int calculateHashCode(int h, int k, int l) {
        final int prime = 31;
        int result = 1;
        result = prime * result + h;
        result = prime * result + k;
        result = prime * result + l;
        return result;
    }

    /** Crystallographic plane indices. */
    private final int[] indices;

    /** Diffraction intensity. */
    private final double intensity;



    /**
     * Creates a new <code>Reflector</code>. Note that the h,k,l indices will be
     * converted to positive indices, so that the first non-zero index is
     * greater than zero (e.g. <code>(-1,1,-1) -> (1,-1,1)</code>).
     * 
     * @param h
     *            h index of the crystallographic plane
     * @param k
     *            k index of the crystallographic plane
     * @param l
     *            l index of the crystallographic plane
     * @param intensity
     *            diffraction intensity intensity of the crystal
     * @throws IllegalArgumentException
     *             if h = k = l = 0
     * @throws IllegalArgumentException
     *             if the intensity is less than 0.
     */
    public Reflector(int h, int k, int l, double intensity) {
        if (h == 0 && k == 0 && l == 0)
            throw new IllegalArgumentException(
                    "Plane hkl cannot be a null vector.");
        if (intensity < 0)
            throw new IllegalArgumentException(
                    "The intensity cannot be less than 0.0.");

        // Positive indices
        int[] indices = new int[] { h, k, l };

        for (int i = 0; i < 3; i++)
            if (indices[i] == 0.0)
                continue;
            else if (indices[i] < 0.0) {
                for (int j = i; j < 3; j++)
                    indices[j] = -1 * indices[j];
                break;
            } else
                break;

        this.indices = indices;
        this.intensity = intensity;
    }



    /**
     * Check whether this reflector is made out of the specified indices.
     * 
     * @param h
     *            h index of the crystallographic plane
     * @param k
     *            k index of the crystallographic plane
     * @param l
     *            l index of the crystallographic plane
     * @return <code>true</code> if the reflector is made of the specified
     *         indices, <code>false</code> otherwise
     */
    public boolean equals(int h, int k, int l) {
        if (!Arrays.equals(indices, new int[] { h, k, l }))
            return false;

        return true;
    }



    /**
     * Two planes are equals if they have the same indices.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Reflector other = (Reflector) obj;
        if (!Arrays.equals(indices, other.indices))
            return false;

        return true;
    }



    /**
     * Returns the indices of the crystallographic plane using the Bravais
     * notation.
     * 
     * @return indices in the Bravais notation (array of length 4)
     */
    public int[] getBravaisIndices() {
        int i = -(indices[0] + indices[1]);
        return new int[] { indices[0], indices[1], i, indices[2] };
    }



    /**
     * Returns the diffracting intensity.
     * 
     * @return intensity
     */
    public double getIntensity() {
        return intensity;
    }



    /**
     * Returns the h index of the crystallographic plane.
     * 
     * @return h
     */
    public int getH() {
        return indices[0];
    }



    /**
     * Returns the k index of the crystallographic plane.
     * 
     * @return k
     */
    public int getK() {
        return indices[1];
    }



    /**
     * Returns the l index of the crystallographic plane.
     * 
     * @return l
     */
    public int getL() {
        return indices[2];
    }



    /**
     * Returns the indices of the crystallographic plane using the Miller
     * notation.
     * 
     * @return indices in the Miller notation (array of length 3)
     */
    public int[] getMillerIndices() {
        return indices;
    }



    @Override
    public int hashCode() {
        return calculateHashCode(getH(), getK(), getL());
    }



    /**
     * Returns a representation of the <code>Reflector</code>.
     * 
     * @return information about the <code>Reflector</code>
     */
    @Override
    public String toString() {
        return Arrays.toString(indices) + "\t" + intensity;
    }
}