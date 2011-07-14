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
package org.sf.jchemistry.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of the periodic table elements.
 * 
 * @author ppinard
 */
public enum Element {
    /** Hydrogen. */
    H(1, "H", "Hydrogen", 1.007900, 0.089900),

    /** Helium. */
    He(2, "He", "Helium", 4.002600, 0.178700),

    /** Lithium. */
    Li(3, "Li", "Lithium", 6.941000, 0.530000),

    /** Beryllium. */
    Be(4, "Be", "Beryllium", 9.012180, 1.850000),

    /** Boron. */
    B(5, "B", "Boron", 10.810000, 2.340000),

    /** Carbon. */
    C(6, "C", "Carbon", 12.011000, 2.620000),

    /** Nitrogen. */
    N(7, "N", "Nitrogen", 14.006700, 1.251000),

    /** Oxygen. */
    O(8, "O", "Oxygen", 15.999400, 1.429000),

    /** Fluorine. */
    F(9, "F", "Fluorine", 18.998403, 1.696000),

    /** Neon. */
    Ne(10, "Ne", "Neon", 20.179000, 0.901000),

    /** Sodium. */
    Na(11, "Na", "Sodium", 22.989770, 0.970000),

    /** Magnesium. */
    Mg(12, "Mg", "Magnesium", 24.305000, 1.740000),

    /** Aluminium. */
    Al(13, "Al", "Aluminium", 26.981540, 2.700000),

    /** Silicon. */
    Si(14, "Si", "Silicon", 28.085500, 2.330000),

    /** Phosphorus. */
    P(15, "P", "Phosphorus", 30.973760, 1.820000),

    /** Sulfur. */
    S(16, "S", "Sulfur", 32.060000, 2.070000),

    /** Chlorine. */
    Cl(17, "Cl", "Chlorine", 35.453000, 3.170000),

    /** Argon. */
    Ar(18, "Ar", "Argon", 39.948000, 1.784000),

    /** Potassium. */
    K(19, "K", "Potassium", 39.098300, 0.860000),

    /** Calcium. */
    Ca(20, "Ca", "Calcium", 40.080000, 1.550000),

    /** Scandium. */
    Sc(21, "Sc", "Scandium", 44.955900, 3.000000),

    /** Titanium. */
    Ti(22, "Ti", "Titanium", 47.900000, 4.500000),

    /** Vanadium. */
    V(23, "V", "Vanadium", 50.941500, 5.800000),

    /** Chromium. */
    Cr(24, "Cr", "Chromium", 51.996000, 7.190000),

    /** Manganese. */
    Mn(25, "Mn", "Manganese", 54.938000, 7.430000),

    /** Iron. */
    Fe(26, "Fe", "Iron", 55.847000, 7.860000),

    /** Cobalt. */
    Co(27, "Co", "Cobalt", 58.933200, 8.900000),

    /** Nickel. */
    Ni(28, "Ni", "Nickel", 58.700000, 8.900000),

    /** Copper. */
    Cu(29, "Cu", "Copper", 63.546000, 8.960000),

    /** Zinc. */
    Zn(30, "Zn", "Zinc", 65.380000, 7.140000),

    /** Gallium. */
    Ga(31, "Ga", "Gallium", 69.720000, 5.910000),

    /** Germanium. */
    Ge(32, "Ge", "Germanium", 72.590000, 5.320000),

    /** Arsenic. */
    As(33, "As", "Arsenic", 74.921600, 5.720000),

    /** Selenium. */
    Se(34, "Se", "Selenium", 78.960000, 4.800000),

    /** Bromine. */
    Br(35, "Br", "Bromine", 79.904000, 3.120000),

    /** Krypton. */
    Kr(36, "Kr", "Krypton", 83.800000, 3.740000),

    /** Rubidium. */
    Rb(37, "Rb", "Rubidium", 85.467800, 1.530000),

    /** Strontium. */
    Sr(38, "Sr", "Strontium", 87.620000, 2.600000),

    /** Yttrium. */
    Y(39, "Y", "Yttrium", 88.905600, 4.500000),

