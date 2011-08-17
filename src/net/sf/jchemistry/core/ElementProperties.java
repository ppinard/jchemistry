package net.sf.jchemistry.core;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import net.sf.jchemistry.util.IOUtils;
import au.com.bytecode.opencsv.CSVReader;

/**
 * Physical, atomic and miscellaneous properties of the elements.
 * 
 * @author ppinard
 */
public final class ElementProperties {

    /** Density map. */
    private static final Map<Element, Double> DENSITY =
            new HashMap<Element, Double>();

    /** Atomic radius map. */
    private static final Map<Element, Double> ATOMIC_RADIUS =
            new HashMap<Element, Double>();

    /** Covalent radius map. */
    private static final Map<Element, Double> COVALENT_RADIUS =
            new HashMap<Element, Double>();

    /** Atomic mass map. */
    private static final Map<Element, Double> ATOMIC_MASS =
            new HashMap<Element, Double>();

    /** Boiling temperature map. */
    private static final Map<Element, Double> BOILING_TEMP =
            new HashMap<Element, Double>();

    /** Melting temperature map. */
    private static final Map<Element, Double> MELTING_TEMP =
            new HashMap<Element, Double>();

    /** Molar volume map. */
    private static final Map<Element, Double> MOLAR_VOLUME =
            new HashMap<Element, Double>();

    /** Thermal conductivity map. */
    private static final Map<Element, Double> THERMAL_CONDUCTIVITY =
            new HashMap<Element, Double>();

    /** Debye temperature map. */
    private static final Map<Element, Double> DEBYE_TEMP =
            new HashMap<Element, Double>();

