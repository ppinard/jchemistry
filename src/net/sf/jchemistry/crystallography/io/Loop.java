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

/**
 * Interface to specify the data inside a looped list for the
 * <code>CifWriter</code>.
 * 
 * @author ppinard
 */
public interface Loop {

    /**
     * Returns the number of rows in the looped list.
     * 
     * @return number of rows
     */
    public int getRowCount();



    /**
     * Returns the number of columns in the looped list. The columns correspond
     * to the the number of tags.
     * 
     * @return number of columns
     */
    public int getColumnCount();



    /**
     * Returns the data tag of the specified column.
     * 
     * @param column
     *            index of the column between 0 and {@link #getColumnCount()}
     * @return data tag
     */
    public String getDataTag(int column);



    /**
     * Returns the data value of the specified row and column.
     * 
     * @param row
     *            index of the row between 0 and {@link #getRowCount()}
     * @param column
     *            index of the column between 0 and {@link #getColumnCount()}
     * @return value
     */
    public String getDataValue(int row, int column);

}
