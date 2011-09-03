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
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.core.ElementComparator;
import net.sf.jchemistry.crystallography.core.AtomSite;
import net.sf.jchemistry.crystallography.core.AtomSites;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflector;
import net.sf.jchemistry.crystallography.core.Reflectors;
import net.sf.jchemistry.crystallography.core.UnitCell;

import static net.sf.jchemistry.crystallography.io.CifConstants.*;

/**
 * Saver of a phase from a crystallography information file (CIF).
 * 
 * @author Philippe T. Pinard
 */
public class CifSaver {

    /**
     * Comparator for <code>AtomSite</code> according to their element.
     */
    private static final Comparator<AtomSite> ATOMSITE_COMPARATOR =
            new Comparator<AtomSite>() {

                /** Element comparator. */
                private final Comparator<Element> elementComparator =
                        new ElementComparator();



                @Override
                public int compare(AtomSite atom0, AtomSite atom1) {
                    int c =
                            elementComparator.compare(atom0.getElement(),
                                    atom1.getElement());
                    if (c != 0)
                        return c;

                    c = Double.compare(atom0.getCharge(), atom1.getCharge());
                    if (c != 0)
                        return c;

                    c =
                            Double.compare(atom0.getPosition().getX(),
                                    atom1.getPosition().getX());
                    if (c != 0)
                        return c;

                    c =
                            Double.compare(atom0.getPosition().getY(),
                                    atom1.getPosition().getY());
                    if (c != 0)
                        return c;

                    return Double.compare(atom0.getPosition().getZ(),
                            atom1.getPosition().getZ());
                }

            };

    /**
     * Loop to save atom positions.
     * 
     * @author ppinard
     */
    private static class AtomsLoop implements Loop {

        /** List of atom sites. */
        private final List<AtomSite> atoms;

        /** List of atom labels. */
        private final List<String> labels;



        /**
         * Creates a new <code>AtomsLoop</code>.
         * 
         * @param atoms
         *            atom sites
         */
        public AtomsLoop(AtomSites atoms) {
            this.atoms = new ArrayList<AtomSite>();
            this.atoms.addAll(atoms);
            Collections.sort(this.atoms, ATOMSITE_COMPARATOR);

            // Generate labels
            labels = new ArrayList<String>();
            int index = 0;
            Element currentElement;
            Element previousElement = null;
            for (AtomSite atom : this.atoms) {
                currentElement = atom.getElement();

                if (currentElement == previousElement) {
                    index += 1;
                } else {
                    index = 1;
                    previousElement = currentElement;
                }

                labels.add(currentElement.symbol() + index);
            }
        }



        @Override
        public int getRowCount() {
            return atoms.size();
        }



        @Override
        public int getColumnCount() {
            return 6;
        }



        @Override
        public String getDataTag(int column) {
            switch (column) {
            case 0:
                return ATOM_SITE_LABEL;
            case 1:
                return ATOM_SITE_TYPE_SYMBOL;
            case 2:
                return ATOM_SITE_FRACT_X;
            case 3:
                return ATOM_SITE_FRACT_Y;
            case 4:
                return ATOM_SITE_FRACT_Z;
            case 5:
                return ATOM_SITE_OCCUPANCY;
            default:
                throw new IllegalArgumentException("Column " + column
                        + " is unknown");
            }
        }



        @Override
        public String getDataValue(int row, int column) {
            AtomSite atom = atoms.get(row);

            switch (column) {
            case 0:
                return labels.get(row);
            case 1:
                if (atom.getCharge() == 0)
                    return atom.getElement().symbol();
                else if (atom.getCharge() > 0)
                    return atom.getElement().symbol() + atom.getCharge() + "+";
                else
                    return atom.getElement().symbol()
                            + Math.abs(atom.getCharge()) + "-";
            case 2:
                return FORMAT.format(atom.getPosition().getX());
            case 3:
                return FORMAT.format(atom.getPosition().getY());
            case 4:
                return FORMAT.format(atom.getPosition().getZ());
            case 5:
                return FORMAT.format(atom.getOccupancy());
            default:
                throw new IllegalArgumentException("Column " + column
                        + " is unknown");
            }
        }
    }

