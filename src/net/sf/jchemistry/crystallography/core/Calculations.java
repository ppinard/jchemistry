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

import net.sf.jchemistry.util.MathUtils;

import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.geometry.Rotation;
import org.apache.commons.math.geometry.Vector3D;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

import edu.umd.cs.findbugs.annotations.CheckReturnValue;

import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static net.sf.jchemistry.crystallography.core.Constants.C;
import static net.sf.jchemistry.crystallography.core.Constants.CHARGE_ELECTRON;
import static net.sf.jchemistry.crystallography.core.Constants.H;
import static net.sf.jchemistry.crystallography.core.Constants.MASS_ELECTRON;
import static net.sf.jchemistry.util.CommonMathUtils.postMultiply;
import static net.sf.jchemistry.util.CommonMathUtils.toRealVector;
import static net.sf.jchemistry.util.CommonMathUtils.toVector3D;

/**
 * Operations on <code>AtomSite</code>, <code>AtomSites</code>,
 * <code>UnitCell</code>, <code>Plane</code>. Common crystallographic
 * calculations that are used in other classes.
 * <p/>
 * References for some of these calculations were taken from:
 * <ul>
 * <li>Mathematical Crystallography</li>
 * </ul>
 * 
 * @author Philippe T. Pinard
 */
public final class Calculations {

    /**
     * Constructor to prevent the class to be instantiated.
     */
    private Calculations() {

    }



    /**
     * Returns the bond angle (in radians) between the vector from
     * <code>atom1</code> to <code>atom2</code> and the vector from
     * <code>atom1</code> to <code>atom3</code>.
     * <p/>
     * <b>References:</b>
     * <ul>
     * <li>Equation 1.13 from Mathematical Crystallography</li>
     * </ul>
     * 
     * @param atom1
     *            middle atom
     * @param atom2
     *            end atom
     * @param atom3
     *            end atom
     * @param unitCell
     *            unit cell of the atoms
     * @return bond angle (in radians)
     */
    public static double bondAngle(AtomSite atom1, AtomSite atom2,
            AtomSite atom3, UnitCell unitCell) {
        Vector3D atom1Vector = atom1.getPosition();
        Vector3D atom2Vector = atom2.getPosition();
        Vector3D atom3Vector = atom3.getPosition();
        RealMatrix metricalMatrix = unitCell.getMetricalMatrix();

        // Inter-atoms vectors
        RealVector vector12 = toRealVector(atom1Vector.subtract(atom2Vector));
        RealVector vector13 = toRealVector(atom1Vector.subtract(atom3Vector));

        // Dot product
        double dotproduct =
                vector12.dotProduct(postMultiply(metricalMatrix, vector13));

        // Bond distance
        double bonddistance12 = bondDistance(atom1, atom2, unitCell);
        double bonddistance13 = bondDistance(atom1, atom3, unitCell);

        // Cosine
        double cosine = dotproduct / (bonddistance12 * bonddistance13);

        // Angle
        double angle = MathUtils.acos(cosine);

        return angle;
    }



    /**
     * Returns the distance (in angstroms) between <code>atom1</code> and
     * <code>atom2</code> of a given <code>unitCell</code>.
     * <p/>
     * <b>References:</b>
     * <ul>
     * <li>Equation 1.13 from Mathematical Crystallography</li>
     * </ul>
     * 
     * @param atom1
     *            first atom
     * @param atom2
     *            second atom
     * @param unitCell
     *            unit cell of the atoms
     * @return bond distance (in angstroms)
     */
    public static double bondDistance(AtomSite atom1, AtomSite atom2,
            UnitCell unitCell) {
        Vector3D atom1vector = atom1.getPosition();
        Vector3D atom2vector = atom2.getPosition();
        RealMatrix metricalMatrix = unitCell.getMetricalMatrix();

        // Vector between atom1 and atom2
        RealVector vector = toRealVector(atom2vector.subtract(atom1vector));

        // Distance square
        double distanceSquare =
                vector.dotProduct(postMultiply(metricalMatrix, vector));

        // Distance
        double distance = sqrt(distanceSquare);

        return distance;
    }



