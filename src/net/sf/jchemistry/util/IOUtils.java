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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import net.sf.jchemistry.crystallography.core.ScatteringFactors;

/**
 * Utilities for input/output.
 * 
 * @author Philippe T. Pinard
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
        try {
            return new InputStreamReader(getURL(filename).openStream());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Stream of filename ("
                    + filename + ") cannot be open", ex);
        }
    }



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
    public static URL getURL(String filename) {
        ClassLoader cl = ScatteringFactors.class.getClassLoader();
        if (cl == null) // If bootstrap classloader
            cl = ClassLoader.getSystemClassLoader();

        URL url = cl.getResource(filename);
        if (url == null)
            throw new IllegalArgumentException("Filename (" + filename
                    + ") not found.");

        return url;
    }



    /**
     * Constructor to prevent the class to be instantiated.
     */
    private IOUtils() {

    }
}
