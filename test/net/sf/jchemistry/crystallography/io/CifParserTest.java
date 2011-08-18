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
import java.io.Reader;
import java.util.List;
import java.util.Map;

import net.sf.jchemistry.util.IOUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CifParserTest {

    private Reader reader;

    private CifParser parser;



    @Before
    public void setUp() throws Exception {
        reader =
                IOUtils.getReader("net/sf/jchemistry/crystallography/testdata/forsterite.cif");
        parser = new CifParser();
    }



    @Test
    public void testParse() throws IOException {
        Map<String, List<String>> data = parser.parse(reader);

        assertEquals(34, data.size());
    }

}
