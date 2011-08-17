package net.sf.jchemistry.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementTest {

    @Test
    public void testFromZ() {
        for (int i = 1; i <= 112; i++)
            assertEquals(i, Element.fromZ(i).z());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testFromZException() {
        Element.fromZ(113);
    }



    @Test
    public void testFromSymbol() {
        for (Element element : Element.values())
            assertEquals(element.symbol(),
                    Element.fromSymbol(element.symbol()).symbol());
    }



    @Test(expected = IllegalArgumentException.class)
    public void testFromSymbolException() {
        Element.fromSymbol("Aa");
    }
}
