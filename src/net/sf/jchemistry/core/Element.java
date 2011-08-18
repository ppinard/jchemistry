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
package net.sf.jchemistry.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of the periodic table elements.
 * 
 * @author Philippe T. Pinard
 */
public enum Element {
    /** Hydrogen. */
    H(1, "H", "Hydrogen"),

    /** Helium. */
    He(2, "He", "Helium"),

    /** Lithium. */
    Li(3, "Li", "Lithium"),

    /** Beryllium. */
    Be(4, "Be", "Beryllium"),

    /** Boron. */
    B(5, "B", "Boron"),

    /** Carbon. */
    C(6, "C", "Carbon"),

    /** Nitrogen. */
    N(7, "N", "Nitrogen"),

    /** Oxygen. */
    O(8, "O", "Oxygen"),

    /** Fluorine. */
    F(9, "F", "Fluorine"),

    /** Neon. */
    Ne(10, "Ne", "Neon"),

    /** Sodium. */
    Na(11, "Na", "Sodium"),

    /** Magnesium. */
    Mg(12, "Mg", "Magnesium"),

    /** Aluminium. */
    Al(13, "Al", "Aluminium"),

    /** Silicon. */
    Si(14, "Si", "Silicon"),

    /** Phosphorus. */
    P(15, "P", "Phosphorus"),

    /** Sulfur. */
    S(16, "S", "Sulfur"),

    /** Chlorine. */
    Cl(17, "Cl", "Chlorine"),

    /** Argon. */
    Ar(18, "Ar", "Argon"),

    /** Potassium. */
    K(19, "K", "Potassium"),

    /** Calcium. */
    Ca(20, "Ca", "Calcium"),

    /** Scandium. */
    Sc(21, "Sc", "Scandium"),

    /** Titanium. */
    Ti(22, "Ti", "Titanium"),

    /** Vanadium. */
    V(23, "V", "Vanadium"),

    /** Chromium. */
    Cr(24, "Cr", "Chromium"),

    /** Manganese. */
    Mn(25, "Mn", "Manganese"),

    /** Iron. */
    Fe(26, "Fe", "Iron"),

    /** Cobalt. */
    Co(27, "Co", "Cobalt"),

    /** Nickel. */
    Ni(28, "Ni", "Nickel"),

    /** Copper. */
    Cu(29, "Cu", "Copper"),

    /** Zinc. */
    Zn(30, "Zn", "Zinc"),

    /** Gallium. */
    Ga(31, "Ga", "Gallium"),

    /** Germanium. */
    Ge(32, "Ge", "Germanium"),

    /** Arsenic. */
    As(33, "As", "Arsenic"),

    /** Selenium. */
    Se(34, "Se", "Selenium"),

    /** Bromine. */
    Br(35, "Br", "Bromine"),

    /** Krypton. */
    Kr(36, "Kr", "Krypton"),

    /** Rubidium. */
    Rb(37, "Rb", "Rubidium"),

    /** Strontium. */
    Sr(38, "Sr", "Strontium"),

    /** Yttrium. */
    Y(39, "Y", "Yttrium"),

    /** Zirconium. */
    Zr(40, "Zr", "Zirconium"),

    /** Niobium. */
    Nb(41, "Nb", "Niobium"),

    /** Molybdenum. */
    Mo(42, "Mo", "Molybdenum"),

    /** Technetium. */
    Tc(43, "Tc", "Technetium"),

    /** Ruthenium. */
    Ru(44, "Ru", "Ruthenium"),

    /** Rhodium. */
    Rh(45, "Rh", "Rhodium"),

    /** Palladium. */
    Pd(46, "Pd", "Palladium"),

    /** Silver. */
    Ag(47, "Ag", "Silver"),

    /** Cadmium. */
    Cd(48, "Cd", "Cadmium"),

    /** Indium. */
    In(49, "In", "Indium"),

    /** Tin. */
    Sn(50, "Sn", "Tin"),

    /** Antimony. */
    Sb(51, "Sb", "Antimony"),

    /** Tellurium. */
    Te(52, "Te", "Tellurium"),

    /** Iodine. */
    I(53, "I", "Iodine"),

    /** Xenon. */
    Xe(54, "Xe", "Xenon"),

    /** Cesium. */
    Cs(55, "Cs", "Cesium"),

    /** Barium. */
    Ba(56, "Ba", "Barium"),

    /** Lanthanum. */
    La(57, "La", "Lanthanum"),

    /** Cerium. */
    Ce(58, "Ce", "Cerium"),

    /** Praseodymium. */
    Pr(59, "Pr", "Praseodymium"),

    /** Neodymium. */
    Nd(60, "Nd", "Neodymium"),

    /** Promethium. */
    Pm(61, "Pm", "Promethium"),

    /** Samarium. */
    Sm(62, "Sm", "Samarium"),

    /** Europium. */
    Eu(63, "Eu", "Europium"),

    /** Gadolinium. */
    Gd(64, "Gd", "Gadolinium"),

    /** Terbium. */
    Tb(65, "Tb", "Terbium"),

