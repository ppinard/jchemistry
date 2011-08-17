package net.sf.jchemistry.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.sf.jchemistry.crystallography.core.ScatteringFactors;

/**
 * Utilities for input/output.
 * 
 * @author ppinard
 */
public final class IOUtils {

    /**
     * Returns the reader for the specified filename. The filename is a relative
     * path to a data file located in the class path. See
     * {@link ClassLoader#getResource(String)} for more information about the
     * filename.
     * 
     * @param filename
     *            filename
     * @return reader for the file
     */
    public static Reader getReader(String filename) {
        ClassLoader cl = ScatteringFactors.class.getClassLoader();
        if (cl == null) // If bootstrap classloader
            cl = ClassLoader.getSystemClassLoader();

        InputStream reader = cl.getResourceAsStream(filename);
        if (reader == null)
            throw new IllegalArgumentException("Filename (" + filename
                    + ") not found.");

        return new InputStreamReader(reader);
    }



    /**
     * Constructor to prevent the class to be instantiated.
     */
    private IOUtils() {

    }
}
