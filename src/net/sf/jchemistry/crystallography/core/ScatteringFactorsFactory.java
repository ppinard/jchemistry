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
package net.sf.jchemistry.crystallography.core;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.core.ElementProperties;
import net.sf.jchemistry.util.IOUtils;
import au.com.bytecode.opencsv.CSVReader;

/**
 * Factory for different types of scattering factors.
 * 
 * @author Philippe T. Pinard
 */
public class ScatteringFactorsFactory {

    /**
     * Constant used to calculate the scattering factor using Ibers (1958)
     * formula.
     */
    private static final double CONST_IBERS = 4 * Math.PI
            * Constants.MASS_ELECTRON * Math.pow(Constants.CHARGE_ELECTRON, 2)
            / (3 * Math.pow(Constants.H, 2));



    /**
     * Calculates the scattering factor when <code>s=0</code> using Ibers (1958)
     * formula as given by Equation 4.3.1.29 in the International Tables for
     * Crystallography (Volume C).
     * 
     * @param element
     *            element
     * @return scattering factor (in angstroms)
     */
    private static double calculateScatteringFactorFromIbers1958(Element element) {
        double radius = ElementProperties.getAtomicRadius(element); // A
        return CONST_IBERS * element.z() * Math.pow(radius, 2);
    }

    /**
     * Tabulated scattering factors for x-rays.
     * 
     * @author Philippe T. Pinard
     */
    private static class XRaySF extends SFTabulated {

        /** Coefficients between 0 < s < 2 (A^{-1}). */
        private final Map<Element, Map<Integer, Double[]>> coeffs02 =
                new HashMap<Element, Map<Integer, Double[]>>();

        /** Coefficients between 2 < s < 6 (A^{-1}). */
        private final Map<Element, Double[]> coeffs26 =
                new HashMap<Element, Double[]>();



        /**
         * Creates a new <code>XRaySFM</code>.
         */
        public XRaySF() {
            super(
                    "net/sf/jchemistry/crystallography/data/xray_scattering_factors_0_2.csv",
                    "net/sf/jchemistry/crystallography/data/xray_scattering_factors_2_6.csv");
            read();
        }



        /**
         * Calculates the scattering factor for values of s between 0 and 2.
         * 
         * @param element
         *            element of the atom
         * @param charge
         *            electron charge of the atom
         * @param s
         *            momentum transfer (in angstroms<sup>-1</sup>)
         * @return scattering factor intensity (in angstroms)
         */
        private double calculate02(Element element, int charge, double s) {
            if (!coeffs02.containsKey(element))
                throw new IllegalArgumentException("No data for element: "
                        + element);
            Map<Integer, Double[]> elementMap = coeffs02.get(element);

            Double[] coeffs;
            if (elementMap.containsKey(charge))
                coeffs = elementMap.get(charge);
            else
                coeffs = elementMap.get(0);

            // Calculate factor
            double ss = Math.pow(s, 2);
            double f = 0.0;

            for (int i = 0; i < 4; i++)
                f += coeffs[i] * Math.exp(-coeffs[i + 4] * ss);

            f += coeffs[8];

            return f;
        }



        /**
         * Calculates the scattering factor for values of s between 2 and 6.
         * 
         * @param element
         *            element of the atom
         * @param charge
         *            electron charge of the atom
         * @param s
         *            momentum transfer (in angstroms<sup>-1</sup>)
         * @return scattering factor intensity (in angstroms)
         */
        private double calculate26(Element element, int charge, double s) {
            if (!coeffs26.containsKey(element))
                throw new IllegalArgumentException("No data for element: "
                        + element);
            Double[] coeffs = coeffs26.get(element);

            // Calculate factor
            double f =
                    coeffs[0] + coeffs[1] * s + coeffs[2] / 10.0
                            * Math.pow(s, 2) + coeffs[3] / 100.0
                            * Math.pow(s, 3);

            return Math.exp(f);
        }