    /**
     * Comparator for <code>Reflector</code> according to their indices.
     */
    private static final Comparator<Reflector> INDICES_COMPARATOR =
            new Comparator<Reflector>() {

                @Override
                public int compare(Reflector refl0, Reflector refl1) {
                    int c = Double.compare(refl0.getH(), refl1.getH());
                    if (c != 0)
                        return c;

                    c = Double.compare(refl0.getK(), refl1.getK());
                    if (c != 0)
                        return c;

                    return Double.compare(refl0.getL(), refl1.getL());
                }

            };

    /**
     * Loop to save the reflectors.
     * 
     * @author ppinard
     */
    private static class ReflectorsLoop implements Loop {

        /** List of reflectors. */
        private List<Reflector> refls;



        /**
         * Creates a new <code>ReflectorsLoop</code>.
         * 
         * @param refls
         *            reflectors
         */
        public ReflectorsLoop(Reflectors refls) {
            this.refls = new ArrayList<Reflector>();
            this.refls.addAll(refls);
            Collections.sort(this.refls, INDICES_COMPARATOR);
        }



        @Override
        public int getRowCount() {
            return refls.size();
        }



        @Override
        public int getColumnCount() {
            return 4;
        }



        @Override
        public String getDataTag(int column) {
            switch (column) {
            case 0:
                return REFLN_INDEX_H;
            case 1:
                return REFLN_INDEX_K;
            case 2:
                return REFLN_INDEX_L;
            case 3:
                return REFLN_INTENSITY_CALC;
            default:
                throw new IllegalArgumentException("Column " + column
                        + " is unknown");
            }
        }



        @Override
        public String getDataValue(int row, int column) {
            Reflector refl = refls.get(row);

            switch (column) {
            case 0:
                return Integer.toString(refl.getH());
            case 1:
                return Integer.toString(refl.getK());
            case 2:
                return Integer.toString(refl.getL());
            case 3:
                return FORMAT.format(refl.getIntensity());
            default:
                throw new IllegalArgumentException("Column " + column
                        + " is unknown");
            }
        }

    }

    /** CIF writer. */
    private CifWriter writer;



    /**
     * Writes the CIF information about the specified phase using the specified
     * writer.
     * 
     * @param out
     *            output writer
     * @param phase
     *            phase
     * @param blockCode
     *            header of the data block
     * @throws IOException
     *             if an exception occurs while writing the CIF
     */
    public void save(Writer out, Phase phase, String blockCode)
            throws IOException {
        // Create writer
        writer = new CifWriter(out);

        writer.writeDataBlock(blockCode);

        // Name
        writer.writeDataItem(CHEMICAL_NAME_COMMON, phase.getName(), true);

        // Citation
        writer.writeDataItem(PUBL_SECTION_REFERENCES, phase.getCitation(), true);

        // Unit cell
        UnitCell unitCell = phase.getUnitCell();
        writer.writeDataItem(CELL_LENGTH_A, unitCell.getA());
        writer.writeDataItem(CELL_LENGTH_B, unitCell.getB());
        writer.writeDataItem(CELL_LENGTH_C, unitCell.getC());
        writer.writeDataItem(CELL_ANGLE_ALPHA,
                Math.toDegrees(unitCell.getAlpha()));
        writer.writeDataItem(CELL_ANGLE_BETA,
                Math.toDegrees(unitCell.getBeta()));
        writer.writeDataItem(CELL_ANGLE_GAMMA,
                Math.toDegrees(unitCell.getGamma()));

        // Space group
        writer.writeDataItem(SPACE_GROUP_IT_NUMBER,
                phase.getSpaceGroup().getIndex());
        writer.writeDataItem(SYMMETRY_SPACE_GROUP_NAME_HM,
                phase.getSpaceGroup().getSymbol(), true);

        // Atoms
        writer.writeLoop(new AtomsLoop(phase.getAtoms()));

        // Reflectors
        writer.writeLoop(new ReflectorsLoop(phase.getReflectors()));

        // Reset
        writer = null;
    }



    /**
     * Writes the CIF information about the specified phase using the specified
     * writer.
     * 
     * @param out
     *            output writer
     * @param phase
     *            phase
     * @throws IOException
     *             if an exception occurs while writing the CIF
     */
    public void save(Writer out, Phase phase) throws IOException {
        save(out, phase, "");
    }
}
