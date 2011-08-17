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

import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

import static java.lang.Math.*;

/**
 * Factory to create the 7 crystal system's unit cell.
 * 
 * @author Philippe T. Pinard
 */
public class UnitCellFactory {

    /**
     * Internal implementation of the {@link UnitCell} interface.
     * 
     * @author Philippe T. Pinard
     */
    private static class UnitCellImpl implements UnitCell {
        /** Lattice constant a. */
        private final double a;

        /** Lattice constant b. */
        private final double b;

        /** Lattice constant c. */
        private final double c;

        /** Lattice angle alpha. */
        private final double alpha;

        /** Lattice angle beta. */
        private final double beta;

        /** Lattice angle gamma. */
        private final double gamma;

        /** Lattice volume. */
        private final double volume;

        /** Reciprocal lattice constant a. */
        private final double aR;

        /** Reciprocal lattice constant b. */
        private final double bR;

        /** Reciprocal lattice constant c. */
        private final double cR;

        /** Reciprocal lattice angle alpha. */
        private final double alphaR;

        /** Reciprocal lattice angle beta. */
        private final double betaR;

        /** Reciprocal lattice angle gamma. */
        private final double gammaR;

        /** Reciprocal lattice volume. */
        private final double volumeR;

        /** Metrical matrix. */
        private final RealMatrix metricalMatrix;

        /** Cartesian matrix. */
        private final RealMatrix cartesianMatrix;



        /**
         * Creates a new unit cell.
         * 
         * @param a
         *            lattice dimension a (in angstroms)
         * @param b
         *            lattice dimension b (in angstroms)
         * @param c
         *            lattice dimension c (in angstroms)
         * @param alpha
         *            angle between b and c (in radians)
         * @param beta
         *            angle between a and c (in radians)
         * @param gamma
         *            angle between a and b (in radians)
         */
        public UnitCellImpl(double a, double b, double c, double alpha,
                double beta, double gamma) {
            if (a <= 0)
                throw new IllegalArgumentException("Lattice constant a (" + a
                        + ") must be greater than 0.");
            if (b <= 0)
                throw new IllegalArgumentException("Lattice constant b (" + b
                        + ") must be greater than 0.");
            if (c <= 0)
                throw new IllegalArgumentException("Lattice constant c (" + c
                        + ") must be greater than 0.");
            if (alpha <= 0 || alpha >= PI)
                throw new IllegalArgumentException("Lattice angle alpha ("
                        + alpha + ") must be between ]0,PI[.");
            if (beta <= 0 || beta >= PI)
                throw new IllegalArgumentException("Lattice angle beta ("
                        + beta + ") must be between ]0,PI[.");
            if (gamma <= 0 || gamma >= PI)
                throw new IllegalArgumentException("Lattice angle gamma ("
                        + gamma + ") must be between ]0,PI[.");

            this.a = a;
            this.b = b;
            this.c = c;
            this.alpha = alpha;
            this.beta = beta;
            this.gamma = gamma;

            // Calculate reciprocal angles
            alphaR =
                    acos((cos(beta) * cos(gamma) - cos(alpha))
                            / (sin(beta) * sin(gamma)));
            betaR =
                    acos((cos(alpha) * cos(gamma) - cos(beta))
                            / (sin(alpha) * sin(gamma)));
            gammaR =
                    acos((cos(alpha) * cos(beta) - cos(gamma))
                            / (sin(alpha) * sin(beta)));

            // Calculate metrical matrix
            double g11 = a * a;
            double g12 = a * b * cos(gamma);
            double g13 = a * c * cos(beta);
            double g21 = g12;
            double g22 = b * b;
            double g23 = b * c * cos(alpha);
            double g31 = g13;
            double g32 = g23;
            double g33 = c * c;

            double[][] m =
                    new double[][] { { g11, g12, g13 }, { g21, g22, g23 },
                            { g31, g32, g33 } };
            metricalMatrix = MatrixUtils.createRealMatrix(m);

            // Calculate cartesian matrix
            double h11 = a * sin(beta);
            double h12 = -b * sin(alpha) * cos(gammaR);
            double h13 = 0;
            double h21 = 0;
            double h22 = b * sin(alpha) * sin(gammaR);
            double h23 = 0;
            double h31 = a * cos(beta);
            double h32 = b * cos(alpha);
            double h33 = c;

            double[][] cm =
                    new double[][] { { h11, h12, h13 }, { h21, h22, h23 },
                            { h31, h32, h33 } };
            cartesianMatrix = MatrixUtils.createRealMatrix(cm);

            // Calculate volume
            double det =
                    m[0][0] * (m[1][1] * m[2][2] - m[2][1] * m[1][2]) - m[1][0]
                            * (m[0][1] * m[2][2] - m[2][1] * m[0][2]) + m[2][0]
                            * (m[0][1] * m[1][2] - m[1][1] * m[0][2]);
            volume = sqrt(det);

            // Calculate reciprocal volume
            volumeR = 1.0 / volume;

            // Calculate reciprocal bases
            aR = b * c * sin(alpha) / volume;
            bR = a * c * sin(beta) / volume;
            cR = a * b * sin(gamma) / volume;
        }



