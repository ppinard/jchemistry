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
package net.sf.jchemistry.crystallography.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sf.jchemistry.crystallography.core.AtomSite;
import net.sf.jchemistry.crystallography.core.AtomSites;

/**
 * Model for the atoms table.
 * 
 * @author ppinard
 */
public class AtomsTableModel extends AbstractTableModel {

    /** Serial version UID. */
    private static final long serialVersionUID = -3020096058553116305L;

    /** Set of atoms. */
    private final AtomSites atomsSet;

    /** List of atoms. */
    private final List<AtomSite> atomsList;

    /** Name of the columns. */
    private final String[] columnNames = { "Element", "Charge", "x", "y", "z",
            "Occup." };



    /**
     * Creates a new <code>AtomsTableModel</code>.
     */
    public AtomsTableModel() {
        atomsSet = new AtomSites();
        atomsList = new ArrayList<AtomSite>();
    }



    /**
     * Appends a new atom to the end of the table. If an atom already exists at
     * the new atom's position, it is not added and the method returns
     * <code>false</code>.
     * 
     * @param atom
     *            new atom
     * @return <code>true</code> if the atom is added to the list,
     *         <code>false</code> otherwise (if another atom already exists at
     *         the new atom's position).
     */
    public boolean append(AtomSite atom) {
        boolean answer = atomsSet.add(atom);
        if (answer) {
            atomsList.add(atom);

            int row = atomsList.size() - 1;
            fireTableRowsInserted(row, row);

            return true;
        } else {
            return false;
        }
    }



    /**
     * Appends a collection of atom sites.
     * 
     * @param atoms
     *            new atoms
     * @return <code>true</code> if all the atoms are added, <code>false</code>
     *         otherwise (if another atom already exists at the new atom's
     *         position).
     */
    public boolean appendAll(Collection<? extends AtomSite> atoms) {
        boolean answer = atomsSet.addAll(atoms);
        if (answer) {
            int firstRow = atomsList.size();

            atomsList.addAll(atoms);

            int lastRow = atomsList.size() - 1;
            fireTableRowsInserted(firstRow, lastRow);

            return true;
        } else {
            return false;
        }
    }



    /**
     * Clears all atoms from the table.
     */
    public void clear() {
        atomsSet.clear();
        atomsList.clear();
        fireTableDataChanged();
    }



    /**
     * Returns the atom sites.
     * 
     * @return atom sites
     */
    public AtomSites getAtomSites() {
        AtomSites copy = new AtomSites();
        copy.addAll(atomsSet);
        return copy;
    }



    @Override
    public int getColumnCount() {
        return columnNames.length;
    }



    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }



    @Override
    public int getRowCount() {
        return atomsList.size();
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AtomSite atom = atomsList.get(rowIndex);

        switch (columnIndex) {
        case 0:
            return atom.getElement();
        case 1:
            return atom.getCharge();
        case 2:
            return atom.getPosition().getX();
        case 3:
            return atom.getPosition().getY();
        case 4:
            return atom.getPosition().getZ();
        case 5:
            return atom.getOccupancy();
        default:
            throw new IllegalArgumentException("Unknown column: " + columnIndex);
        }
    }



    /**
     * Removes the atom at the specified row.
     * 
     * @param row
     *            row of the atom in the table
     * @return <code>true</code> if the atom is removed, <code>false</code>
     *         otherwise (invalid row, impossible to remove this atom).
     */
    public boolean remove(int row) {
        if (row < 0 || row >= atomsList.size())
            return false;

        AtomSite atom = atomsList.get(row);

        if (atomsSet.remove(atom)) {
            atomsList.remove(atom);

            fireTableRowsDeleted(row, row);

            return true;
        } else {
            return false;
        }
    }
}
