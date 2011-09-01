package net.sf.jchemistry.gui;

import java.util.HashSet;
import java.util.Set;

import net.sf.jchemistry.core.Element;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Panel;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

public class PeriodicTableDialogFactoryTest extends UISpecTestCase {

    private Set<Element> selection;



    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        selection = new HashSet<Element>();
        selection.add(Element.Si);
        selection.add(Element.Be);
    }



    @Test
    public void testShowJFrameBooleanSetOfElementMultiSelection() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                Set<Element> returnSelection =
                        PeriodicTableDialogFactory.show(null, true, selection);

                assertEquals(1, returnSelection.size());
                assertTrue(returnSelection.contains(Element.Be));
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                Panel panel = window.getPanel("periodic table");

                panel.getButton("Si").click(); // unselect

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testShowJFrameBooleanSetOfElementSingleSelection() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                Set<Element> returnSelection =
                        PeriodicTableDialogFactory.show(null, false);

                assertEquals(1, returnSelection.size());
                assertTrue(returnSelection.contains(Element.Be));
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                Panel panel = window.getPanel("periodic table");

                panel.getButton("Be").click(); // select

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testShowJFrameBooleanSetOfElementCancel() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                Set<Element> returnSelection =
                        PeriodicTableDialogFactory.show(null, true, selection);

                assertEquals(2, returnSelection.size());
                assertTrue(returnSelection.contains(Element.Si));
                assertTrue(returnSelection.contains(Element.Be));
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                Panel panel = window.getPanel("periodic table");

                panel.getButton("Al").click(); // select
                panel.getButton("Si").click(); // unselect

                return window.getButton("cancel").triggerClick();
            }
        }).run();
    }

}