        @Override
        public double getA() {
            return a;
        }



        @Override
        public double getAlpha() {
            return alpha;
        }



        @Override
        public double getB() {
            return b;
        }



        @Override
        public double getBeta() {
            return beta;
        }



        @Override
        public double getC() {
            return c;
        }



        /**
         * Returns a copy of the Cartesian matrix as a <code>RealMatrix</code>.
         * 
         * @return metrical matrix
         */
        @Override
        public RealMatrix getCartesianMatrix() {
            return cartesianMatrix.copy();
        }



        @Override
        public double getGamma() {
            return gamma;
        }



        /**
         * Returns a copy of the metrical matrix as a <code>RealMatrix</code>.
         * 
         * @return metrical matrix
         */
        @Override
        public RealMatrix getMetricalMatrix() {
            return metricalMatrix.copy();
        }



        @Override
        public double getReciprocalA() {
            return aR;
        }



        @Override
        public double getReciprocalAlpha() {
            return alphaR;
        }



        @Override
        public double getReciprocalB() {
            return bR;
        }



        @Override
        public double getReciprocalBeta() {
            return betaR;
        }



        @Override
        public double getReciprocalC() {
            return cR;
        }



        @Override
        public double getReciprocalGamma() {
            return gammaR;
        }



        @Override
        public double getReciprocalVolume() {
            return volumeR;
        }



        @Override
        public double getVolume() {
            return volume;
        }



        /**
         * Returns a representation of the unit cell.
         * 
         * @return information of the <code>UnitCell</code>
         */
        @Override
        public String toString() {
            return "UnitCell [a=" + a + ", b=" + b + ", c=" + c + ", alpha="
                    + alpha + " rad, beta=" + beta + " rad, gamma=" + gamma
                    + " rad]";
        }

    }



    /**
     * Returns a cubic unit cell. * a = b = c * alpha = beta = gamma = pi/2
     * 
     * @param a
     *            unit cell parameter in angstroms
     * @return a cubic unit cell
     */
    public static UnitCell cubic(double a) {
        return new UnitCellImpl(a, a, a, PI / 2.0, PI / 2.0, PI / 2.0);
    }



    /**
     * Returns a hexagonal unit cell. * a = b * alpha = beta = pi/2, gamma ==
     * 2pi/3
     * 
     * @param a
     *            unit cell parameter in angstroms
     * @param c
     *            unit cell parameter in angstroms
     * @return a hexagonal unit cell
     */
    public static UnitCell hexagonal(double a, double c) {
        return new UnitCellImpl(a, a, c, PI / 2.0, PI / 2.0, 2.0 * PI / 3.0);
    }



    /**
     * Returns a monoclinic unit cell.
     * 
     * @param a
     *            unit cell parameter in angstroms
     * @param b
     *            unit cell parameter in angstroms
     * @param c
     *            unit cell parameter in angstroms
     * @param beta
     *            unit cell angle in radians
     * @return a monoclinic unit cell
     */
    public static UnitCell monoclinic(double a, double b, double c, double beta) {
        return new UnitCellImpl(a, b, c, PI / 2.0, beta, PI / 2.0);
    }



    /**
     * Returns a orthorhombic unit cell. * a != b != c * alpha = beta = gamma =
     * pi/2
     * 
     * @param a
     *            unit cell parameter in angstroms
     * @param b
     *            unit cell parameter in angstroms
     * @param c
     *            unit cell parameter in angstroms
     * @return a orthorhombic unit cell
     */
    public static UnitCell orthorhombic(double a, double b, double c) {
        return new UnitCellImpl(a, b, c, PI / 2.0, PI / 2.0, PI / 2.0);
    }



    /**
     * Returns a tetragonal unit cell. * a = b * alpha = beta = gamma = pi/2
     * 
     * @param a
     *            unit cell parameter in angstroms
     * @param c
     *            unit cell parameter in angstroms
     * @return a tetragonal unit cell
     */
    public static UnitCell tetragonal(double a, double c) {
        return new UnitCellImpl(a, a, c, PI / 2.0, PI / 2.0, PI / 2.0);
    }



    /**
     * Returns a triclinic unit cell. * a != b != c * alpha != beta != gamma
     * 
     * @param a
     *            unit cell parameter in angstroms
     * @param b
     *            unit cell parameter in angstroms
     * @param c
     *            unit cell parameter in angstroms
     * @param alpha
     *            unit cell angle in radians
     * @param beta
     *            unit cell angle in radians
     * @param gamma
     *            unit cell angle in radians
     * @return a triclinic unit cell
     */
    public static UnitCell triclinic(double a, double b, double c,
            double alpha, double beta, double gamma) {
        return new UnitCellImpl(a, b, c, alpha, beta, gamma);
    }



    /**
     * Returns a trigonal unit cell. * a = b = c * alpha = beta = gamma
     * 
     * @param a
     *            unit cell parameter in angstroms
     * @param alpha
     *            unit cell angle in radians
     * @return a trigonal unit cell
     */
    public static UnitCell trigonal(double a, double alpha) {
        return new UnitCellImpl(a, a, a, alpha, alpha, alpha);
    }
}
