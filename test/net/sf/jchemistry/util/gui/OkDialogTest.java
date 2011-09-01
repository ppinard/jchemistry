package net.sf.jchemistry.util.gui;

import java.awt.Dialog;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

public class OkDialogTest extends UISpecTestCase {

    private static class OkDialogMock extends OkDialog {

        public OkDialogMock(java.awt.Window owner) {
            super(owner);
        }



        @Override
        protected JComponent getMainComponent() {
            return new JPanel();
        }

    }

    private OkDialog dialog;



    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        dialog = new OkDialogMock(null);
        dialog.setMinimumSize(new Dimension(5, 6));
        dialog.setMaximumSize(new Dimension(25, 26));
        dialog.setResizable(true);
        dialog.setTitle("Test");
    }



    @Test
    public void testShow() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                int returnValue = dialog.show();
                assertEquals(JOptionPane.OK_OPTION, returnValue);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                Dialog component = (Dialog) window.getAwtComponent();

                Dimension dim = component.getMinimumSize();
                assertEquals(5, dim.width);
                assertEquals(6, dim.height);

                dim = component.getMaximumSize();
                assertEquals(25, dim.width);
                assertEquals(26, dim.height);

                assertTrue(component.isResizable());

                assertEquals("Test", component.getTitle());

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testGetMaximumSize() {
        Dimension dim = dialog.getMaximumSize();
        assertEquals(25, dim.width);
        assertEquals(26, dim.height);
    }



    @Test
    public void testGetMinimumSize() {
        Dimension dim = dialog.getMinimumSize();
        assertEquals(5, dim.width);
        assertEquals(6, dim.height);
    }



    @Test
    public void testGetTitle() {
        assertEquals("Test", dialog.getTitle());
    }



    @Test
    public void testIsResizable() {
        assertTrue(dialog.isResizable());
    }



    @Test
    public void testSetMaximumSize() {
        dialog.setMaximumSize(new Dimension(35, 36));

        Dimension dim = dialog.getMaximumSize();
        assertEquals(35, dim.width);
        assertEquals(36, dim.height);
    }



    @Test
    public void testSetMinimumSize() {
        dialog.setMinimumSize(new Dimension(15, 16));

        Dimension dim = dialog.getMinimumSize();
        assertEquals(15, dim.width);
        assertEquals(16, dim.height);
    }



    @Test
    public void testSetResizable() {
        dialog.setResizable(false);
        assertFalse(dialog.isResizable());
    }



    @Test
    public void testSetTitle() {
        dialog.setTitle("Test2");
        assertEquals("Test2", dialog.getTitle());
    }
}