    static {
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Returns the atomic mass of the specified element (in g/mol).
     * 
     * @param element
     *            element
     * @return atomic mass (in g/mol)
     * @throws IllegalArgumentException
     *             if the atomic mass is not available for the specified element
     */
    public static double getAtomicMass(Element element) {
        if (!hasAtomicMass(element))
            throw new IllegalArgumentException(
                    "No atomic mass data for element: " + element);
        return ATOMIC_MASS.get(element);
    }



    /**
     * Returns the atomic radius of the specified element (in angstroms).
     * 
     * @param element
     *            element
     * @return atomic radius (in angstroms)
     * @throws IllegalArgumentException
     *             if the atomic radius is not available for the specified
     *             element
     */
    public static double getAtomicRadius(Element element) {
        if (!hasAtomicRadius(element))
            throw new IllegalArgumentException(
                    "No atomic radius data for element: " + element);
        return ATOMIC_RADIUS.get(element);
    }



    /**
     * Returns the boiling temperature of the specified element (in K).
     * 
     * @param element
     *            element
     * @return boiling temperature (in K)
     * @throws IllegalArgumentException
     *             if the boiling temperature is not available for the specified
     *             element
     */
    public static double getBoilingTemperature(Element element) {
        if (!hasBoilingTemperature(element))
            throw new IllegalArgumentException(
                    "No boiling temperature data for element: " + element);
        return BOILING_TEMP.get(element);
    }



    /**
     * Returns the covalent radius of the specified element (in angstroms).
     * 
     * @param element
     *            element
     * @return covalent radius (in angstroms)
     * @throws IllegalArgumentException
     *             if the covalent radius is not available for the specified
     *             element
     */
    public static double getCovalentRadius(Element element) {
        if (!hasCovalentRadius(element))
            throw new IllegalArgumentException(
                    "No covalent radius data for element: " + element);
        return COVALENT_RADIUS.get(element);
    }



    /**
     * Returns the Debye temperature of the specified element (in K).
     * 
     * @param element
     *            element
     * @return Debye temperature (in K)
     * @throws IllegalArgumentException
     *             if the Debye temperature is not available for the specified
     *             element
     */
    public static double getDebyeTemperature(Element element) {
        if (!hasDebyeTemperature(element))
            throw new IllegalArgumentException(
                    "No Debye temperature data for element: " + element);
        return DEBYE_TEMP.get(element);
    }



    /**
     * Returns the density of the specified element (in kg/m3).
     * 
     * @param element
     *            element
     * @return density (in kg/m3)
     * @throws IllegalArgumentException
     *             if the density is not available for the specified element
     */
    public static double getDensity(Element element) {
        if (!hasDensity(element))
            throw new IllegalArgumentException("No density data for element: "
                    + element);
        return DENSITY.get(element);
    }



    /**
     * Returns the melting temperature of the specified element (in K).
     * 
     * @param element
     *            element
     * @return melting temperature (in K)
     * @throws IllegalArgumentException
     *             if the melting temperature is not available for the specified
     *             element
     */
    public static double getMeltingTemperature(Element element) {
        if (!hasMeltingTemperature(element))
            throw new IllegalArgumentException(
                    "No melting temperature data for element: " + element);
        return MELTING_TEMP.get(element);
    }



    /**
     * Returns the molar volume of the specified element (in m3).
     * 
     * @param element
     *            element
     * @return molar volume (in m3)
     * @throws IllegalArgumentException
     *             if the molar volume is not available for the specified
     *             element
     */
    public static double getMolarVolume(Element element) {
        if (!hasMolarVolume(element))
            throw new IllegalArgumentException(
                    "No molar volume data for element: " + element);
        return MOLAR_VOLUME.get(element);
    }



    /**
     * Returns the thermal conductivity of the specified element (in W/(m.K)).
     * 
     * @param element
     *            element
     * @return thermal conductivity (in W/(m.K))
     * @throws IllegalArgumentException
     *             if the thermal conductivity is not available for the
     *             specified element
     */
    public static double getThermalConductivity(Element element) {
        if (!hasThermalConductivity(element))
            throw new IllegalArgumentException(
                    "No thermal conductivity data for element: " + element);
        return THERMAL_CONDUCTIVITY.get(element);
    }



    /**
     * Returns whether the atomic mass of the specified element is available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the atomic mass exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasAtomicMass(Element element) {
        return ATOMIC_MASS.containsKey(element);
    }



    /**
     * Returns whether the atomic radius of the specified element is available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the atomic radius exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasAtomicRadius(Element element) {
        return ATOMIC_RADIUS.containsKey(element);
    }



    /**
     * Returns whether the boiling temperature of the specified element is
     * available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the boiling temperature exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasBoilingTemperature(Element element) {
        return BOILING_TEMP.containsKey(element);
    }



    /**
     * Returns whether the covalent radius of the specified element is
     * available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the covalent radius exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasCovalentRadius(Element element) {
        return COVALENT_RADIUS.containsKey(element);
    }



    /**
     * Returns whether the Debye temperature of the specified element is
     * available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the Debye temperature exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasDebyeTemperature(Element element) {
        return DEBYE_TEMP.containsKey(element);
    }



    /**
     * Returns whether the density of the specified element is available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the density exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasDensity(Element element) {
        return DENSITY.containsKey(element);
    }



    /**
     * Returns whether the melting temperature of the specified element is
     * available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the melting temperature exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasMeltingTemperature(Element element) {
        return MELTING_TEMP.containsKey(element);
    }



    /**
     * Returns whether the molar volume of the specified element is available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the molar volume exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasMolarVolume(Element element) {
        return MOLAR_VOLUME.containsKey(element);
    }



    /**
     * Returns whether the thermal conductivity of the specified element is
     * available.
     * 
     * @param element
     *            element
     * @return <code>true</code> if a value for the thermal conductivity exists,
     *         <code>false</code> otherwise
     */
    public static boolean hasThermalConductivity(Element element) {
        return THERMAL_CONDUCTIVITY.containsKey(element);
    }



    /**
     * Reads the data for the properties from the CSV file.
     * 
     * @throws IOException
     *             if an error occurs while reading the file
     */
    private static void readData() throws IOException {
        Reader reader =
                IOUtils.getReader("net/sf/jchemistry/data/properties.csv");

        CSVReader csv = new CSVReader(reader);
        csv.readNext(); // skip header

        String[] line;
        Element element;
        while (true) {
            line = csv.readNext();
            if (line == null)
                break; // end of lines

            element = Element.fromZ(Integer.parseInt(line[0]));

            readStoreValue(line[1], element, ATOMIC_RADIUS, 1);
            readStoreValue(line[2], element, COVALENT_RADIUS, 1);
            readStoreValue(line[3], element, ATOMIC_MASS, 1);
            readStoreValue(line[4], element, BOILING_TEMP, 1);
            readStoreValue(line[5], element, MELTING_TEMP, 1);
            readStoreValue(line[6], element, DENSITY, 1);
            readStoreValue(line[7], element, MOLAR_VOLUME, 1e-6);
            readStoreValue(line[8], element, DEBYE_TEMP, 1);
            readStoreValue(line[9], element, THERMAL_CONDUCTIVITY, 1e2);
        }

        csv.close();
        reader.close();
    }



    /**
     * Reads and stores the value from a row item. If the value is negative, the
     * map is unchanged.
     * 
     * @param s
     *            item
     * @param element
     *            element of the row
     * @param map
     *            map in which to store the value
     * @param factor
     *            multiplication factor to convert the units of the value
     */
    private static void readStoreValue(String s, Element element,
            Map<Element, Double> map, double factor) {
        double value = Double.parseDouble(s) * factor;
        if (value >= 0)
            map.put(element, value);
    }



    /**
     * Don't let anyone instantiate this class.
     */
    private ElementProperties() {
    }

}
