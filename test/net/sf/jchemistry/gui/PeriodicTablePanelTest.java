package net.sf.jchemistry.gui;

import java.util.HashSet;
import java.util.Set;

import net.sf.jchemistry.core.Element;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Panel;
import org.uispec4j.UISpecTestCase;

public class PeriodicTablePanelTest extends UISpecTestCase {

    private PeriodicTablePanel periodicTablePanel;

    private Panel uiPanel;



    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        periodicTablePanel = new PeriodicTablePanel();
        uiPanel = new Panel(periodicTablePanel);
    }



    @Test
    public void testSelectElementSingleSelection() {
        periodicTablePanel.setMultiSelection(false);

        uiPanel.getButton("Si").click();
        assertEquals(Element.Si, periodicTablePanel.getSelection());
        assertEquals(1, periodicTablePanel.getSelections().size());

        uiPanel.getButton("Be").click();
        assertEquals(Element.Be, periodicTablePanel.getSelection());
        assertEquals(1, periodicTablePanel.getSelections().size());

        uiPanel.getButton("Be").click(); // unselect
        assertEquals(0, periodicTablePanel.getSelections().size());
    }



    @Test
    public void testSelectElementMultiSelection() {
        periodicTablePanel.setMultiSelection(true);

        uiPanel.getButton("Si").click();
        uiPanel.getButton("Be").click();
        assertEquals(2, periodicTablePanel.getSelections().size());
        assertTrue(periodicTablePanel.getSelections().contains(Element.Si));
        assertTrue(periodicTablePanel.getSelections().contains(Element.Be));

        uiPanel.getButton("Si").click(); // unselect
        assertEquals(1, periodicTablePanel.getSelections().size());
        assertTrue(periodicTablePanel.getSelections().contains(Element.Be));
    }



    @Test
    public void testGetSelection() {
        assertNull(periodicTablePanel.getSelection());
    }



    @Test
    public void testGetSelections() {
        assertEquals(0, periodicTablePanel.getSelections().size());
    }



    @Test
    public void testIsMultiSelection() {
        assertFalse(periodicTablePanel.isMultiSelection());
    }



    @Test
    public void testSetMultiSelection() {
        periodicTablePanel.setMultiSelection(true);
        assertTrue(periodicTablePanel.isMultiSelection());
    }



    @Test
    public void testSetSelectionElementSingleSelection() {
        periodicTablePanel.setMultiSelection(false);
        periodicTablePanel.setSelection(Element.Si);
        assertEquals(Element.Si, periodicTablePanel.getSelection());
        assertEquals(1, periodicTablePanel.getSelections().size());
        assertTrue(periodicTablePanel.getSelections().contains(Element.Si));
    }



    @Test
    public void testSetSelectionElementMultiSelection() {
        periodicTablePanel.setMultiSelection(true);
        periodicTablePanel.setSelection(Element.Si);
        periodicTablePanel.setSelection(Element.Be);
        assertEquals(Element.Be, periodicTablePanel.getSelection());
        assertEquals(1, periodicTablePanel.getSelections().size());
        assertTrue(periodicTablePanel.getSelections().contains(Element.Be));
    }



    @Test
    public void testSetSelectionIterableOfElementSingleSelection() {
        Set<Element> selection = new HashSet<Element>();
        selection.add(Element.Si);
        selection.add(Element.Be);

        periodicTablePanel.setMultiSelection(false);
        periodicTablePanel.setSelection(selection);
        assertEquals(1, periodicTablePanel.getSelections().size());
    }



    @Test
    public void testSetSelectionIterableOfElementMultiSelection() {
        Set<Element> selection = new HashSet<Element>();
        selection.add(Element.Si);
        selection.add(Element.Be);

        periodicTablePanel.setMultiSelection(true);
        periodicTablePanel.setSelection(selection);
        assertEquals(2, periodicTablePanel.getSelections().size());
        assertTrue(periodicTablePanel.getSelections().contains(Element.Si));
        assertTrue(periodicTablePanel.getSelections().contains(Element.Be));
    }



    @Test
    public void testClearSelection() {
        periodicTablePanel.setSelection(Element.Si);
        assertEquals(1, periodicTablePanel.getSelections().size());

        periodicTablePanel.clearSelection();
        assertEquals(0, periodicTablePanel.getSelections().size());
    }

}
