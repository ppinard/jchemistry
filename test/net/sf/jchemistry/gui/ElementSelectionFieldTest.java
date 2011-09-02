package net.sf.jchemistry.gui;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import net.sf.jchemistry.core.Element;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Panel;
import org.uispec4j.TextBox;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import static net.sf.jchemistry.test.UISpecUtils.getTextBox;

public class ElementSelectionFieldTest extends UISpecTestCase {

    private ElementSelectionField field;

    private Panel uiPanel;



    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        JPanel panel = new JPanel();
        field = new ElementSelectionField();
        panel.add(field);

        uiPanel = new Panel(panel);
    }



    @Test
    public void testBrowseSingleSelection() {
        field.setMultiSelection(false);

        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                uiPanel.getButton("browse").click();

                assertEquals(Element.Si, field.getSelection());
                assertEquals(1, field.getSelections().size());
                assertTrue(field.getSelections().contains(Element.Si));

                assertEquals("Si", getTextBox(uiPanel, "selection").getText());
            }
        }).process(new WindowHandler("periodic table") {
            @Override
            public Trigger process(Window window) {
                window.getButton("Si").click(); // select

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testBrowseMultiSelection() {
        field.setMultiSelection(true);

        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                uiPanel.getButton("browse").click();

                assertEquals(2, field.getSelections().size());
                assertTrue(field.getSelections().contains(Element.Si));
                assertTrue(field.getSelections().contains(Element.Be));

                assertEquals("Be, Si",
                        getTextBox(uiPanel, "selection").getText());
            }
        }).process(new WindowHandler("periodic table") {
            @Override
            public Trigger process(Window window) {
                window.getButton("Si").click(); // select
                window.getButton("Be").click(); // select

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testTextInputSingleSelection() {
        field.setMultiSelection(false);

        TextBox textBox = getTextBox(uiPanel, "selection");
        textBox.setText("Si");
        textBox.focusLost();

        assertEquals(Element.Si, field.getSelection());
        assertEquals(1, field.getSelections().size());
        assertTrue(field.getSelections().contains(Element.Si));

        assertEquals("Si", textBox.getText());
    }



    @Test
    public void testTextInputSingleSelection2() {
        field.setMultiSelection(false);

        TextBox textBox = getTextBox(uiPanel, "selection");
        textBox.setText("Si, Be");
        textBox.focusLost();

        assertEquals(1, field.getSelections().size());
    }



    @Test
    public void testTextInputMultiSelection() {
        field.setMultiSelection(true);

        TextBox textBox = getTextBox(uiPanel, "selection");
        textBox.setText("Si, Be");
        textBox.focusLost();

        assertEquals(2, field.getSelections().size());
        assertTrue(field.getSelections().contains(Element.Si));
        assertTrue(field.getSelections().contains(Element.Be));

        assertEquals("Be, Si", textBox.getText());
    }



    @Test
    public void testTextInputMultiSelectionIncorrectSymbol() {
        field.setMultiSelection(true);

        final TextBox textBox = getTextBox(uiPanel, "selection");
        textBox.setText("Si, Zz");

        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                textBox.focusLost();
            }
        }).process(new WindowHandler("error dialog") {
            @Override
            public Trigger process(Window window) {
                return window.getButton("ok").triggerClick();
            }
        }).run();

        assertEquals(1, field.getSelections().size());
        assertTrue(field.getSelections().contains(Element.Si));

        assertEquals("Si", textBox.getText());
    }



    @Test
    public void testGetSelection() {
        assertNull(field.getSelection());
    }



    @Test
    public void testGetSelections() {
        assertEquals(0, field.getSelections().size());
    }



    @Test
    public void testIsMultiSelection() {
        assertFalse(field.isMultiSelection());
    }



    @Test
    public void testSetMultiSelection() {
        field.setMultiSelection(true);
        assertTrue(field.isMultiSelection());
    }



    @Test
    public void testSetSelectionElementSingleSelection() {
        field.setMultiSelection(false);

        field.setSelection(Element.Si);
        assertEquals(Element.Si, field.getSelection());
        assertEquals(1, field.getSelections().size());
        assertTrue(field.getSelections().contains(Element.Si));
    }



    @Test
    public void testSetSelectionElementMultiSelection() {
        field.setMultiSelection(true);

        field.setSelection(Element.Si);
        field.setSelection(Element.Be);
        assertEquals(Element.Be, field.getSelection());
        assertEquals(1, field.getSelections().size());
        assertTrue(field.getSelections().contains(Element.Be));
    }



    @Test
    public void testSetSelectionIterableOfElementSingleSelection() {
        Set<Element> selection = new HashSet<Element>();
        selection.add(Element.Si);
        selection.add(Element.Be);

        field.setMultiSelection(false);
        field.setSelection(selection);
        assertEquals(1, field.getSelections().size());
    }



    @Test
    public void testSetSelectionIterableOfElementMultiSelection() {
        Set<Element> selection = new HashSet<Element>();
        selection.add(Element.Si);
        selection.add(Element.Be);

        field.setMultiSelection(true);
        field.setSelection(selection);
        assertEquals(2, field.getSelections().size());
        assertTrue(field.getSelections().contains(Element.Si));
        assertTrue(field.getSelections().contains(Element.Be));
    }

}
