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

import org.apache.commons.math.linear.RealMatrix;

/**
 * Defines a unit cell. From the lattice parameters and angles, the class
 * calculates for any crystal system:
 * <ul>
 * <li>the reciprocal lattice parameters</li>
 * <li>the reciprocal lattice angles</li>
 * <li>the cell's volume</li>
 * <li>the reciprocal cell's volume</li>
 * <li>the Cartesian matrix</li>
 * <li>the metrical matrix</li>
 * </ul>
 * All these results can be access via public final variables.
 * <b>References:</b>
 * <ul>
 * <li>Mathematical Crystallography</li>
 * <li>International Crystallography Tables</li>
 * </ul>
 * 
 * @author Philippe T. Pinard
 */
@Immutable
public interface UnitCell {

    /**
     * Returns the lattice constant <code>a</code> of this unit cell in
     * angstroms.
     * 
     * @return lattice constant <code>a</code>
     */
    public double getA();



    /**
     * Returns the lattice constant <code>b</code> of this unit cell in
     * angstroms.
     * 
     * @return lattice constant <code>b</code>
     */
    public double getB();



    /**
     * Returns the lattice constant <code>c</code> of this unit cell in
     * angstroms.
     * 
     * @return lattice constant <code>c</code>
     */
    public double getC();



    /**
     * Returns the lattice angle <code>alpha</code> of this unit cell in
     * radians. This corresponds to the angle between the lattice vector
     * <code>b</code> and <code>c</code> .
     * 
     * @return lattice angle <code>alpha</code>
     */
    public double getAlpha();



    /**
     * Returns the lattice angle <code>beta</code> of this unit cell in radians.
     * This corresponds to the angle between the lattice vector <code>a</code>
     * and <code>c</code> .
     * 
     * @return lattice angle <code>beta</code>
     */
    public double getBeta();



    /**
     * Returns the lattice angle <code>gamma</code> of this unit cell in
     * radians. This corresponds to the angle between the lattice vector
     * <code>a</code> and <code>b</code> .
     * 
     * @return lattice angle <code>gamma</code>
     */
    public double getGamma();



    /**
     * Returns the volume of this unit cell in angstroms cube.
     * 
     * @return volume of this unit cell
     */
    public double getVolume();



    /**
     * Returns the lattice constant <code>a</code> of this unit cell in the
     * reciprocal space. The units are in angstroms<sup>-1</sup>.
     * 
     * @return lattice constant <code>a</code> in the reciprocal space
     */
    public double getReciprocalA();



    /**
     * Returns the lattice constant <code>b</code> of this unit cell in the
     * reciprocal space. The units are in angstroms<sup>-1</sup>.
     * 
     * @return lattice constant <code>a</code> in the reciprocal space
     */
    public double getReciprocalB();



    /**
     * Returns the lattice constant <code>c</code> of this unit cell in the
     * reciprocal space. The units are in angstroms<sup>-1</sup>.
     * 
     * @return lattice constant <code>a</code> in the reciprocal space
     */
    public double getReciprocalC();



    /**
     * Returns the lattice angle <code>alpha</code> of this unit cell in the
     * reciprocal space. The units are in radians. This corresponds to the angle
     * between the lattice vector <code>b*</code> and <code>c*</code> .
     * 
     * @return lattice angle <code>alpha</code> in the reciprocal space
     */
    public double getReciprocalAlpha();



    /**
     * Returns the lattice angle <code>beta</code> of this unit cell in the
     * reciprocal space. The units are in radians. This corresponds to the angle
     * between the lattice vector <code>a*</code> and <code>c*</code> .
     * 
     * @return lattice angle <code>beta</code> in the reciprocal space
     */
    public double getReciprocalBeta();



    /**
     * Returns the lattice angle <code>gamma</code> of this unit cell in the
     * reciprocal space. The units are in radians. This corresponds to the angle
     * between the lattice vector <code>a*</code> and <code>b*</code> .
     * 
     * @return lattice angle <code>gamma</code> in the reciprocal space
     */
    public double getReciprocalGamma();



    /**
     * Returns the volume of this unit cell in the reciprocal space. The units
     * are angstroms<sup>-3</sup>.
     * 
     * @return volume of this unit cell in the reciprocal space
     */
    public double getReciprocalVolume();



    /**
     * Returns a copy of the metrical matrix. The values in the matrix are in
     * angstroms.
     * 
     * @return metrical matrix
     */
    public RealMatrix getMetricalMatrix();



    /**
     * Returns a copy of the Cartesian matrix. The values in the matrix are in
     * angstroms.
     * 
     * @return metrical matrix
     */
    public RealMatrix getCartesianMatrix();
}