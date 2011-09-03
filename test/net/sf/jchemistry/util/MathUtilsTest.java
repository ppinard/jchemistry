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

import org.junit.Test;

import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;

public class MathUtilsTest {

    @Test
    public void testAcos() {
        assertEquals(MathUtils.acos(4), 0, 1e-7);
        assertEquals(MathUtils.acos(-4), PI, 1e-7);
        assertEquals(MathUtils.acos(0.5), 60 / 180.0 * PI, 1e-7);
        assertEquals(MathUtils.acos(0.45675), java.lang.Math.acos(0.45675),
                1e-7);
    }

}