        @Override
        protected void read02Line(String[] line) {
            // Element
            Element element = Element.fromSymbol(line[0].trim());

            // Charge
            if (line[1].trim().equals("val"))
                return;
            int charge = (int) Double.parseDouble(line[1]);

            // Coefficients
            Double[] elementCoeffs = new Double[9];
            elementCoeffs[0] = Double.parseDouble(line[3]);
            elementCoeffs[1] = Double.parseDouble(line[5]);
            elementCoeffs[2] = Double.parseDouble(line[7]);
            elementCoeffs[3] = Double.parseDouble(line[9]);
            elementCoeffs[4] = Double.parseDouble(line[4]);
            elementCoeffs[5] = Double.parseDouble(line[6]);
            elementCoeffs[6] = Double.parseDouble(line[8]);
            elementCoeffs[7] = Double.parseDouble(line[10]);
            elementCoeffs[8] = Double.parseDouble(line[11]);

            if (coeffs02.containsKey(element)) {
                coeffs02.get(element).put(charge, elementCoeffs);
            } else {
                Map<Integer, Double[]> elementMap =
                        new HashMap<Integer, Double[]>();
                elementMap.put(charge, elementCoeffs);
                coeffs02.put(element, elementMap);
            }
        }



        @Override
        protected void read26Line(String[] line) {
            // Element
            Element element = Element.fromSymbol(line[0].trim());

            // Coefficients
            Double[] elementCoeffs = new Double[4];
            elementCoeffs[0] = Double.parseDouble(line[3]);
            elementCoeffs[1] = Double.parseDouble(line[4]);
            elementCoeffs[2] = Double.parseDouble(line[5]);
            elementCoeffs[3] = Double.parseDouble(line[6]);

            coeffs26.put(element, elementCoeffs);
        }



        @Override
        public double getIntensity(Element element, int charge, double s) {
            if (element == null)
                throw new NullPointerException("element == null");
            if (s < 0)
                throw new IllegalArgumentException("Momentum transfer (" + s
                        + ") < 0.");

            // NOTE: X-ray table used s = sin(theta) / lambda definition
            // instead of s = 4 pi sin(theta) / lambda
            s = s / (4 * Math.PI);

            if (s >= 0 && s < 2)
                return calculate02(element, charge, s);
            else if (s >= 2 && s < 6)
                return calculate26(element, charge, s);
            else {
                LOGGER.warning("Outside table range of s (" + s
                        + ") < 6 angstroms");
                return calculate26(element, charge, s);
            }
        }
    }

    /**
     * Tabulated scattering factors for electron (elastic).
     * 
     * @author Philippe T. Pinard
     */
    private static class ElectronSF extends SFTabulated {

        /** Coefficients between 0 < s < 2 (A^{-1}). */
        private final Map<Element, Double[]> coeffs02 =
                new HashMap<Element, Double[]>();

        /** Coefficients between 2 < s < 6 (A^{-1}). */
        private final Map<Element, Double[]> coeffs26 =
                new HashMap<Element, Double[]>();



        /**
         * Creates a new <code>ElectronSF</code>.
         */
        public ElectronSF() {
            super(
                    "net/sf/jchemistry/crystallography/data/electron_scattering_factors_0_2.csv",
                    "net/sf/jchemistry/crystallography/data/electron_scattering_factors_2_6.csv");
            read();
        }



        /**
         * Calculates the scattering factor based on the specified coefficients
         * map.
         * 
         * @param element
         *            element of the atom
         * @param charge
         *            electron charge of the atom
         * @param s
         *            momentum transfer (in angstroms<sup>-1</sup>)
         * @param coeffs
         *            map of coefficients to use in the calculation
         * @return scattering factor intensity (in angstroms)
         */
        private double calculate(Element element, int charge, double s,
                Map<Element, Double[]> coeffs) {
            if (!coeffs.containsKey(element))
                throw new IllegalArgumentException("No data for element: "
                        + element);

            Double[] elementCoeffs = coeffs.get(element);
            double s2 = Math.pow(s, 2); // s^2

            double f = 0;
            for (int i = 0; i < 5; i++) {
                f += elementCoeffs[i] * Math.exp(-elementCoeffs[i + 5] * s2);
            }

            return f;
        }



