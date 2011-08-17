package net.sf.jchemistry.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElementPropertiesTest {

    @Test
    public void testGetAtomicMass() {
        assertEquals(196.967, ElementProperties.getAtomicMass(Element.Au), 1e-3);
    }



    @Test
    public void testGetAtomicRadius() {
        assertEquals(1.79, ElementProperties.getAtomicRadius(Element.Au), 1e-3);
    }



    @Test
    public void testGetBoilingTemperature() {
        assertEquals(3130, ElementProperties.getBoilingTemperature(Element.Au),
                1e-3);
    }



    @Test
    public void testGetCovalentRadius() {
        assertEquals(1.34, ElementProperties.getCovalentRadius(Element.Au),
                1e-3);
    }



    @Test
    public void testGetDebyeTemperature() {
        assertEquals(165, ElementProperties.getDebyeTemperature(Element.Au),
                1e-3);
    }



    @Test
    public void testGetDensity() {
        assertEquals(19.3, ElementProperties.getDensity(Element.Au), 1e-3);
    }



    @Test
    public void testGetMeltingTemperature() {
        assertEquals(1337.58,
                ElementProperties.getMeltingTemperature(Element.Au), 1e-3);
    }



    @Test
    public void testGetMolarVolume() {
        assertEquals(10.2, ElementProperties.getMolarVolume(Element.Au) * 1e6,
                1e-3);
    }



    @Test
    public void testGetThermalConductivity() {
        assertEquals(317, ElementProperties.getThermalConductivity(Element.Au),
                1e-3);
    }



    @Test
    public void testHasAtomicMass() {
        assertTrue(ElementProperties.hasAtomicMass(Element.Au));
        assertFalse(ElementProperties.hasAtomicMass(Element.Cn));
    }



    @Test
    public void testHasAtomicRadius() {
        assertTrue(ElementProperties.hasAtomicMass(Element.Au));
        assertFalse(ElementProperties.hasAtomicMass(Element.Cn));
    }



    @Test
    public void testHasBoilingTemperature() {
        assertTrue(ElementProperties.hasBoilingTemperature(Element.Au));
        assertFalse(ElementProperties.hasBoilingTemperature(Element.Cn));
    }



    @Test
    public void testHasCovalentRadius() {
        assertTrue(ElementProperties.hasCovalentRadius(Element.Au));
        assertFalse(ElementProperties.hasCovalentRadius(Element.Cn));
    }



    @Test
    public void testHasDebyeTemperature() {
        assertTrue(ElementProperties.hasDebyeTemperature(Element.Au));
        assertFalse(ElementProperties.hasDebyeTemperature(Element.Cn));
    }



    @Test
    public void testHasDensity() {
        assertTrue(ElementProperties.hasDensity(Element.Au));
        assertFalse(ElementProperties.hasDensity(Element.Cn));
    }



    @Test
    public void testHasMeltingTemperature() {
        assertTrue(ElementProperties.hasMeltingTemperature(Element.Au));
        assertFalse(ElementProperties.hasMeltingTemperature(Element.Cn));
    }



    @Test
    public void testHasMolarVolume() {
        assertTrue(ElementProperties.hasMolarVolume(Element.Au));
        assertFalse(ElementProperties.hasMolarVolume(Element.Cn));
    }



    @Test
    public void testHasThermalConductivity() {
        assertTrue(ElementProperties.hasThermalConductivity(Element.Au));
        assertFalse(ElementProperties.hasThermalConductivity(Element.Cn));
    }

}
