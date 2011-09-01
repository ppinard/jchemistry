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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Parses a crystallographic information file (CIF). The parser was adapted from
 * the CIF parser in the <a href="http://jmol.org">JMol</a> library.
 * 
 * @author Philippe T. Pinard
 */
public class CifParser {

    /** Buffer to read CIF file. */
    private BufferedReader br;

    /** Parsing variable. */
    private int cch;

    /** Parsing variable. */
    private int ich;

    /** Parsing variable. */
    private int ichPeeked;

    /** Parsing variable. */
    private String line;

    /** Parsing variable. */
    private String str;

    /** Parsing variable. */
    private String strPeeked;

    /** Parsing variable. */
    private boolean wasUnQuoted;



    /**
     * Reads all CIF data from a reader and returns a map of the CIF keywords
     * with their values. Note that all values are returned as a list of string
     * even if they may only contain one value.
     * 
     * @param in
     *            reader containing the CIF data
     * @return Map of CIF keywords with their values
     * @throws IOException
     *             if an error occurs while reading the data
     */
    public Map<String, List<String>> parse(Reader in) throws IOException {
        br = new BufferedReader(in);

        line = "";
        String key;
        Map<String, List<String>> data = new Hashtable<String, List<String>>();

        while ((key = getNextToken()) != null) {
            if (key.startsWith("global_") || key.startsWith("data_")) {
                continue;
            }

            if (key.startsWith("loop_")) {
                data.putAll(getCifLoopData());
                continue;
            }

            if (key.indexOf("_") != 0) {
                throw new IOException("Key should starts with an underscore: "
                        + key);
            } else {
                String value = getNextToken();
                if (value == null) {
                    throw new IOException("End of file; data missing: " + key);
                } else {
                    value = value.replaceAll("\\n", " ").trim();
                    data.put(key, Collections.singletonList(value));
                }
            }
        }

        br.close();

        return data;
    }



    /**
     * Get the data from a loop in a CIF.
     * 
     * @return data inside the loop
     * @throws IOException
     *             if an error occurs while parsing the data
     */
    private Map<String, List<String>> getCifLoopData() throws IOException {
        String str;
        List<String> keywords = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        Map<String, List<String>> loopData =
                new Hashtable<String, List<String>>();

        // Read keywords
        while ((str = peekToken()) != null && str.charAt(0) == '_') {
            str = getTokenPeeked();
            keywords.add(str);
            loopData.put(str, new ArrayList<String>());
        }

        int keywordsCount = keywords.size();
        if (keywordsCount == 0)
            return loopData;

        // Read values
        String value;
        while ((value = getNextDataToken()) != null)
            values.add(value);

        int valuesCount = values.size();
        if (valuesCount % keywordsCount != 0)
            throw new IOException("The total number of values ("
                    + values.size()
                    + ") is not dividable by the number of keywords ("
                    + keywords.size() + ")");

        // Store values
        int nbRows = valuesCount / keywordsCount;
        List<String> datum;
        for (int i = 0; i < keywordsCount; i++) {
            datum = loopData.get(keywords.get(i));

            for (int j = 0; j < nbRows; j++) {
                datum.add(values.get(j * keywordsCount + i));
            }
        }

        return loopData;
    }



    /**
     * First checks to see if the next token is an unquoted control code, and if
     * so, returns null.
     * 
     * @return next data token or null
     */
    @CheckForNull
    private String getNextDataToken() {
        String str = peekToken();
        if (str == null)
            return null;
        if (wasUnQuoted)
            if (str.charAt(0) == '_' || str.startsWith("loop_")
                    || str.startsWith("data_") || str.startsWith("stop_")
                    || str.startsWith("global_"))
                return null;
        return getTokenPeeked();
    }



    /**
     * Returns the next token of any kind.
     * 
     * @return the next token of any kind, or null
     */
    @CheckForNull
    private String getNextToken() {
        while (!hasMoreTokens())
            if (setStringNextLine() == null)
                return null;
        return nextToken();
    }