        @Override
        public double getMaxIntensity(Element element, int charge) {
            return calculateScatteringFactorFromIbers1958(element);
        }



        @Override
        protected void read02Line(String[] line) {
            readLine(line, coeffs02);
        }



        @Override
        protected void read26Line(String[] line) {
            readLine(line, coeffs26);
        }



        /**
         * Reads, extracts and stores element and coefficients from one line.
         * 
         * @param line
         *            data line
         * @param coeffs
         *            map in which to store the data
         */
        private void readLine(String[] line, Map<Element, Double[]> coeffs) {
            // Element
            Element element = Element.fromZ(Integer.parseInt(line[0]));

            // Coefficients
            Double[] elementCoeffs = new Double[10];
            elementCoeffs[0] = Double.parseDouble(line[1]);
            elementCoeffs[1] = Double.parseDouble(line[2]);
            elementCoeffs[2] = Double.parseDouble(line[3]);
            elementCoeffs[3] = Double.parseDouble(line[4]);
            elementCoeffs[4] = Double.parseDouble(line[5]);
            elementCoeffs[5] = Double.parseDouble(line[6]);
            elementCoeffs[6] = Double.parseDouble(line[7]);
            elementCoeffs[7] = Double.parseDouble(line[8]);
            elementCoeffs[8] = Double.parseDouble(line[9]);
            elementCoeffs[9] = Double.parseDouble(line[10]);

            coeffs.put(element, elementCoeffs);
        }



        @Override
        public double getIntensity(Element element, int charge, double s) {
            if (element == null)
                throw new NullPointerException("element == null");
            if (s < 0)
                throw new IllegalArgumentException("Momentum transfer (" + s
                        + ") < 0.");

            if (s == 0)
                return getMaxIntensity(element, charge);
            else if (s > 0 && s < 2)
                return calculate(element, charge, s, coeffs02);
            else if (s >= 2 && s < 6)
                return calculate(element, charge, s, coeffs26);
            else {
                LOGGER.warning("Outside table range of s (" + s
                        + ") < 6 angstroms");
                return calculate(element, charge, s, coeffs26);
            }
        }

    }

    /**
     * Base class for tabulated scattering factors. This method simplifies
     * reading the tabulated coefficients and the calculate of the scattering
     * factors. It assumes that the coefficients are given in 2 tables for (1) 0
     * < s < 2 and (2) 2 < s < 6.
     * 
     * @author Philippe T. Pinard
     */
    private static abstract class SFTabulated implements ScatteringFactors {

        /** Logger. */
        protected static final Logger LOGGER =
                Logger.getLogger("crystallography");

        /**
         * Filename for the CSV file containing the data for [0, 2[
         * coefficients.
         */
        private final String filename02;

        /**
         * Filename for the CSV file containing the data for [2, 6[
         * coefficients.
         */
        private final String filename26;



        /**
         * Creates a new <code>SFTabulated</code>.
         * 
         * @param filename02
         *            filename for the CSV file containing the data for [0, 2[
         *            coefficients.
         * @param filename26
         *            filename for the CSV file containing the data for [2, 6[
         *            coefficients.
         * @throws RuntimeException
         *             if the data can be read from the files
         */
        public SFTabulated(String filename02, String filename26) {
            this.filename02 = filename02;
            this.filename26 = filename26;
        }