    /** Dysprosium. */
    Dy(66, "Dy", "Dysprosium"),

    /** Holmium. */
    Ho(67, "Ho", "Holmium"),

    /** Erbium. */
    Er(68, "Er", "Erbium"),

    /** Thulium. */
    Tm(69, "Tm", "Thulium"),

    /** Ytterbium. */
    Yb(70, "Yb", "Ytterbium"),

    /** Lutetium. */
    Lu(71, "Lu", "Lutetium"),

    /** Hafnium. */
    Hf(72, "Hf", "Hafnium"),

    /** Tantalum. */
    Ta(73, "Ta", "Tantalum"),

    /** Tungsten. */
    W(74, "W", "Tungsten"),

    /** Rhenium. */
    Re(75, "Re", "Rhenium"),

    /** Osmium. */
    Os(76, "Os", "Osmium"),

    /** Iridium. */
    Ir(77, "Ir", "Iridium"),

    /** Platinum. */
    Pt(78, "Pt", "Platinum"),

    /** Gold. */
    Au(79, "Au", "Gold"),

    /** Mercury. */
    Hg(80, "Hg", "Mercury"),

    /** Thallium. */
    Tl(81, "Tl", "Thallium"),

    /** Lead. */
    Pb(82, "Pb", "Lead"),

    /** Bismuth. */
    Bi(83, "Bi", "Bismuth"),

    /** Polonium. */
    Po(84, "Po", "Polonium"),

    /** Astatine. */
    At(85, "At", "Astatine"),

    /** Radon. */
    Rn(86, "Rn", "Radon"),

    /** Francium. */
    Fr(87, "Fr", "Francium"),

    /** Radium. */
    Ra(88, "Ra", "Radium"),

    /** Actinium. */
    Ac(89, "Ac", "Actinium"),

    /** Thorium. */
    Th(90, "Th", "Thorium"),

    /** Protactinium. */
    Pa(91, "Pa", "Protactinium"),

    /** Uranium. */
    U(92, "U", "Uranium"),

    /** Neptunium. */
    Np(93, "Np", "Neptunium"),

    /** Plutonium. */
    Pu(94, "Pu", "Plutonium"),

    /** Americium. */
    Am(95, "Am", "Americium"),

    /** Curium. */
    Cm(96, "Cm", "Curium"),

    /** Berkelium. */
    Bk(97, "Bk", "Berkelium"),

    /** Californium. */
    Cf(98, "Cf", "Californium"),

    /** Einsteinium. */
    Es(99, "Es", "Einsteinium"),

    /** Fermium. */
    Fm(100, "Fm", "Fermium"),

    /** Mendelevium. */
    Md(101, "Md", "Mendelevium"),

    /** Nobelium. */
    No(102, "No", "Nobelium"),

    /** Lawrencium. */
    Lr(103, "Lr", "Lawrencium"),

    /** Rutherfordium. */
    Rf(104, "Rf", "Rutherfordium"),

    /** Dubnium. */
    Db(105, "Db", "Dubnium"),

    /** Seaborgium. */
    Sg(106, "Sg", "Seaborgium"),

    /** Bohrium. */
    Bh(107, "Bh", "Bohrium"),

    /** Hassium. */
    Hs(108, "Hs", "Hassium"),

    /** Meitnerium. */
    Mt(109, "Mt", "Meitnerium"),

    /** Darmstadtium. */
    Ds(110, "Ds", "Darmstadtium"),

    /** Roentgenium. */
    Rg(111, "Rg", "Roentgenium"),

    /** Copernicium. */
    Cn(112, "Cn", "Copernicium");

    /** Map of each atomic number with its corresponding <code>Element</code>. */
    private static final Map<Integer, Element> ATOMICNUMBER;

    /** Map of each symbol with its corresponding <code>Element</code>. */
    private static final Map<String, Element> SYMBOL;

    static {
        ATOMICNUMBER = new HashMap<Integer, Element>();
        for (Element element : values())
            ATOMICNUMBER.put(element.z(), element);

        SYMBOL = new HashMap<String, Element>();
        for (Element element : values())
            SYMBOL.put(element.symbol(), element);
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
        if (!ATOMICNUMBER.containsKey(z))
            throw new IllegalArgumentException(
                    "No element associated with atomic number: " + z);
        return ATOMICNUMBER.get(z);
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
        if (!SYMBOL.containsKey(symbol))
            throw new IllegalArgumentException(
                    "No element associated with symbol: " + symbol);
        return SYMBOL.get(symbol);
    }

    /** Atomic number. */
    private final int atomicNumber;

    /** Symbol. */
    private final String symbol;

    /** Full name of the element. */
    private final String name;



    /**
     * Creates a new element.
     * 
     * @param atomicNumber
     *            atomic number
     * @param symbol
     *            symbol
     * @param name
     *            full name
     */
    private Element(int atomicNumber, String symbol, String name) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.name = name;
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
     * Equivalent to {@link #symbol()}.
     * 
     * @return symbol
     */
    @Override
    public String toString() {
        return symbol;
    }
}
