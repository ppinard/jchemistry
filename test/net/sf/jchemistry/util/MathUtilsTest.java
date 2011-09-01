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
