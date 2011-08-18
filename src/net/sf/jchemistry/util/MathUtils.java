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
package net.sf.jchemistry.util;

import static java.lang.Math.PI;

/**
 * Mathematical utilities.
 * 
 * @author Philippe T. Pinard
 */
public final class MathUtils {

    /**
     * Constructor to prevent the class to be instantiated.
     */
    private MathUtils() {

    }



    /**
     * Overrides {@link java.lang.Math#acos(double)} to prevent rounding errors
     * when the <code>angle</code> is slightly greater than 1.0 or less than
     * -1.0.
     * 
     * @param angle
     *            in radians
     * @return a value between 0.0 and PI.
     */
    public static double acos(double angle) {
        if (Double.isNaN(angle))
            throw new IllegalArgumentException("NaN cannot be evaluated");

        if (angle > 1.0)
            return 0.0;
        else if (angle < -1.0)
            return PI;
        else
            return java.lang.Math.acos(angle);
    }
}