    /**
     * Returns the first order (n=1) diffraction angle based on Bragg's Law.
     * 
     * @param planeSpacing
     *            plane spacing of diffracting plane
     * @param wavelength
     *            wavelength of wave
     * @return diffraction angle (in radians)
     */
    public static double diffractionAngle(double planeSpacing, double wavelength) {
        return diffractionAngle(planeSpacing, wavelength, 1.0);
    }



    /**
     * Returns the diffraction angle based on Bragg's Law.
     * 
     * @param planeSpacing
     *            plane spacing of diffracting plane
     * @param wavelength
     *            wavelength of wave
     * @param order
     *            diffraction order
     * @return diffraction angle (in radians)
     */
    public static double diffractionAngle(double planeSpacing,
            double wavelength, double order) {
        return asin(order * wavelength / (2 * planeSpacing));
    }



    /**
     * Returns the diffraction intensity (I) for a given plane, set of atoms and
     * scattering factors.
     * 
     * @param plane
     *            crystallography plane
     * @param unitCell
     *            unit cell containing the plane
     * @param atomSites
     *            atoms contained in the unit cell
     * @param scatteringFactors
     *            scattering factors to calculate for the form factor
     * @return diffraction intensity
     */
    public static double diffractionIntensity(Vector3D plane,
            UnitCell unitCell, AtomSites atomSites,
            ScatteringFactors scatteringFactors) {
        Complex formFactor =
                formFactor(plane, unitCell, atomSites, scatteringFactors);

        return formFactor.multiply(formFactor.conjugate()).getReal();
    }



    /**
     * Returns the relativistic electron wavelength.
     * 
     * @param energy
     *            energy (in eV)
     * @return electron wavelength (in angstroms)
     * @throws IllegalArgumentException
     *             if the energy is less than 0
     */
    public static double electronWavelength(double energy) {
        if (energy < 0)
            throw new IllegalArgumentException("energy (" + energy + ") < 0");

        double a = H / sqrt(2 * MASS_ELECTRON * CHARGE_ELECTRON);
        double b = 2 * CHARGE_ELECTRON / (MASS_ELECTRON * pow(C, 2));

        return a / sqrt(energy + b * pow(energy, 2)) * 1e10;
    }



    /**
     * Applies the space group symmetry on the specified atom positions and
     * returns all the possible equivalent atom positions.
     * 
     * @param atomSites
     *            positions of atoms
     * @param spaceGroup
     *            space group
     * @return all possible atom positions
     */
    @CheckReturnValue
    public static AtomSites equivalentPositions(AtomSites atomSites,
            SpaceGroup spaceGroup) {
        AtomSites newAtomSites = new AtomSites();

        for (AtomSite atom : atomSites)
            for (Generator generator : spaceGroup.getGenerators())
                newAtomSites.add(generator.apply(atom));

        return newAtomSites;
    }



    /**
     * Returns the form factor (F) for a given plane, set of atoms and
     * scattering factors.
     * 
     * @param plane
     *            crystallography plane
     * @param unitCell
     *            unit cell containing the plane
     * @param atomSites
     *            atoms contained in the unit cell
     * @param scatteringFactors
     *            scattering factors to calculate for the form factor
     * @return form factor (complex form)
     */
    public static Complex formFactor(Vector3D plane, UnitCell unitCell,
            AtomSites atomSites, ScatteringFactors scatteringFactors) {
        Complex f = new Complex(0.0, 0.0); // Form factor
        double s = 2 * Math.PI / planeSpacing(plane, unitCell);

        double fi;
        Complex x;
        for (AtomSite atom : atomSites) {
            fi =
                    scatteringFactors.getIntensity(atom.getElement(),
                            atom.getCharge(), s);
            x =
                    new Complex(0.0, 2 * PI
                            * Vector3D.dotProduct(plane, atom.getPosition()));

            f = f.add(x.exp().multiply(fi)); // f += fi * exp(x);
        }

        return f;
    }



