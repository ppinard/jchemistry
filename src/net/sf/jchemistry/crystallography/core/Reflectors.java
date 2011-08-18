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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * List of <code>Reflector</code>s of a crystal.
 * 
 * @author Philippe T. Pinard
 */
public final class Reflectors extends AbstractSet<Reflector> {

    /**
     * Comparator for <code>Reflector</code> according to their intensity.
     */
    private static final Comparator<Reflector> INTENSITY_COMPARATOR =
            new Comparator<Reflector>() {

                @Override
                public int compare(Reflector refl0, Reflector refl1) {
                    return Double.compare(refl0.getIntensity(),
                            refl1.getIntensity());
                }

            };



    /**
     * Generates a new list of <code>Reflector</code>s for the given crystal.
     * The reflectors are automatically computed for all planes with indices
     * less or equal to <code>maxIndice</code>. Only diffracting plane are
     * added.
     * 
     * @param phase
     *            phase
     * @param scatter
     *            scattering factors
     * @param maxIndex
     *            maximum index of the planes to compute
     * @throws NullPointerException
     *             if the phase is null
     * @throws NullPointerException
     *             if the scattering factors is null
     * @throws IllegalArgumentException
     *             if the maxIndex is less than 0
     * @param minRelativeIntensity
     *            minimum intensity relative to the most intense reflector, i.e.
     *            percentage of the maximum intensity
     * @return reflectors for the given phase
     */
    public static Reflectors generate(Phase phase, ScatteringFactors scatter,
            int maxIndex, double minRelativeIntensity) {
        if (phase == null)
            throw new NullPointerException("Crystal cannot be null.");
        if (scatter == null)
            throw new NullPointerException("Scattering factors cannot be null.");
        if (maxIndex < 1)
            throw new IllegalArgumentException(
                    "The maximum index has to greater or equal to 1.");

        Reflectors refls = new Reflectors();

        double minIntensity =
                Calculations.maximumDiffractionIntensity(phase.getUnitCell(),
                        phase.getAtoms(), scatter) * minRelativeIntensity;

        // Find reflectors
        Reflector refl;
        for (int h = -maxIndex; h <= maxIndex; h++) {
            for (int k = -maxIndex; k <= maxIndex; k++) {
                for (int l = -maxIndex; l <= maxIndex; l++) {
                    if (h == 0 && k == 0 & l == 0)
                        continue;

                    refl = Reflector.create(h, k, l, phase, scatter);
                    if (refl.getIntensity() >= minIntensity)
                        refls.add(refl);
                }
            }
        }

        return refls;
    }

    /** Reflectors and their hash code. */
    private final HashMap<Integer, Reflector> reflectors;



    /**
     * Creates a new <code>Reflectors</code>.
     */
    public Reflectors() {
        reflectors = new HashMap<Integer, Reflector>();
    }



    /**
     * Adds a new reflector. The method returns <code>true</code> if and only
     * if:
     * <ul>
     * <li>The relative intensity of the reflector is above the threshold</li>
     * <li>A reflector with the same h, k, l values was not already added</li>
     * </ul>
     * 
     * @param reflector
     *            new reflector
     * @return <code>true</code> if the reflector was added, <code>false</code>
     *         otherwise
     */
    @Override
    public boolean add(Reflector reflector) {
        if (reflector == null)
            throw new NullPointerException("reflector == null");

        return reflectors.put(reflector.hashCode(), reflector) == null;
    }



    /**
     * Check whether this <code>Reflectors</code> object contains a
     * <code>Reflector</code> with the specified indices.
     * 
     * @param h
     *            h index of the crystallographic plane
     * @param k
     *            k index of the crystallographic plane
     * @param l
     *            l index of the crystallographic plane
     * @return <code>true</code> if a <code>Reflector</code> exists,
     *         <code>false</code> otherwise
     */
    public boolean contains(int h, int k, int l) {
        int hashCode = Reflector.calculateHashCode(h, k, l);

        return reflectors.containsKey(hashCode);
    }



    /**
     * Returns a reflector for the given plane.
     * 
     * @param h
     *            h index of the crystallographic plane
     * @param k
     *            k index of the crystallographic plane
     * @param l
     *            l index of the crystallographic plane
     * @return reflector associated to the plane
     */
    public Reflector get(int h, int k, int l) {
        int hashCode = Reflector.calculateHashCode(h, k, l);

        Reflector refl = reflectors.get(hashCode);
        if (refl == null)
            throw new IllegalArgumentException("Plane (" + h + ";" + k + ";"
                    + l + ") was not found.");
        else
            return refl;
    }



    /**
     * Returns an iterator of the reflectors sorted by their intensity. If
     * <code>reverse</code> is <code>false</code>, the order is ascending. If
     * <code>reverse</code> is <code>true</code>, the order is descending.
     * 
     * @param reverse
     *            order of the sorting
     * @return sorted iterator
     */
    public Iterator<Reflector> intensityIterator(boolean reverse) {
        List<Reflector> copy = new ArrayList<Reflector>(reflectors.values());
        Collections.sort(copy, INTENSITY_COMPARATOR);
        if (reverse)
            Collections.reverse(copy);

        return copy.iterator();
    }



    /**
     * Iterates over all the reflectors.
     * 
     * @return an iterator
     */
    @Override
    public Iterator<Reflector> iterator() {
        return reflectors.values().iterator();
    }



    /**
     * Removes a reflector.
     * 
     * @param h
     *            crystallographic plane h index
     * @param k
     *            crystallographic plane k index
     * @param l
     *            crystallographic plane l index
     * @return <code>true</code> if a reflector was removed, <code>false</code>
     *         if the specified reflector is not part of this object
     */
    public boolean remove(int h, int k, int l) {
        return reflectors.remove(Reflector.calculateHashCode(h, k, l)) != null;
    }



    @Override
    public int size() {
        return reflectors.size();
    }

};
