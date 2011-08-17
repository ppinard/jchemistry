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

import net.sf.jchemistry.core.Element;

/**
 * Interface for database of scattering factors.
 * 
 * @author Philippe T. Pinard
 */
public interface ScatteringFactors {

    /**
     * Returns the atomic form factor for the given element and momentum
     * transfer (s). The momentum transfer is calculated either from the
     * scattering angle (\theta) and wavelength (\lambda) or from the plane
     * spacing (d): <br/>
     * <code>s = 4\pi\sin\theta / \lambda = 2\pi / d</code>
     * <p/>
     * Note that if no data is available for the specified charge, the intensity
     * for the element with no charge is returned.
     * 
     * @param element
     *            element of the atom
     * @param charge
     *            electron charge of the atom
     * @param s
     *            momentum transfer (in angstroms<sup>-1</sup>)
     * @return scattering factor intensity (in angstroms)
     * @throws NullPointerException
     *             if element is null
     * @throws IllegalArgumentException
     *             if no scattering factor exists for the specified element
     * @throws IllegalArgumentException
     *             if the momentum transfer is less than 0
     */
    public double getIntensity(Element element, int charge, double s);



    /**
     * Returns the maximum intensity of all scattering factors for the specified
     * element. It is the intensity at <code>s = 0</code>.
     * <p/>
     * Note that this method is required as the calculation for
     * <code>f(s=0)</code> may differ from <code>f(s>0)</code>.
     * 
     * @param element
     *            element of the atom
     * @param charge
     *            electron charge of the atom
     * @return maximum intensity of the scattering factors (in angstroms)
     * @throws IllegalArgumentException
     *             if no scattering factor exists for the specified element
     */
    public double getMaxIntensity(Element element, int charge);

}