    /**
     * Returns the interplanar direction cosine between <code>plane1</code> and
     * <code>plane2</code> of a unit cell.
     * 
     * @param plane1
     *            first crystallographic plane
     * @param plane2
     *            second crystallographic plane
     * @param unitCell
     *            unitCell unit cell containing the planes
     * @return angle between plane1 and plane2 (in radians)
     */
    public static double interplanarAngle(Vector3D plane1, Vector3D plane2,
            UnitCell unitCell) {
        return MathUtils.acos(interplanarCosine(plane1, plane2, unitCell));
    }



    /**
     * Returns the interplanar cosine between <code>plane1</code> and
     * <code>plane2</code> of a unit cell.
     * 
     * @param plane1
     *            first crystallographic plane
     * @param plane2
     *            second crystallographic plane
     * @param unitCell
     *            unitCell unit cell containing the planes
     * @return direction cosine between plane1 and plane2 (in radians)
     */
    public static double interplanarCosine(Vector3D plane1, Vector3D plane2,
            UnitCell unitCell) {
        RealMatrix cartesianMatrix = unitCell.getCartesianMatrix();
        RealMatrix b =
                new LUDecompositionImpl(cartesianMatrix.transpose()).getSolver().getInverse();

        RealVector plane1C = postMultiply(b, toRealVector(plane1));
        RealVector plane2C = postMultiply(b, toRealVector(plane2));

        return plane1C.dotProduct(plane2C)
                / (plane1C.getNorm() * plane2C.getNorm());
    }



    /**
     * Check if a plane is diffracting. The intensity has to be greater than
     * fraction * (maximum intensity) to be diffracting.
     * 
     * @param intensity
     *            intensity of the plane
     * @param maxIntensity
     *            maximum intensity of a set of atoms and unit cell
     * @param fraction
     *            discriminating fraction to say that a plane is diffracting
     * @return <code>true</code> if intensity is greater than fraction *
     *         (maximum intensity)
     */
    protected static boolean isDiffracting(double intensity,
            double maxIntensity, double fraction) {
        double lowerlimit = fraction * maxIntensity;

        return intensity > lowerlimit;
    }



    /**
     * Checks if a plane is diffracting for a given set of atom sites and the
     * specified scattering factors.
     * <p/>
     * The intensity has to be greater than fraction * (maximum intensity) to be
     * diffracting.
     * 
     * @param plane
     *            crystallography plane
     * @param unitCell
     *            unit cell containing the plane
     * @param atomSites
     *            atoms contained in the unit cell
     * @param scatteringFactors
     *            scattering factors to calculate for the form factor
     * @param fraction
     *            discriminating fraction to say that a plane is diffracting
     * @return <code>true</code> plane is diffraction
     */
    public static boolean isDiffracting(Vector3D plane, UnitCell unitCell,
            AtomSites atomSites, ScatteringFactors scatteringFactors,
            double fraction) {
        double maxIntensity =
                maximumDiffractionIntensity(unitCell, atomSites,
                        scatteringFactors);
        double intensity =
                diffractionIntensity(plane, unitCell, atomSites,
                        scatteringFactors);

        return isDiffracting(intensity, maxIntensity, fraction);

    }



    /**
     * Returns the maximum diffraction intensity (I) for a set of atoms and
     * scattering factors.
     * 
     * @param unitCell
     *            unit cell containing the plane
     * @param atomSites
     *            atoms contained in the unit cell
     * @param scatteringFactors
     *            scattering factors to calculate for the form factor
     * @return maximum diffraction intensity
     */
    public static double maximumDiffractionIntensity(UnitCell unitCell,
            AtomSites atomSites, ScatteringFactors scatteringFactors) {
        Complex formFactor =
                maximumFormFactor(unitCell, atomSites, scatteringFactors);

        double intensity =
                formFactor.multiply(formFactor.conjugate()).getReal();

        return intensity;
    }



    /**
     * Returns the maximum value of the form factor for a given unit cell, atom
     * sites and scattering factors.
     * 
     * @param unitCell
     *            unit cell containing the plane
     * @param atomSites
     *            atoms contained in the unit cell
     * @param scatteringFactors
     *            scattering factors to calculate for the form factor
     * @return maximum form factor (complex form)
     */
    public static Complex maximumFormFactor(UnitCell unitCell,
            AtomSites atomSites, ScatteringFactors scatteringFactors) {
        // NOTE: The maximum form factor is a real number, but a Complex number
        // is used for consistency with the form factor.
        double f = 0.0;

        for (AtomSite atom : atomSites) {
            f +=
                    scatteringFactors.getMaxIntensity(atom.getElement(),
                            atom.getCharge());
        }

        return new Complex(f, 0.0);
    }



