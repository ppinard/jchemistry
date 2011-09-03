package net.sf.jchemistry.crystallography.io;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CifWriterTest {

    private static class LoopMock implements Loop {

        @Override
        public int getRowCount() {
            return 2;
        }



        @Override
        public int getColumnCount() {
            return 2;
        }



        @Override
        public String getDataTag(int column) {
            return "col" + column;
        }



        @Override
        public String getDataValue(int row, int column) {
            return "R" + row + "C" + column;
        }

    }

    private Writer out;

    private CifWriter writer;



    @Before
    public void setUp() throws Exception {
        out = new StringWriter();
        writer = new CifWriter(out);
    }



    @Test
    public void testWriteDataBlock() throws IOException {
        writer.writeDataBlock("20110903");
        assertEquals("data_20110903", out.toString().trim());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testWriteDataBlockException1() throws IOException {
        writer.writeDataBlock("20110903 with spaces");
    }



    @Test
    public void testWriteDataItemStringInt() throws IOException {
        writer.writeDataItem("data_tag", 14);
        assertEquals("_data_tag 14", out.toString().trim());
    }



    @Test
    public void testWriteDataItemStringInt2() throws IOException {
        writer.writeDataItem("data_tag", 0);
        assertEquals("_data_tag 0", out.toString().trim());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testWriteDataItemStringIntException() throws IOException {
        writer.writeDataItem("data tag", 0);
    }



    @Test
    public void testWriteDataItemStringDouble() throws IOException {
        writer.writeDataItem("data_tag", 14.0);
        assertEquals("_data_tag 14.0", out.toString().trim());
    }



    @Test
    public void testWriteDataItemStringDouble2() throws IOException {
        writer.writeDataItem("data_tag", 123456789.123456);
        assertEquals("_data_tag 123456789.123456", out.toString().trim());
    }



    @Test
    public void testWriteDataItemStringDouble3() throws IOException {
        writer.writeDataItem("data_tag", 0.0);
        assertEquals("_data_tag 0.0", out.toString().trim());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testWriteDataItemStringDoubleException() throws IOException {
        writer.writeDataItem("data tag", 0.0);
    }



    @Test
    public void testWriteDataItemStringStringBooleanUnquoted()
            throws IOException {
        writer.writeDataItem("data_tag", "hexagonal", false);
        assertEquals("_data_tag hexagonal", out.toString().trim());
    }



    @Test
    public void testWriteDataItemStringStringBooleanQuotedShort()
            throws IOException {
        writer.writeDataItem("data_tag", "hexagonal", true);
        assertEquals("_data_tag 'hexagonal'", out.toString().trim());
    }



    @Test
    public void testWriteDataItemStringStringBooleanQuotedLong()
            throws IOException {
        String value =
                "Smyth, J. R., Hazen, R. M. (1973), "
                        + "The crystal structures of forsterite and hortonolite at "
                        + "several temperatures  up to 900 C  T = 25 C, "
                        + "American Mineralogist, 58, 588-593";
        writer.writeDataItem("data_tag", value, true);

        String[] expected =
                {
                        "_data_tag",
                        ";",
                        "Smyth, J. R., Hazen, R. M. (1973), The crystal structures of forsterite and",
                        "hortonolite at several temperatures  up to 900 C  T = 25 C, American",
                        "Mineralogist, 58, 588-593", ";" };
        String[] actual =
                out.toString().split(System.getProperty("line.separator"));
        assertArrayEquals(expected, actual);
    }



    @Test(expected = IllegalArgumentException.class)
    public void testWriteDataItemStringStringBooleanException()
            throws IOException {
        writer.writeDataItem("data tag", "blah", true);
    }



    @Test
    public void testWriteLoop() throws IOException {
        writer.writeLoop(new LoopMock());

        String[] expected =
                { "loop_", "_col0", "_col1", "R0C0 R0C1", "R1C0 R1C1" };
        String[] actual =
                out.toString().split(System.getProperty("line.separator"));
        assertArrayEquals(expected, actual);
    }



    @Test
    public void testWriteCommentShort() throws IOException {
        writer.writeComment("This is a comment");
        assertEquals("#This is a comment", out.toString().trim());
    }



    @Test
    public void testWriteCommentLong() throws IOException {
        writer.writeComment("All data on this site have been placed in the public domain by the contributors.");

        String[] expected =
                {
                        "#All data on this site have been placed in the public domain by the",
                        "#contributors." };
        String[] actual =
                out.toString().split(System.getProperty("line.separator"));
        assertArrayEquals(expected, actual);
    }
}
