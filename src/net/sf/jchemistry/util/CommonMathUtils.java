package net.sf.jchemistry.util;

import org.apache.commons.math.geometry.Vector3D;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealVector;

/**
 * Utilities for the Apache common math library.
 * 
 * @author ppinard
 */
public final class CommonMathUtils {

    /**
     * Converts a <code>Vector3D</code> to a <code>RealVector</code>.
     * 
     * @param v
     *            a vector 3D
     * @return a real vector
     */
    public static RealVector toRealVector(Vector3D v) {
        return new ArrayRealVector(
                new double[] { v.getX(), v.getY(), v.getZ() });
    }



    /**
     * Converts a <code>RealVector</code> to a <code>Vector3D</code>.
     * 
     * @param v
     *            a real vector
     * @return a vector 3D
     */
    public static Vector3D toVector3D(RealVector v) {
        if (v.getDimension() != 3)
            throw new IllegalArgumentException("The vector length ("
                    + v.getDimension() + ") must be equal to 3.");

        return new Vector3D(v.getEntry(0), v.getEntry(1), v.getEntry(2));
    }



    /**
     * Multiplies the specified matrix by the specified COLUMN vector.
     * <p/>
     * The <code>RealMatrix</code> interface only allows multiplication of
     * matrix by ROW vector.
     * 
     * @param m
     *            matrix
     * @param v
     *            vector
     * @return resultant COLUMN vector
     */
    public static RealVector postMultiply(RealMatrix m, RealVector v) {
        return m.transpose().preMultiply(v);
    }



    /**
     * Constructor to prevent the class to be instantiated.
     */
    private CommonMathUtils() {
    }
}