    /**
     * Returns the token last acquired.
     * 
     * @return the token last acquired; may be null
     */
    private String getTokenPeeked() {
        ich = ichPeeked;
        return strPeeked;
    }



    /**
     * Return if there is more available tokens.
     * 
     * @return <code>true</code> if there are more tokens in the line buffer
     */
    private boolean hasMoreTokens() {
        if (str == null)
            return false;
        char ch = '#';
        while (ich < cch && ((ch = str.charAt(ich)) == ' ' || ch == '\t'))
            ++ich;
        return (ich < cch && ch != '#');
    }



    /**
     * Assume that hasMoreTokens() has been called and that ich is pointing at a
     * non-white character. Also sets boolean wasUnQuoted, because we need to
     * know if we should be checking for a control keyword. 'loop_' is different
     * from just loop_ without the quotes.
     * 
     * @return null if no more tokens, "\0" if '.' or '?', or next token
     */
    @CheckForNull
    private String nextToken() {
        if (ich == cch)
            return null;
        int ichStart = ich;
        char ch = str.charAt(ichStart);
        if (ch != '\'' && ch != '"' && ch != '\1') {
            wasUnQuoted = true;
            while (ich < cch && (ch = str.charAt(ich)) != ' ' && ch != '\t')
                ++ich;
            if (ich == ichStart + 1)
                if (str.charAt(ichStart) == '.' || str.charAt(ichStart) == '?')
                    return "\0";
            return str.substring(ichStart, ich);
        }
        wasUnQuoted = false;
        char chOpeningQuote = ch;
        boolean previousCharacterWasQuote = false;
        while (++ich < cch) {
            ch = str.charAt(ich);
            if (previousCharacterWasQuote && (ch == ' ' || ch == '\t'))
                break;
            previousCharacterWasQuote = (ch == chOpeningQuote);
        }
        if (ich == cch) {
            if (previousCharacterWasQuote) // close quote was last char of
                // string
                return str.substring(ichStart + 1, ich - 1);
            // reached the end of the string without finding closing '
            return str.substring(ichStart, ich);
        }
        ++ich; // throw away the last white character
        return str.substring(ichStart + 1, ich - 2);
    }



    /**
     * Just look at the next token. Saves it for retrieval using
     * getTokenPeeked()
     * 
     * @return next token or null if EOF
     */
    @CheckForNull
    private String peekToken() {
        while (!hasMoreTokens())
            if (setStringNextLine() == null)
                return null;
        int ich = this.ich;
        strPeeked = nextToken();
        ichPeeked = this.ich;
        this.ich = ich;
        return strPeeked;
    }



    /**
     * Read the next line from the bufferer reader.
     * 
     * @return next line
     */
    private String readLine() {
        try {
            line = br.readLine();
            return line;
        } catch (Exception e) {
            return null;
        }
    }



    /**
     * Sets a string to be parsed from the beginning.
     * 
     * @param str
     *            string to set;
     */
    private void setString(String str) {
        this.str = str;
        line = str;
        cch = (str == null ? 0 : str.length());
        ich = 0;
    }



    /**
     * Sets the string for parsing to be from the next line when the token
     * buffer is empty, and if ';' is at the beginning of that line, extends the
     * string to include that full multiline string. Uses \1 to indicate that
     * this is a special quotation.
     * 
     * @return the next line or null if EOF
     */
    @CheckForNull
    private String setStringNextLine() {
        setString(readLine());
        if (line == null || line.length() == 0 || line.charAt(0) != ';')
            return line;
        ich = 1;
        String str = '\1' + line.substring(1) + '\n';
        while (readLine() != null) {
            if (line.startsWith(";")) {
                // remove trailing <eol> only, and attach rest of next line
                str =
                        str.substring(0, str.length() - 1) + '\1'
                                + line.substring(1);
                break;
            }
            str += line + '\n';
        }
        setString(str);
        return str;
    }
}