    /** Zirconium. */
    Zr(40, "Zr", "Zirconium", 91.220000, 6.490000),

    /** Niobium. */
    Nb(41, "Nb", "Niobium", 92.906400, 8.550000),

    /** Molybdenum. */
    Mo(42, "Mo", "Molybdenum", 95.940000, 10.200000),

    /** Technetium. */
    Tc(43, "Tc", "Technetium", 98.000000, 11.500000),

    /** Ruthenium. */
    Ru(44, "Ru", "Ruthenium", 101.070000, 12.200000),

    /** Rhodium. */
    Rh(45, "Rh", "Rhodium", 102.905500, 12.400000),

    /** Palladium. */
    Pd(46, "Pd", "Palladium", 106.400000, 12.000000),

    /** Silver. */
    Ag(47, "Ag", "Silver", 107.868000, 10.500000),

    /** Cadmium. */
    Cd(48, "Cd", "Cadmium", 112.410000, 8.650000),

    /** Indium. */
    In(49, "In", "Indium", 114.820000, 7.310000),

    /** Tin. */
    Sn(50, "Sn", "Tin", 118.690000, 7.300000),

    /** Antimony. */
    Sb(51, "Sb", "Antimony", 121.750000, 6.680000),

    /** Tellurium. */
    Te(52, "Te", "Tellurium", 127.600000, 6.240000),

    /** Iodine. */
    I(53, "I", "Iodine", 126.904500, 4.920000),

    /** Xenon. */
    Xe(54, "Xe", "Xenon", 131.300000, 5.890000),

    /** Cesium. */
    Cs(55, "Cs", "Cesium", 132.905400, 1.870000),

    /** Barium. */
    Ba(56, "Ba", "Barium", 137.330000, 3.500000),

    /** Lanthanum. */
    La(57, "La", "Lanthanum", 138.905500, 6.700000),

    /** Cerium. */
    Ce(58, "Ce", "Cerium", 140.120000, 6.780000),

    /** Praseodymium. */
    Pr(59, "Pr", "Praseodymium", 140.907700, 6.770000),

    /** Neodymium. */
    Nd(60, "Nd", "Neodymium", 144.240000, 7.000000),

    /** Promethium. */
    Pm(61, "Pm", "Promethium", 145.000000, 6.475000),

    /** Samarium. */
    Sm(62, "Sm", "Samarium", 150.400000, 7.540000),

    /** Europium. */
    Eu(63, "Eu", "Europium", 151.960000, 5.260000),

    /** Gadolinium. */
    Gd(64, "Gd", "Gadolinium", 157.250000, 7.890000),

    /** Terbium. */
    Tb(65, "Tb", "Terbium", 158.925400, 8.270000),

    /** Dysprosium. */
    Dy(66, "Dy", "Dysprosium", 162.500000, 8.540000),

    /** Holmium. */
    Ho(67, "Ho", "Holmium", 164.930400, 8.800000),

    /** Erbium. */
    Er(68, "Er", "Erbium", 167.260000, 9.050000),

    /** Thulium. */
    Tm(69, "Tm", "Thulium", 168.934200, 9.330000),

    /** Ytterbium. */
    Yb(70, "Yb", "Ytterbium", 173.040000, 6.980000),

    /** Lutetium. */
    Lu(71, "Lu", "Lutetium", 174.967000, 9.840000),

    /** Hafnium. */
    Hf(72, "Hf", "Hafnium", 178.490000, 13.100000),

    /** Tantalum. */
    Ta(73, "Ta", "Tantalum", 180.947900, 16.600000),

    /** Tungsten. */
    W(74, "W", "Tungsten", 183.850000, 19.300000),

    /** Rhenium. */
    Re(75, "Re", "Rhenium", 186.207000, 21.000000),

    /** Osmium. */
    Os(76, "Os", "Osmium", 190.200000, 22.400000),

    /** Iridium. */
    Ir(77, "Ir", "Iridium", 192.220000, 22.500000),