    /**
     * Returns the plane spacing (in angstroms) between two adjacent planes of a
     * unit cell.
     * <p/>
     * <b>References:</b>
     * <ul>
     * <li>Mathematical Crystallography</li>
     * </ul>
     * 
     * @param plane
     *            crystallographic plane
     * @param unitCell
     *            unit cell containing the plane
     * @return plane spacing (in angstroms)
     */
    public static double planeSpacing(Vector3D plane, UnitCell unitCell) {
        RealMatrix metricalMatrix = unitCell.getMetricalMatrix();
        RealMatrix matrix =
                new LUDecompositionImpl(metricalMatrix).getSolver().getInverse();
        RealVector p = toRealVector(plane);

        // s square (d = 1/s^2)
        double sSquare = p.dotProduct(postMultiply(matrix, p));

        // plane spacing d
        double d = 1.0 / sqrt(sSquare);

        return d;
    }



    /**
     * Returns the plane normal expressed as a vector in Cartesian space.
     * <p/>
     * <b>References:</b>
     * <ul>
     * <li>http://www.mse.mtu.edu/casting/my3200/stereo/sg4.html</li>
     * </ul>
     * 
     * @param indices
     *            indices of the plane expressed in terms of the three crystal
     *            unit vectors
     * @param unitCell
     *            unit cell of the crystal
     * @return plane normal
     */
    public static Vector3D planeNormal(Vector3D indices, UnitCell unitCell) {
        RealMatrix cartesianMatrix = unitCell.getCartesianMatrix();
        RealVector v = toRealVector(indices);

        return toVector3D(postMultiply(cartesianMatrix, v));
    }



    /**
     * Reduce a rotation to its fundamental group based on the given point
     * group.
     * 
     * @param q
     *            a rotation
     * @param lg
     *            a Laue group
     * @return reduced rotation
     */
    @CheckReturnValue
    public static Rotation reduce(Rotation q, LaueGroup lg) {
        Rotation equiv =
                new Rotation(q.getQ0(), q.getQ1(), q.getQ2(), q.getQ3(), false);

        Rotation out;
        for (Rotation op : lg.getOperators()) {
            out = op.applyTo(q);
            if (Math.abs(out.getQ0()) > Math.abs(equiv.getQ0()))
                equiv = out;
        }

        return equiv;
    }



    /**
     * Reduce a rotation to its fundamental group based on the given point
     * group.
     * 
     * @param q
     *            a rotation
     * @param sg
     *            a space group
     * @return reduced rotation
     */
    @CheckReturnValue
    public static Rotation reduce(Rotation q, SpaceGroup sg) {
        return reduce(q, sg.getLaueGroup());
    }



    /**
     * Returns the zone axis of <code>plane1</code> and <code>plane2</code> of a
     * unit cell.
     * <p/>
     * <b>References:</b>
     * <ul>
     * <li>Theorem 2.14 from Mathematical Crystallography</li>
     * </ul>
     * 
     * @param plane1
     *            first crystallographic plane
     * @param plane2
     *            second crystallographic plane
     * @param unitCell
     *            unit cell containing the planes
     * @return zone axis created by plane1 and plane2
     */
    public static Vector3D zoneAxis(Vector3D plane1, Vector3D plane2,
            UnitCell unitCell) {
        double volumeReciprocal = unitCell.getReciprocalVolume();
        RealMatrix metricalMatrix = unitCell.getMetricalMatrix();
        RealVector p1 = toRealVector(plane1);
        RealVector p2 = toRealVector(plane2);

        Vector3D s1 = toVector3D(postMultiply(metricalMatrix, p1));
        Vector3D s2 = toVector3D(postMultiply(metricalMatrix, p2));

        Vector3D crossproduct = Vector3D.crossProduct(s1, s2);

        return crossproduct.scalarMultiply(volumeReciprocal);
    }

}
