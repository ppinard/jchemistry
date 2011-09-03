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
package net.sf.jchemistry.crystallography.io;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.crystallography.core.AtomSite;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflector;
import net.sf.jchemistry.crystallography.core.SpaceGroup;
import net.sf.jchemistry.crystallography.core.SpaceGroups;
import net.sf.jchemistry.crystallography.core.UnitCell;
import net.sf.jchemistry.crystallography.core.UnitCellFactory;

import org.apache.commons.math.geometry.Vector3D;

import bsh.EvalError;
import bsh.Interpreter;

import static net.sf.jchemistry.crystallography.io.CifConstants.*;

/**
 * Loader of a phase from a crystallography information file (CIF).
 * 
 * @author Philippe T. Pinard
 */
public class CifLoader {

    /** Parsed CIF data. */
    private Map<String, List<String>> cifData;

    /** Beanshell interpreter. */
    private final Interpreter interpreter = new Interpreter();

    /** Logger. */
    private final Logger logger =
            Logger.getLogger("net.sf.jchemistry.crystallography.io.CifLoader");

    /** Regex pattern to get atom symbol. */
    private static final Pattern SYMBOL_PATTERN =
            Pattern.compile("([A-Z][a-z]?).*");

    /** Regex pattern to separate atom symbol and charge. */
    private static final Pattern CHARGE_PATTERN =
            Pattern.compile("(\\d+)([+-]).*");



    /**
     * Returns the coordinate of a position from the specified symmetrically
     * equivalent position expression. The expression can contain symbols such
     * as +, -, *, / and the variables x,y and z. It is evaluated with the
     * values of x, y and z of the given atom positions.
     * 
     * @param coord
     *            string of the coordinate
     * @param x
     *            x value for an atom position
     * @param y
     *            y value for an atom position
     * @param z
     *            z value for an atom position
     * @return double value of the coordinate of a position
     * @throws IOException
     *             if an error occurs while evaluating the expression of the
     *             position
     */
    private double calculateCoord(String coord, double x, double y, double z)
            throws IOException {
        try {
            return ((Number) interpreter.eval(coord.replace("x",
                    String.valueOf(x)).replace("y", String.valueOf(y)).replace(
                    "z", String.valueOf(z)))).doubleValue();
        } catch (EvalError e) {
            throw new IOException(e);
        }
    }



    /**
     * Evaluates the symmetry equivalent position string and returns the
     * position as a vector.
     * 
     * @param position
     *            string of the three coordinates (separated by commas)
     * @param x
     *            x value for an atom position
     * @param y
     *            y value for an atom position
     * @param z
     *            z value for an atom position
     * @return coordinate of a position
     * @throws IOException
     *             if the <code>position</code> does not contain 3 coordinates
     * @throws IOException
     *             if an error occurs while evaluating the expression of the
     *             position
     */
    private Vector3D calculatePosition(String position, double x, double y,
            double z) throws IOException {
        String[] coords = position.split(",");
        if (coords.length != 3)
            throw new IOException("Invalid symmetry equivalent position: "
                    + position);

        return new Vector3D(calculateCoord(coords[0], x, y, z), calculateCoord(
                coords[1], x, y, z), calculateCoord(coords[2], x, y, z));
    }