    /** Platinum. */
    Pt(78, "Pt", "Platinum", 195.090000, 21.400000),

    /** Gold. */
    Au(79, "Au", "Gold", 196.966500, 19.300000),

    /** Mercury. */
    Hg(80, "Hg", "Mercury", 200.590000, 13.530000),

    /** Thallium. */
    Tl(81, "Tl", "Thallium", 204.370000, 11.850000),

    /** Lead. */
    Pb(82, "Pb", "Lead", 207.200000, 11.400000),

    /** Bismuth. */
    Bi(83, "Bi", "Bismuth", 208.980400, 9.800000),

    /** Polonium. */
    Po(84, "Po", "Polonium", 209.000000, 9.400000),

    /** Astatine. */
    At(85, "At", "Astatine", 210.000000, 1.000000),

    /** Radon. */
    Rn(86, "Rn", "Radon", 222.000000, 9.910000),

    /** Francium. */
    Fr(87, "Fr", "Francium", 223.000000, 1.000000),

    /** Radium. */
    Ra(88, "Ra", "Radium", 226.025400, 5.000000),

    /** Actinium. */
    Ac(89, "Ac", "Actinium", 227.027800, 10.070000),

    /** Thorium. */
    Th(90, "Th", "Thorium", 232.038100, 11.700000),

    /** Protactinium. */
    Pa(91, "Pa", "Protactinium", 231.035900, 15.400000),

    /** Uranium. */
    U(92, "U", "Uranium", 238.029000, 18.900000),

    /** Neptunium. */
    Np(93, "Np", "Neptunium", 237.048200, 20.400000),

    /** Plutonium. */
    Pu(94, "Pu", "Plutonium", 244.000000, 19.800000),

    /** Americium. */
    Am(95, "Am", "Americium", 243.000000, 13.600000),

    /** Curium. */
    Cm(96, "Cm", "Curium", 247.000000, 13.511000);

    /** Map of each atomic number with its corresponding <code>Element</code>. */
    private static final Map<Integer, Element> ATOMICNUMBER_MAP;

    /** Map of each symbol with its corresponding <code>Element</code>. */
    private static final Map<String, Element> SYMBOL_MAP;

