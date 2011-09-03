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
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.sf.jchemistry.crystallography.core.Reflector;
import net.sf.jchemistry.crystallography.core.Reflectors;

/**
 * Model for the reflectors table.
 * 
 * @author ppinard
 */
public class ReflectorsTableModel extends AbstractTableModel {

    /** Serial version UID. */
    private static final long serialVersionUID = 8636416725601572329L;

    /** Set of reflectors. */
    private final Reflectors reflsSet;

    /** List of reflectors. */
    private final List<Reflector> reflsList;

    /** Name of the columns. */
    private final String[] columnNames = { "h", "k", "l", "intensity" };



    /**
     * Creates a new <code>ReflectorsTableModel</code>.
     */
    public ReflectorsTableModel() {
        reflsSet = new Reflectors();
        reflsList = new ArrayList<Reflector>();
    }



    /**
     * Appends a new reflector to the end of the table. If a reflector with the
     * same indices as the new one already exists, it is not added and the
     * method returns <code>false</code>.
     * 
     * @param refl
     *            new reflector
     * @return <code>true</code> if the atom is added to the list,
     *         <code>false</code> otherwise (if another reflector already exists
     *         with the same indices).
     */
    public boolean append(Reflector refl) {
        boolean answer = reflsSet.add(refl);
        if (answer) {
            reflsList.add(refl);

            int row = reflsList.size() - 1;
            fireTableRowsInserted(row, row);

            return true;
        } else {
            return false;
        }
    }



    /**
     * Appends a collection of reflectors.
     * 
     * @param refls
     *            new reflectors
     * @return <code>true</code> if all the reflectors are added,
     *         <code>false</code> otherwise (if another reflector already exists
     *         with the same indices).
     */
    public boolean appendAll(Reflectors refls) {
        boolean answer = reflsSet.addAll(refls);
        if (answer) {
            int firstRow = reflsList.size();

            reflsList.addAll(refls);

            int lastRow = reflsList.size() - 1;
            fireTableRowsInserted(firstRow, lastRow);

            return true;
        } else {
            return false;
        }
    }



    /**
     * Clears all reflectors from the table.
     */
    public void clear() {
        reflsSet.clear();
        reflsList.clear();
        fireTableDataChanged();
    }



    @Override
    public int getColumnCount() {
        return columnNames.length;
    }



    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }



    /**
     * Returns the reflectors.
     * 
     * @return reflectors
     */
    public Reflectors getReflectors() {
        Reflectors copy = new Reflectors();
        copy.addAll(reflsSet);
        return copy;
    }



    @Override
    public int getRowCount() {
        return reflsList.size();
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reflector refl = reflsList.get(rowIndex);

        switch (columnIndex) {
        case 0:
            return refl.getH();
        case 1:
            return refl.getK();
        case 2:
            return refl.getL();
        case 3:
            return refl.getIntensity();
        default:
            throw new IllegalArgumentException("Unknown column: " + columnIndex);
        }
    }



    /**
     * Removes the reflector at the specified row.
     * 
     * @param row
     *            row of the reflector in the table
     * @return <code>true</code> if the reflector is removed, <code>false</code>
     *         otherwise (invalid row, impossible to remove this reflector).
     */
    public boolean remove(int row) {
        if (row < 0 || row >= reflsList.size())
            return false;

        Reflector refl = reflsList.get(row);

        if (reflsSet.remove(refl)) {
            reflsList.remove(refl);

            fireTableRowsDeleted(row, row);

            return true;
        } else {
            return false;
        }
    }
}