    /**
     * Parses a string as a double.
     * 
     * @param value
     *            string value
     * @return double value
     * @throws IOException
     *             if the string cannot be parsed as a double
     */
    private double parseDouble(String value) throws IOException {
        try {
            return FORMAT.parse(value).doubleValue();
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }



    /**
     * Returns the first value in the list for the first match of the specified
     * keys. The value is parsed as a double.
     * 
     * @param keys
     *            possible keys
     * @return double value
     * @throws IOException
     *             if no value are found for the specified keys
     */
    protected double getDoubleValue(String... keys) throws IOException {
        return parseDouble(getStringValue(keys));
    }



    /**
     * Returns a list of values for the first match of the specified keys. If no
     * values is found, an empty list is returned. These values are parsed as
     * doubles.
     * 
     * @param keys
     *            possible keys
     * @return list of double values
     * @throws IOException
     *             if a string cannot be parsed as a double
     */
    protected List<Double> getDoubleValues(String... keys) throws IOException {
        List<String> values = getStringValues(keys);

        List<Double> newValues = new ArrayList<Double>();
        for (String value : values)
            newValues.add(parseDouble(value));

        return newValues;
    }



    /**
     * Returns the first value in the list for the first match of the specified
     * keys. The value is parsed as a integer.
     * 
     * @param keys
     *            possible keys
     * @return integer value
     * @throws IOException
     *             if no value are found for the specified keys
     */
    protected int getIntegerValue(String... keys) throws IOException {
        return Integer.parseInt(getStringValue(keys));
    }



    /**
     * Returns a list of values for the first match of the specified keys. If no
     * values is found, an empty list is returned. The values are parsed as
     * integers.
     * 
     * @param keys
     *            possible keys
     * @return list of integer values
     */
    protected List<Integer> getIntegerValues(String... keys) {
        List<String> values = getStringValues(keys);

        List<Integer> newValues = new ArrayList<Integer>();
        for (String value : values)
            newValues.add(Integer.parseInt(value));

        return newValues;
    }



    /**
     * Returns the first value in the list for the first match of the specified
     * keys.
     * 
     * @param keys
     *            possible keys
     * @return value
     * @throws IOException
     *             if no value are found for the specified keys
     */
    protected String getStringValue(String... keys) throws IOException {
        List<String> values = getStringValues(keys);

        if (values.isEmpty())
            throw new IOException("No value found for keys: "
                    + Arrays.toString(keys));
        else
            return values.get(0);
    }



    /**
     * Returns a list of values for the first match of the specified keys. If no
     * values is found, an empty list is returned.
     * 
     * @param keys
     *            possible keys
     * @return list of values
     */
    protected List<String> getStringValues(String... keys) {
        List<String> values;
        for (String key : keys) {
            values = cifData.get(key);
            if (values != null)
                return values;
        }

        return new ArrayList<String>();
    }



    /**
     * Loads a phase from a CIF.
     * 
     * @param in
     *            reader of a CIF file
     * @return phase
     * @throws IOException
     *             if an error occurs while reading or parsing the CIF
     */
    public Phase load(Reader in) throws IOException {
        // Get CIF data
        cifData = new CifParser().parse(in);

        // Parse data
        String name = parseName();
        String reference = parseCitation();
        SpaceGroup spaceGroup = parseSpaceGroup();
        UnitCell unitCell = parseUnitCell();

        Phase phase = new Phase(name, reference, spaceGroup, unitCell);
        phase.getAtoms().addAll(parseAtoms());
        phase.getReflectors().addAll(parseReflectors());

        // Reset
        cifData = null;

        return phase;
    }



    /**
     * Returns the atom sites from the CIF data.
     * 
     * @return atom sites
     * @throws IOException
     *             if an error occurs while parsing the atom sites
     */
    private List<AtomSite> parseAtoms() throws IOException {
        // Positions
        List<Double> xs = getDoubleValues(ATOM_SITE_FRACT_X);
        List<Double> ys = getDoubleValues(ATOM_SITE_FRACT_Y);
        List<Double> zs = getDoubleValues(ATOM_SITE_FRACT_Z);

        // Occupancies
        List<Double> occupancies = getDoubleValues(ATOM_SITE_OCCUPANCY);
        if (occupancies.isEmpty())
            occupancies = java.util.Collections.nCopies(xs.size(), 1.0);

        // Element and charge
        List<String> labels =
                getStringValues(ATOM_SITE_TYPE_SYMBOL, ATOM_SITE_LABEL);
        List<Element> elements = new ArrayList<Element>();
        List<Integer> charges = new ArrayList<Integer>();

        Matcher symbolMatch;
        Matcher chargeMatcher;
        String chargeLabel;
        int charge;
        for (String label : labels) {
            // Symbol
            symbolMatch = SYMBOL_PATTERN.matcher(label);
            if (!symbolMatch.matches())
                throw new IOException("Atom symbol is invalid: " + label);

            elements.add(Element.fromSymbol(symbolMatch.group(1)));

            // Charge
            chargeLabel = label.substring(symbolMatch.end(1));
            chargeMatcher = CHARGE_PATTERN.matcher(chargeLabel);

            if (!chargeMatcher.matches()) {
                charges.add(0);
                continue;
            }

            charge = Integer.parseInt(chargeMatcher.group(1));
            if (chargeMatcher.group(2).equals("-"))
                charges.add(-1 * charge);
            else
                charges.add(charge);
        }

        // Check lists size
        if (xs.size() != ys.size() || xs.size() != zs.size()
                || xs.size() != occupancies.size()
                || xs.size() != elements.size() || xs.size() != charges.size())
            throw new IOException(
                    "The number of coordinates in X, Y, Z, elements, occupancies and size do not match: x="
                            + xs.size()
                            + ", y="
                            + ys.size()
                            + ", z="
                            + zs.size()
                            + ", elements="
                            + elements.size()
                            + ", charges="
                            + charges.size()
                            + ", occupancies="
                            + occupancies.size());

        // Symmetric equivalent positions
        List<String> symEquivPositions =
                getStringValues(SYMMETRY_EQUIV_POS_AS_XYZ,
                        SPACE_GROUP_SYMOP_OPERATION_XYZ);
        if (symEquivPositions.isEmpty())
            symEquivPositions.add("x,y,z");

        // Create atoms
        List<AtomSite> atoms = new ArrayList<AtomSite>();

        for (int i = 0; i < xs.size(); i++) {
            for (String pos : symEquivPositions) {
                atoms.add(new AtomSite(
                        elements.get(i),
                        charges.get(i),
                        calculatePosition(pos, xs.get(i), ys.get(i), zs.get(i)),
                        occupancies.get(i)));
            }
        }

        return atoms;
    }



    /**
     * Returns the phase name from the CIF data. The information is extracted
     * from either the common chemical name or mineral chemical name.
     * 
     * @return phase's name
     * @throws IOException
     *             if no name is found
     */
    private String parseName() throws IOException {
        return getStringValue(CHEMICAL_NAME_COMMON, CHEMICAL_NAME_MINERAL,
                CHEMICAL_NAME_STRUCTURE_TYP, CHEMICAL_NAME_SYSTEMATIC);
    }



    /**
     * Returns a string representing the citation information in the CIF data.
     * 
     * @return citation
     */
    private String parseCitation() {
        // Single citation entry
        List<String> references = getStringValues(PUBL_SECTION_REFERENCES);
        if (references.size() == 1)
            return references.get(0);

        // Check for multiple citations
        List<String> ids = getStringValues(CITATION_ID);
        if (ids.size() > 1) {
            logger.warning("Loader does not support multiple citations");
            return "No reference";
        }

        StringBuilder ref = new StringBuilder();

        // Authors
        List<String> authors =
                getStringValues(CITATION_AUTHOR_NAME, PUBL_AUTHOR_NAME);
        if (!authors.isEmpty())
            ref.append(net.sf.jchemistry.util.Collections.join(authors, ", "));
        else
            ref.append("-");

        List<String> years = getStringValues(CITATION_YEAR, JOURNAL_YEAR);
        if (!years.isEmpty()) {
            ref.append(" (");
            ref.append(years.get(0));
            ref.append(")");
        }
        ref.append(", ");

        if (cifData.containsKey(CITATION_BOOK_TTTLE)) {
            // Book
            List<String> titles =
                    getStringValues(CITATION_TITLE, PUBL_SECTION_TITLE);
            if (!titles.isEmpty()) {
                ref.append(titles.get(0));
                ref.append(", ");
            }

            List<String> bookTitles = getStringValues(CITATION_BOOK_TTTLE);
            if (!bookTitles.isEmpty()) {
                ref.append("in: ");
                ref.append(bookTitles.get(0).trim());
                ref.append(", ");
            }

            List<String> editors = getStringValues(CITATION_EDITOR_NAME);
            if (!editors.isEmpty()) {
                ref.append("eds. ");
                ref.append(net.sf.jchemistry.util.Collections.join(editors,
                        ", "));
                ref.append(", ");
            }

            List<String> publishers = getStringValues(CITATION_BOOK_PUBLISHER);
            if (!publishers.isEmpty()) {
                ref.append(publishers.get(0));
                ref.append(", ");
            }

            List<String> publisherCities =
                    getStringValues(CITATION_BOOK_PUBLISHER_CITY);
            if (!publisherCities.isEmpty()) {
                ref.append(publisherCities.get(0));
                ref.append(", ");
            }
        } else {
            // Journal
            List<String> titles =
                    getStringValues(CITATION_TITLE, PUBL_SECTION_TITLE);
            if (!titles.isEmpty()) {
                ref.append(titles.get(0).trim());
                ref.append(", ");
            }

            List<String> journals =
                    getStringValues(CITATION_JOURNAL_ABBREV,
                            CITATION_JOURNAL_FULL, JOURNAL_NAME_FULL);
            if (!journals.isEmpty()) {
                ref.append(journals.get(0));
                ref.append(", ");
            }

            List<String> volumes =
                    getStringValues(CITATION_JOURNAL_VOLUME, JOURNAL_VOLUME);
            if (!volumes.isEmpty()) {
                ref.append(volumes.get(0));
                ref.append(", ");
            }

            List<String> firsts =
                    getStringValues(CITATION_PAGE_FIRST, JOURNAL_PAGE_FIRST);
            List<String> lasts =
                    getStringValues(CITATION_PAGE_LAST, JOURNAL_PAGE_LAST);
            if (!firsts.isEmpty() && !lasts.isEmpty()) {
                ref.append(firsts.get(0));
                ref.append("-");
                ref.append(lasts.get(0));
                ref.append(", ");
            }
        }

        ref.setLength(ref.length() - 2); // remove last comma
        return ref.toString();
    }



    /**
     * Returns a set of <code>Reflector</code>'s from the data in the CIF.
     * 
     * @return reflectors
     * @throws IOException
     *             if an error occurs while parsing the reflectors
     */
    private Set<Reflector> parseReflectors() throws IOException {
        Set<Reflector> refls = new HashSet<Reflector>();

        List<Integer> hs = getIntegerValues(REFLN_INDEX_H);
        List<Integer> ks = getIntegerValues(REFLN_INDEX_K);
        List<Integer> ls = getIntegerValues(REFLN_INDEX_L);

        List<Double> intensities =
                getDoubleValues(REFLN_INTENSITY_MEAS, REFLN_F_SQUARED_MEAS,
                        REFLN_INTENSITY_CALC, REFLN_F_SQUARED_CALC);
        if (intensities.isEmpty()) {
            List<Double> newIntensities =
                    getDoubleValues(REFLN_F_MEAS, REFLN_F_CALC);
            for (Double intensity : newIntensities)
                intensities.add(Math.pow(intensity, 2));
        }

        if (hs.size() != ks.size() || hs.size() != ls.size()
                || hs.size() != intensities.size())
            throw new IOException(
                    "The number of reflectors and intensities do not match: h="
                            + hs.size() + ", k=" + ks.size() + ", l="
                            + ls.size() + ", I=" + intensities.size());

        for (int i = 0; i < hs.size(); i++) {
            refls.add(new Reflector(hs.get(i), ks.get(i), ls.get(i),
                    intensities.get(i)));
        }

        return refls;
    }



    /**
     * Returns the space group from the CIF data.
     * 
     * @return space group
     * @throws IOException
     *             if space group is missing
     */
    private SpaceGroup parseSpaceGroup() throws IOException {
        int index =
                getIntegerValue(SPACE_GROUP_IT_NUMBER,
                        SYMMETRY_INT_TABLES_NUMBER);

        return SpaceGroups.fromIndex(index);
    }



    /**
     * Returns the unit cell from the CIF data.
     * 
     * @return unit cell
     * @throws IOException
     *             if a value is missing
     */
    private UnitCell parseUnitCell() throws IOException {
        double a = getDoubleValue(CELL_LENGTH_A);
        double b = getDoubleValue(CELL_LENGTH_B);
        double c = getDoubleValue(CELL_LENGTH_C);
        double alpha = Math.toRadians(getDoubleValue(CELL_ANGLE_ALPHA));
        double beta = Math.toRadians(getDoubleValue(CELL_ANGLE_BETA));
        double gamma = Math.toRadians(getDoubleValue(CELL_ANGLE_GAMMA));

        return UnitCellFactory.triclinic(a, b, c, alpha, beta, gamma);
    }

}