        /**
         * Reads the CSV files.
         */
        protected void read() {
            try {
                read02(filename02);
                read26(filename26);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        @Override
        public double getMaxIntensity(Element element, int charge) {
            return getIntensity(element, charge, 0.0);
        }



        /**
         * Reads the data for the coefficients between 0 < s < 2 (A^{-1}).
         * 
         * @param filename
         *            filename of the CSV file
         * @throws IOException
         *             if an error occurs while reading the file
         */
        private void read02(String filename) throws IOException {
            Reader reader = IOUtils.getReader(filename);

            CSVReader csv = new CSVReader(reader);

            // Skip header
            csv.readNext();

            String[] line;
            while (true) {
                line = csv.readNext();
                if (line == null)
                    break; // end of lines

                read02Line(line);
            }

            csv.close();
            reader.close();
        }



        /**
         * Reads, extracts and stores element and coefficients from one line.
         * 
         * @param line
         *            data line
         */
        protected abstract void read02Line(String[] line);



        /**
         * Reads the data for the coefficients between 2 < s < 6 (A^{-1}).
         * 
         * @param filename
         *            filename of the CSV file
         * @throws IOException
         *             if an error occurs while reading the file
         */
        private void read26(String filename) throws IOException {
            Reader reader = IOUtils.getReader(filename);

            CSVReader csv = new CSVReader(reader);

            // Skip header
            csv.readNext();

            String[] line;
            while (true) {
                line = csv.readNext();
                if (line == null)
                    break; // end of lines

                read26Line(line);
            }

            csv.close();
            reader.close();
        }



        /**
         * Reads, extracts and stores element and coefficients from one line.
         * 
         * @param line
         *            data line
         */
        protected abstract void read26Line(String[] line);
    }

    /**
     * Electron scattering factors calculated using the Mott-Bethe formula.
     * 
     * @author Philippe T. Pinard
     */
    private static class ElectronMottBetheSF implements ScatteringFactors {

        /** Constant used in the scattering intensity calculation. */
        private static final double CONST = (2 * Math.PI
                * Constants.MASS_ELECTRON * Math.pow(Constants.CHARGE_ELECTRON,
                2)) / (Math.pow(Constants.H, 2) * Constants.PERMITTIVITY);



        @Override
        public double getIntensity(Element element, int charge, double s) {
            if (s == 0)
                return getMaxIntensity(element, charge);

            double fx = XRAY_TABULATED.getIntensity(element, charge, s);
            return CONST * (element.z() - fx) / Math.pow(s, 2) / 1e10;
            // TODO: Check units of equation
        }



        @Override
        public double getMaxIntensity(Element element, int charge) {
            return calculateScatteringFactorFromIbers1958(element);
        }
    }

    /**
     * Elastic electron scattering factors based on Peng, Ren, Dudarev and
     * Whelan (1996) Gaussian fit as given in Tables 4.3.2.2 and 4.3.2.3 of the
     * International Tables for Crystallography (Volume C).
     * <p/>
     * For the scattering factors when <code>s=0</code>, the formula by Ibers
     * (1958) is used (ref. International Tables for Crystallography (Volume C),
     * section 4.3.1.6).
     */
    public static final ScatteringFactors ELECTRON_TABULATED = new ElectronSF();

    /**
     * X-ray scattering factors based on tabulated values in the International
     * Tables for Crystallography (Volume C), Tables 6.1.1.4 and 6.1.1.5.
     */
    public static final ScatteringFactors XRAY_TABULATED = new XRaySF();

    /**
     * Born electron scattering factors calculated from the tabulated x-ray
     * scattering factors and the Mott-Bethe formula. The x-ray scattering
     * factors are obtained from tabulated values in the International Tables
     * for Crystallography (Volume C), Tables 6.1.1.4 and 6.1.1.5. The
     * Mott-Bethe formula was taken from Equation 4.3.1.14 in the International
     * Tables for Crystallography (Volume C).
     * <p/>
     * As for tabulated elastic electorn scattering factors, when
     * <code>s=0</code>, the formula by Ibers (1958) is used (ref. International
     * Tables for Crystallography (Volume C), section 4.3.1.6).
     */
    public static final ScatteringFactors ELECTRON_MOTT_BETHE =
            new ElectronMottBetheSF();

}