    static {
        ATOMICNUMBER_MAP = new HashMap<Integer, Element>();
        ATOMICNUMBER_MAP.put(1, H);
        ATOMICNUMBER_MAP.put(2, He);
        ATOMICNUMBER_MAP.put(3, Li);
        ATOMICNUMBER_MAP.put(4, Be);
        ATOMICNUMBER_MAP.put(5, B);
        ATOMICNUMBER_MAP.put(6, C);
        ATOMICNUMBER_MAP.put(7, N);
        ATOMICNUMBER_MAP.put(8, O);
        ATOMICNUMBER_MAP.put(9, F);
        ATOMICNUMBER_MAP.put(10, Ne);
        ATOMICNUMBER_MAP.put(11, Na);
        ATOMICNUMBER_MAP.put(12, Mg);
        ATOMICNUMBER_MAP.put(13, Al);
        ATOMICNUMBER_MAP.put(14, Si);
        ATOMICNUMBER_MAP.put(15, P);
        ATOMICNUMBER_MAP.put(16, S);
        ATOMICNUMBER_MAP.put(17, Cl);
        ATOMICNUMBER_MAP.put(18, Ar);
        ATOMICNUMBER_MAP.put(19, K);
        ATOMICNUMBER_MAP.put(20, Ca);
        ATOMICNUMBER_MAP.put(21, Sc);
        ATOMICNUMBER_MAP.put(22, Ti);
        ATOMICNUMBER_MAP.put(23, V);
        ATOMICNUMBER_MAP.put(24, Cr);
        ATOMICNUMBER_MAP.put(25, Mn);
        ATOMICNUMBER_MAP.put(26, Fe);
        ATOMICNUMBER_MAP.put(27, Co);
        ATOMICNUMBER_MAP.put(28, Ni);
        ATOMICNUMBER_MAP.put(29, Cu);
        ATOMICNUMBER_MAP.put(30, Zn);
        ATOMICNUMBER_MAP.put(31, Ga);
        ATOMICNUMBER_MAP.put(32, Ge);
        ATOMICNUMBER_MAP.put(33, As);
        ATOMICNUMBER_MAP.put(34, Se);
        ATOMICNUMBER_MAP.put(35, Br);
        ATOMICNUMBER_MAP.put(36, Kr);
        ATOMICNUMBER_MAP.put(37, Rb);
        ATOMICNUMBER_MAP.put(38, Sr);
        ATOMICNUMBER_MAP.put(39, Y);
        ATOMICNUMBER_MAP.put(40, Zr);
        ATOMICNUMBER_MAP.put(41, Nb);
        ATOMICNUMBER_MAP.put(42, Mo);
        ATOMICNUMBER_MAP.put(43, Tc);
        ATOMICNUMBER_MAP.put(44, Ru);
        ATOMICNUMBER_MAP.put(45, Rh);
        ATOMICNUMBER_MAP.put(46, Pd);
        ATOMICNUMBER_MAP.put(47, Ag);
        ATOMICNUMBER_MAP.put(48, Cd);
        ATOMICNUMBER_MAP.put(49, In);
        ATOMICNUMBER_MAP.put(50, Sn);
        ATOMICNUMBER_MAP.put(51, Sb);
        ATOMICNUMBER_MAP.put(52, Te);
        ATOMICNUMBER_MAP.put(53, I);
        ATOMICNUMBER_MAP.put(54, Xe);
        ATOMICNUMBER_MAP.put(55, Cs);
        ATOMICNUMBER_MAP.put(56, Ba);
        ATOMICNUMBER_MAP.put(57, La);
        ATOMICNUMBER_MAP.put(58, Ce);
        ATOMICNUMBER_MAP.put(59, Pr);
        ATOMICNUMBER_MAP.put(60, Nd);
        ATOMICNUMBER_MAP.put(61, Pm);
        ATOMICNUMBER_MAP.put(62, Sm);
        ATOMICNUMBER_MAP.put(63, Eu);
        ATOMICNUMBER_MAP.put(64, Gd);
        ATOMICNUMBER_MAP.put(65, Tb);
        ATOMICNUMBER_MAP.put(66, Dy);
        ATOMICNUMBER_MAP.put(67, Ho);
        ATOMICNUMBER_MAP.put(68, Er);
        ATOMICNUMBER_MAP.put(69, Tm);
        ATOMICNUMBER_MAP.put(70, Yb);
        ATOMICNUMBER_MAP.put(71, Lu);
        ATOMICNUMBER_MAP.put(72, Hf);
        ATOMICNUMBER_MAP.put(73, Ta);
        ATOMICNUMBER_MAP.put(74, W);
        ATOMICNUMBER_MAP.put(75, Re);
        ATOMICNUMBER_MAP.put(76, Os);
        ATOMICNUMBER_MAP.put(77, Ir);
        ATOMICNUMBER_MAP.put(78, Pt);
        ATOMICNUMBER_MAP.put(79, Au);
        ATOMICNUMBER_MAP.put(80, Hg);
        ATOMICNUMBER_MAP.put(81, Tl);
        ATOMICNUMBER_MAP.put(82, Pb);
        ATOMICNUMBER_MAP.put(83, Bi);
        ATOMICNUMBER_MAP.put(84, Po);
        ATOMICNUMBER_MAP.put(85, At);
        ATOMICNUMBER_MAP.put(86, Rn);
        ATOMICNUMBER_MAP.put(87, Fr);
        ATOMICNUMBER_MAP.put(88, Ra);
        ATOMICNUMBER_MAP.put(89, Ac);
        ATOMICNUMBER_MAP.put(90, Th);
        ATOMICNUMBER_MAP.put(91, Pa);
        ATOMICNUMBER_MAP.put(92, U);
        ATOMICNUMBER_MAP.put(93, Np);
        ATOMICNUMBER_MAP.put(94, Pu);
        ATOMICNUMBER_MAP.put(95, Am);
        ATOMICNUMBER_MAP.put(96, Cm);

        SYMBOL_MAP = new HashMap<String, Element>();
        SYMBOL_MAP.put("H", H);
        SYMBOL_MAP.put("He", He);
        SYMBOL_MAP.put("Li", Li);
        SYMBOL_MAP.put("Be", Be);
        SYMBOL_MAP.put("B", B);
        SYMBOL_MAP.put("C", C);
        SYMBOL_MAP.put("N", N);
        SYMBOL_MAP.put("O", O);
        SYMBOL_MAP.put("F", F);
        SYMBOL_MAP.put("Ne", Ne);
        SYMBOL_MAP.put("Na", Na);
        SYMBOL_MAP.put("Mg", Mg);
        SYMBOL_MAP.put("Al", Al);
        SYMBOL_MAP.put("Si", Si);
        SYMBOL_MAP.put("P", P);
        SYMBOL_MAP.put("S", S);
        SYMBOL_MAP.put("Cl", Cl);
        SYMBOL_MAP.put("Ar", Ar);
        SYMBOL_MAP.put("K", K);
        SYMBOL_MAP.put("Ca", Ca);
        SYMBOL_MAP.put("Sc", Sc);
        SYMBOL_MAP.put("Ti", Ti);
        SYMBOL_MAP.put("V", V);
        SYMBOL_MAP.put("Cr", Cr);
        SYMBOL_MAP.put("Mn", Mn);
        SYMBOL_MAP.put("Fe", Fe);
        SYMBOL_MAP.put("Co", Co);
        SYMBOL_MAP.put("Ni", Ni);
        SYMBOL_MAP.put("Cu", Cu);
        SYMBOL_MAP.put("Zn", Zn);
        SYMBOL_MAP.put("Ga", Ga);
        SYMBOL_MAP.put("Ge", Ge);
        SYMBOL_MAP.put("As", As);
        SYMBOL_MAP.put("Se", Se);
        SYMBOL_MAP.put("Br", Br);
        SYMBOL_MAP.put("Kr", Kr);
        SYMBOL_MAP.put("Rb", Rb);
        SYMBOL_MAP.put("Sr", Sr);
        SYMBOL_MAP.put("Y", Y);
        SYMBOL_MAP.put("Zr", Zr);
        SYMBOL_MAP.put("Nb", Nb);
        SYMBOL_MAP.put("Mo", Mo);
        SYMBOL_MAP.put("Tc", Tc);
        SYMBOL_MAP.put("Ru", Ru);
        SYMBOL_MAP.put("Rh", Rh);
        SYMBOL_MAP.put("Pd", Pd);
        SYMBOL_MAP.put("Ag", Ag);
        SYMBOL_MAP.put("Cd", Cd);
        SYMBOL_MAP.put("In", In);
        SYMBOL_MAP.put("Sn", Sn);
        SYMBOL_MAP.put("Sb", Sb);
        SYMBOL_MAP.put("Te", Te);
        SYMBOL_MAP.put("I", I);
        SYMBOL_MAP.put("Xe", Xe);
        SYMBOL_MAP.put("Cs", Cs);
        SYMBOL_MAP.put("Ba", Ba);
        SYMBOL_MAP.put("La", La);
        SYMBOL_MAP.put("Ce", Ce);
        SYMBOL_MAP.put("Pr", Pr);
        SYMBOL_MAP.put("Nd", Nd);
        SYMBOL_MAP.put("Pm", Pm);
        SYMBOL_MAP.put("Sm", Sm);
        SYMBOL_MAP.put("Eu", Eu);
        SYMBOL_MAP.put("Gd", Gd);
        SYMBOL_MAP.put("Tb", Tb);
        SYMBOL_MAP.put("Dy", Dy);
        SYMBOL_MAP.put("Ho", Ho);
        SYMBOL_MAP.put("Er", Er);
        SYMBOL_MAP.put("Tm", Tm);
        SYMBOL_MAP.put("Yb", Yb);
        SYMBOL_MAP.put("Lu", Lu);
        SYMBOL_MAP.put("Hf", Hf);
        SYMBOL_MAP.put("Ta", Ta);
        SYMBOL_MAP.put("W", W);
        SYMBOL_MAP.put("Re", Re);
        SYMBOL_MAP.put("Os", Os);
        SYMBOL_MAP.put("Ir", Ir);
        SYMBOL_MAP.put("Pt", Pt);
        SYMBOL_MAP.put("Au", Au);
        SYMBOL_MAP.put("Hg", Hg);
        SYMBOL_MAP.put("Tl", Tl);
        SYMBOL_MAP.put("Pb", Pb);
        SYMBOL_MAP.put("Bi", Bi);
        SYMBOL_MAP.put("Po", Po);
        SYMBOL_MAP.put("At", At);
        SYMBOL_MAP.put("Rn", Rn);
        SYMBOL_MAP.put("Fr", Fr);
        SYMBOL_MAP.put("Ra", Ra);
        SYMBOL_MAP.put("Ac", Ac);
        SYMBOL_MAP.put("Th", Th);
        SYMBOL_MAP.put("Pa", Pa);
        SYMBOL_MAP.put("U", U);
        SYMBOL_MAP.put("Np", Np);
        SYMBOL_MAP.put("Pu", Pu);
        SYMBOL_MAP.put("Am", Am);
        SYMBOL_MAP.put("Cm", Cm);
    }



