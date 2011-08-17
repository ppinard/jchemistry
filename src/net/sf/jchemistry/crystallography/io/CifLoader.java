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
package net.sf.jchemistry.crystallography.io;

import java.io.IOException;
import java.io.Reader;
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

    /** Regex pattern to separate atom symbol and charge. */
    private static final Pattern SYMBOL_CHARGE_MATCHER =
            Pattern.compile("([A-Z][a-z]?)((\\d+)([+-]?))?");



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
    private double calculateCoord(String coord, String x, String y, String z)
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
    private Vector3D calculatePosition(String position, String x, String y,
            String z) throws IOException {
        String[] coords = position.split(",");
        if (coords.length != 3)
            throw new IOException("Invalid symmetry equivalent position: "
                    + position);

        return new Vector3D(calculateCoord(coords[0], x, y, z), calculateCoord(
                coords[1], x, y, z), calculateCoord(coords[2], x, y, z));
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
        return Double.parseDouble(getStringValue(keys));
    }



    /**
     * Returns a list of values for the first match of the specified keys. If no
     * values is found, an empty list is returned. Thes values are parsed as
     * doubles.
     * 
     * @param keys
     *            possible keys
     * @return list of double values
     */
    protected List<Double> getDoubleValues(String... keys) {
        List<String> values = getStringValues(keys);

        List<Double> newValues = new ArrayList<Double>();
        for (String value : values)
            newValues.add(Double.parseDouble(value));

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
        String reference = parseReference();
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
        List<String> xs = getStringValues("_atom_site_fract_x");
        List<String> ys = getStringValues("_atom_site_fract_y");
        List<String> zs = getStringValues("_atom_site_fract_z");

        // Occupancies
        List<Double> occupancies = getDoubleValues("_atom_site_occupancy");
        if (occupancies.isEmpty())
            occupancies = java.util.Collections.nCopies(xs.size(), 1.0);

        // Element and charge
        List<String> labels =
                getStringValues("_atom_site_type_symbol", "_atom_site_label");
        List<Element> elements = new ArrayList<Element>();
        List<Integer> charges = new ArrayList<Integer>();

        Matcher match;
        int charge;
        for (String label : labels) {
            match = SYMBOL_CHARGE_MATCHER.matcher(label);
            if (!match.matches())
                throw new IOException("Atom symbol and/or charge is invalid: "
                        + label);

            elements.add(Element.fromSymbol(match.group(1)));

            if (match.groupCount() == 3) {
                charge = Integer.parseInt(match.group(3));

                if (match.group(4).equals("-"))
                    charges.add(-1 * charge);
                else
                    charges.add(charge);
            } else {
                charges.add(0);
            }
        }

        if (charges.isEmpty())
            charges = java.util.Collections.nCopies(xs.size(), 0);

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
                getStringValues("_symmetry_equiv_pos_as_xyz",
                        "_space_group_symop_operation_xyz");
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
        return getStringValue("_chemical_name_common", "_chemical_name_mineral");
    }



    /**
     * Returns a string representing the citation information in the CIF data.
     * 
     * @return reference
     */
    private String parseReference() {
        // Check for multiple citations
        List<String> ids = getStringValues("_citation_id");
        if (ids.size() > 1) {
            logger.warning("Loader does not support multiple citations");
            return "No reference";
        }

        StringBuilder ref = new StringBuilder();

        // Authors
        List<String> authors =
                getStringValues("_citation_author_name", "_publ_author_name");
        if (!authors.isEmpty())
            ref.append(net.sf.jchemistry.util.Collections.join(authors, ", "));
        else
            ref.append("-");

        List<String> years = getStringValues("_citation_year", "_journal_year");
        if (!years.isEmpty()) {
            ref.append(" (");
            ref.append(years.get(0));
            ref.append(")");
        }
        ref.append(", ");

        if (cifData.containsKey("_citation_book_title")) {
            // Book
            List<String> titles =
                    getStringValues("_citation_title", "_publ_section_title");
            if (!titles.isEmpty()) {
                ref.append(titles.get(0));
                ref.append(", ");
            }

            List<String> bookTitles = getStringValues("_citation_book_title");
            if (!bookTitles.isEmpty()) {
                ref.append("in: ");
                ref.append(bookTitles.get(0).trim());
                ref.append(", ");
            }

            List<String> editors = getStringValues("_citation_editor_name");
            if (!editors.isEmpty()) {
                ref.append("eds. ");
                ref.append(net.sf.jchemistry.util.Collections.join(editors,
                        ", "));
                ref.append(", ");
            }

            List<String> publishers =
                    getStringValues("_citation_book_publisher");
            if (!publishers.isEmpty()) {
                ref.append(publishers.get(0));
                ref.append(", ");
            }

            List<String> publisherCities =
                    getStringValues("_citation_book_publisher_city");
            if (!publisherCities.isEmpty()) {
                ref.append(publisherCities.get(0));
                ref.append(", ");
            }
        } else {
            // Journal
            List<String> titles =
                    getStringValues("_citation_title", "_publ_section_title");
            if (!titles.isEmpty()) {
                ref.append(titles.get(0).trim());
                ref.append(", ");
            }

            List<String> journals =
                    getStringValues("_citation_journal_abbrev",
                            "_citation_journal_full", "_journal_name_full");
            if (!journals.isEmpty()) {
                ref.append(journals.get(0));
                ref.append(", ");
            }

            List<String> volumes =
                    getStringValues("_citation_journal_volume",
                            "_journal_volume");
            if (!volumes.isEmpty()) {
                ref.append(volumes.get(0));
                ref.append(", ");
            }

            List<String> firsts =
                    getStringValues("_citation_page_first",
                            "_journal_page_first");
            List<String> lasts =
                    getStringValues("_citation_page_last", "_journal_page_last");
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

        List<Integer> hs = getIntegerValues("_refln_index_h");
        List<Integer> ks = getIntegerValues("_refln_index_k");
        List<Integer> ls = getIntegerValues("_refln_index_l");

        List<Double> intensities =
                getDoubleValues("_refln_intensity_meas",
                        "_refln_F_squared_meas", "_refln_intensity_calc",
                        "_refln_F_squared_calc");
        if (intensities.isEmpty()) {
            List<Double> newIntensities =
                    getDoubleValues("_refln_F_meas", "_refln_calc");
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
                getIntegerValue("_space_group_IT_number",
                        "_symmetry_Int_Tables_number");

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
        double a = getDoubleValue("_cell_length_a");
        double b = getDoubleValue("_cell_length_b");
        double c = getDoubleValue("_cell_length_c");
        double alpha = Math.toRadians(getDoubleValue("_cell_angle_alpha"));
        double beta = Math.toRadians(getDoubleValue("_cell_angle_beta"));
        double gamma = Math.toRadians(getDoubleValue("_cell_angle_gamma"));

        return UnitCellFactory.triclinic(a, b, c, alpha, beta, gamma);
    }

}
