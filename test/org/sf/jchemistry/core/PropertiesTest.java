package org.sf.jchemistry.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PropertiesTest {

    @Test
    public void testGetAtomicMass() {
        assertEquals(196.967, Properties.getAtomicMass(Element.Au), 1e-3);
    }



    @Test
    public void testGetAtomicRadius() {
        assertEquals(1.79, Properties.getAtomicRadius(Element.Au) * 1e10, 1e-3);
    }



    @Test
    public void testGetBoilingTemperature() {
        assertEquals(3130, Properties.getBoilingTemperature(Element.Au), 1e-3);
    }



    @Test
    public void testGetCovalentRadius() {
        assertEquals(1.34, Properties.getCovalentRadius(Element.Au) * 1e10,
                1e-3);
    }



    @Test
    public void testGetDebyeTemperature() {
        assertEquals(165, Properties.getDebyeTemperature(Element.Au), 1e-3);
    }



    @Test
    public void testGetDensity() {
        assertEquals(19.3, Properties.getDensity(Element.Au), 1e-3);
    }



    @Test
    public void testGetMeltingTemperature() {
        assertEquals(1337.58, Properties.getMeltingTemperature(Element.Au),
                1e-3);
    }



    @Test
    public void testGetMolarVolume() {
        assertEquals(10.2, Properties.getMolarVolume(Element.Au) * 1e6, 1e-3);
    }



    @Test
    public void testGetThermalConductivity() {
        assertEquals(317, Properties.getThermalConductivity(Element.Au), 1e-3);
    }



    @Test
    public void testHasAtomicMass() {
        assertTrue(Properties.hasAtomicMass(Element.Au));
        assertFalse(Properties.hasAtomicMass(Element.Cn));
    }



    @Test
    public void testHasAtomicRadius() {
        assertTrue(Properties.hasAtomicMass(Element.Au));
        assertFalse(Properties.hasAtomicMass(Element.Cn));
    }



    @Test
    public void testHasBoilingTemperature() {
        assertTrue(Properties.hasBoilingTemperature(Element.Au));
        assertFalse(Properties.hasBoilingTemperature(Element.Cn));
    }



    @Test
    public void testHasCovalentRadius() {
        assertTrue(Properties.hasCovalentRadius(Element.Au));
        assertFalse(Properties.hasCovalentRadius(Element.Cn));
    }



    @Test
    public void testHasDebyeTemperature() {
        assertTrue(Properties.hasDebyeTemperature(Element.Au));
        assertFalse(Properties.hasDebyeTemperature(Element.Cn));
    }



    @Test
    public void testHasDensity() {
        assertTrue(Properties.hasDensity(Element.Au));
        assertFalse(Properties.hasDensity(Element.Cn));
    }



    @Test
    public void testHasMeltingTemperature() {
        assertTrue(Properties.hasMeltingTemperature(Element.Au));
        assertFalse(Properties.hasMeltingTemperature(Element.Cn));
    }



    @Test
    public void testHasMolarVolume() {
        assertTrue(Properties.hasMolarVolume(Element.Au));
        assertFalse(Properties.hasMolarVolume(Element.Cn));
    }



    @Test
    public void testHasThermalConductivity() {
        assertTrue(Properties.hasThermalConductivity(Element.Au));
        assertFalse(Properties.hasThermalConductivity(Element.Cn));
    }

}
