package net.sf.jchemistry.util.gui;

import javax.swing.JCheckBox;
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

public class OkCancelDialogTest extends UISpecTestCase {

    private static class OkCancelDialogMock extends OkCancelDialog {

        private final JCheckBox checkBox;



        public OkCancelDialogMock(java.awt.Window owner) {
            super(owner);

            checkBox = new JCheckBox();
            checkBox.setName("checkbox");
            checkBox.setSelected(false);
        }



        @Override
        protected JComponent getMainComponent() {
            JPanel panel = new JPanel();
            panel.add(checkBox);

            return panel;
        }



        @Override
        protected boolean isCorrect() {
            return checkBox.isSelected();
        }

    }

    private OkDialog dialog;



    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        dialog = new OkCancelDialogMock(null);
    }



    @Test
    public void testShowOk() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                int returnValue = dialog.show();
                assertEquals(JOptionPane.OK_OPTION, returnValue);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                window.getCheckBox("checkbox").select();
                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testShowOkIncorrect() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                int returnValue = dialog.show();
                assertEquals(JOptionPane.CANCEL_OPTION, returnValue);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                window.getCheckBox("checkbox").select();
                window.getButton("ok").triggerClick(); // Won't close

                return window.getButton("cancel").triggerClick();
            }
        }).run();
    }



    @Test
    public void testShowCancel() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                int returnValue = dialog.show();
                assertEquals(JOptionPane.CANCEL_OPTION, returnValue);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                return window.getButton("cancel").triggerClick();
            }
        }).run();
    }

}