    /**
     * Returns an <code>Element</code> of the specified atomic number.
     * 
     * @param z
     *            atomic number
     * @return corresponding <code>Element</code>
     * @throws IllegalArgumentException
     *             if no element is associated with the specified atomic number
     */
    public static Element fromZ(int z) {
        Element element = ATOMICNUMBER_MAP.get(z);
        if (element == null)
            throw new IllegalArgumentException(
                    "No element associated with atomic number: " + z);
        return element;
    }



    /**
     * Returns an <code>Element</code> of the specified symbol.
     * 
     * @param symbol
     *            symbol of the element (e.g. Cu)
     * @return corresponding <code>Element</code>
     * @throws IllegalArgumentException
     *             if no element is associated with the specified symbol
     */
    public static Element fromSymbol(String symbol) {
        Element element = SYMBOL_MAP.get(symbol);
        if (element == null)
            throw new IllegalArgumentException(
                    "No element associated with symbol: " + symbol);
        return element;
    }

    /** Atomic number. */
    private final int atomicNumber;

    /** Symbol. */
    private final String symbol;

    /** Full name of the element. */
    private final String name;

    /** Atomic mass (g/mol). */
    private double atomicMass;

    /** Mass density (g/cm3 = kg/m3). */
    private double density;



    /**
     * Creates a new element.
     * 
     * @param atomicNumber
     *            atomic number
     * @param symbol
     *            symbol
     * @param name
     *            full name
     * @param atomicMass
     *            atomic mass (g/mol)
     * @param density
     *            mass density (g/cm3)
     */
    private Element(int atomicNumber, String symbol, String name,
            double atomicMass, double density) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.name = name;
        this.atomicMass = atomicMass;
        this.density = density;
    }



    /**
     * Returns the atomic number.
     * 
     * @return atomic number
     */
    public int z() {
        return atomicNumber;
    }



    /**
     * Returns the symbol/abbreviation.
     * 
     * @return symbol
     */
    public String symbol() {
        return symbol;
    }



    /**
     * Returns the full name (in English).
     * 
     * @return name
     */
    public String fullName() {
        return name;
    }



    /**
     * Returns the atomic mass (g/mol).
     * 
     * @return atomic mass (g/mol)
     */
    public double atomicMass() {
        return atomicMass;
    }



    /**
     * Returns the mass density (g/cm3 = kg/m3).
     * 
     * @return mass density (g/cm3 = kg/m3)
     */
    public double density() {
        return density;
    }



    /**
     * Equivalent to {@link #symbol()}.
     * 
     * @return symbol
     */
    @Override
    public String toString() {
        return symbol;
    }
}
