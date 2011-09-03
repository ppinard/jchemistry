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
import java.util.regex.Pattern;

import static net.sf.jchemistry.crystallography.io.CifConstants.FORMAT;

/**
 * Writer of a crystallographic information file (CIF). For the terminology used
 * in the javadoc, please refer to the International Tables for Crystallography,
 * Vol. G, Chapter 2.2, pp. 20-36.
 * 
 * @author ppinard
 */
public class CifWriter {

    /** Internal writer. */
    private final Writer out;

    /** System's line separation. */
    private static final String LINE_SEP = System.getProperty("line.separator");

    /** Maximum length of a line (= 80). */
    private static final int LINE_LENGTH = 80;

    /** Pattern to check for correctness of data tag. */
    private static final Pattern TAG_PATTERN = Pattern.compile("^\\S*$");



    /**
     * Creates a new <code>CifWriter</code>.
     * 
     * @param out
     *            output writer
     */
    public CifWriter(Writer out) {
        if (out == null)
            throw new NullPointerException("out == null");

        this.out = out;
    }



    /**
     * Writes the <code>data_{blockCode}</code> header.
     * 
     * @param blockCode
     *            block code (can be an empty string)
     * @throws IOException
     *             if an error occurs while writing
     */
    public void writeDataBlock(String blockCode) throws IOException {
        if (blockCode == null)
            throw new NullPointerException("blockCode == null");
        if (!TAG_PATTERN.matcher(blockCode).matches())
            throw new IllegalArgumentException("Block code (" + blockCode
                    + ") cannot contain whitespace");

        writeLine("data_" + blockCode);
    }



    /**
     * Writes a data item.
     * 
     * @param tag
     *            tag (without the leading underscore)
     * @param value
     *            value
     * @throws IOException
     *             if an error occurs while writing
     */
    public void writeDataItem(String tag, int value) throws IOException {
        checkTag(tag);
        writeLine("_" + tag + " " + Integer.toString(value));
    }



    /**
     * Writes a data item.
     * 
     * @param tag
     *            tag (without the leading underscore)
     * @param value
     *            value
     * @throws IOException
     *             if an error occurs while writing
     */
    public void writeDataItem(String tag, double value) throws IOException {
        checkTag(tag);
        writeLine("_" + tag + " " + FORMAT.format(value));
    }



    /**
     * Writes a data item.
     * 
     * @param tag
     *            tag (without the leading underscore)
     * @param value
     *            value
     * @param quote
     *            whether to include quote <code>'</code> before and after the
     *            value. Note that if the value is longer than 80 characters,
     *            the quoted value is put over several lines using the
     *            <code>;</code>
     * @throws IOException
     *             if an error occurs while writing
     */
    public void writeDataItem(String tag, String value, boolean quote)
            throws IOException {
        checkTag(tag);

        if (quote) {
            if ((value.length() + tag.length() + 4) > LINE_LENGTH) {
                writeLine("_" + tag);
                writeLine(";");
                writeLongLine(value, "");
                writeLine(";");
            } else {
                writeLine("_" + tag + " '" + value + "'");
            }
        } else {
            writeLine("_" + tag + " " + value);
        }
    }



    /**
     * Writes a looped list of data item.
     * 
     * @param loop
     *            loop
     * @throws IOException
     *             if an error occurs while writing
     */
    public void writeLoop(Loop loop) throws IOException {
        if (loop == null)
            throw new NullPointerException("loop == null");

        writeLine("loop_");

        // Tags
        String tag;
        for (int i = 0; i < loop.getColumnCount(); i++) {
            tag = loop.getDataTag(i);
            checkTag(tag);
            writeLine("_" + tag);
        }

        // Values
        for (int i = 0; i < loop.getRowCount(); i++) {
            for (int j = 0; j < loop.getColumnCount(); j++) {
                out.write(loop.getDataValue(i, j));

                // do not write space after last value
                if (j < (loop.getColumnCount() - 1))
                    out.write(" ");
            }

            out.write(LINE_SEP);
        }
    }



    /**
     * Writes a comment.
     * 
     * @param comment
     *            comment
     * @throws IOException
     *             if an error occurs while writing
     */
    public void writeComment(String comment) throws IOException {
        writeLongLine(comment, "#");
    }



    /**
     * Writes the specified line with the system's line separator.
     * 
     * @param line
     *            line
     * @throws IOException
     *             if an error occurs while writing
     */
    private void writeLine(String line) throws IOException {
        out.write(line);
        out.write(LINE_SEP);
    }



    /**
     * Writes a line over several lines so that each line respected the maximum
     * line length (@link {@link #LINE_LENGTH}).
     * 
     * @param longLine
     *            a long line possibly more than the maximum line length
     * @param prefix
     *            prefix to add in front of each line
     * @throws IOException
     *             if an error occurs while writing
     */
    private void writeLongLine(String longLine, String prefix)
            throws IOException {
        String[] words = longLine.split(" ");
        StringBuffer line = new StringBuffer(LINE_LENGTH);

        for (String word : words) {
            if ((line.length() + word.length() + prefix.length()) >= LINE_LENGTH) {
                writeLine(prefix + line.toString().trim());
                line.setLength(0);
            }

            line.append(word + " ");
        }

        if (line.length() > 0)
            writeLine(prefix + line.toString().trim());
    }



    /**
     * Checks for tag correctness (no whitespace character).
     * 
     * @param tag
     *            tag
     */
    private void checkTag(String tag) {
        if (!TAG_PATTERN.matcher(tag).matches())
            throw new IllegalArgumentException("Tag (" + tag
                    + ") cannot contain whitespace");
    }

}
