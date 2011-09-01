package net.sf.jchemistry.crystallography.gui;

import java.text.NumberFormat;

import javax.swing.JComboBox;

import net.sf.jchemistry.crystallography.core.AtomSites;
import net.sf.jchemistry.crystallography.core.CrystalSystem;
import net.sf.jchemistry.crystallography.core.LaueGroup;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflectors;
import net.sf.jchemistry.crystallography.core.SpaceGroup;
import net.sf.jchemistry.crystallography.core.UnitCell;
import net.sf.jchemistry.crystallography.test.PhaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Trigger;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import static net.sf.jchemistry.test.UISpecUtils.getComboBox;
import static net.sf.jchemistry.test.UISpecUtils.getComboBoxSelection;
import static net.sf.jchemistry.test.UISpecUtils.getTextBox;

public class PhaseDialogFactoryTest extends UISpecTestCase {

    private Phase phase;



    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        phase = PhaseFactory.kryptonite();
    }



    @Test
    public void testShowEditDialog() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                PhaseDialogFactory.showEditDialog(null, phase);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                assertEquals(phase.getName(),
                        getTextBox(window, "name").getText());
                assertEquals(phase.getCitation(),
                        getTextBox(window, "citation").getText());

                // Crystal system
                CrystalSystem cs = phase.getSpaceGroup().getCrystalSystem();
                assertEquals(cs, getComboBoxSelection(window, "crystal system"));

                // Laue group
                LaueGroup lg = phase.getSpaceGroup().getLaueGroup();
                assertEquals(lg, getComboBoxSelection(window, "Laue group"));

                // Space group
                SpaceGroup sg = phase.getSpaceGroup();
                assertEquals(sg, getComboBoxSelection(window, "space group"));

                // Unit cell
                UnitCellPanelTest.testUnitCellPanel(window, phase.getUnitCell());

                // Atoms
                AtomSites atoms = phase.getAtoms();
                assertEquals(atoms.size(),
                        window.getTable("atoms").getRowCount());

                // Reflectors
                Reflectors refls = phase.getReflectors();
                assertEquals(refls.size(),
                        window.getTable("reflectors").getRowCount());

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testShowInfoDialogSinglePhase() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                PhaseDialogFactory.showInfoDialog(null, phase);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                PhaseInfoPanelTest.testPhaseInfoPanel(window, phase);

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testShowInfoDialogMultiplePhase() {
        final Phase phase2 = PhaseFactory.silicon();

        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                PhaseDialogFactory.showInfoDialog(null, phase, phase2);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                window.getComboBox("phase").select("Kryptonite");
                PhaseInfoPanelTest.testPhaseInfoPanel(window, phase);

                window.getComboBox("phase").select("Silicon");
                PhaseInfoPanelTest.testPhaseInfoPanel(window, phase2);

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }



    @Test
    public void testShowNewDialog() {
        WindowInterceptor.init(new Trigger() {
            @Override
            public void run() throws Exception {
                Phase newPhase = PhaseDialogFactory.showNewDialog(null);

                assertEquals(phase.getName(), newPhase.getName());
                assertEquals(phase.getCitation(), newPhase.getCitation());
                assertEquals(phase.getSpaceGroup(), newPhase.getSpaceGroup());

                UnitCell unitCell = phase.getUnitCell();
                UnitCell newUnitCell = newPhase.getUnitCell();
                assertEquals(unitCell.getA(), newUnitCell.getA(), 1e-3);
                assertEquals(unitCell.getB(), newUnitCell.getB(), 1e-3);
                assertEquals(unitCell.getC(), newUnitCell.getC(), 1e-3);
                assertEquals(unitCell.getAlpha(), newUnitCell.getAlpha(), 1e-3);
                assertEquals(unitCell.getBeta(), newUnitCell.getBeta(), 1e-3);
                assertEquals(unitCell.getGamma(), newUnitCell.getGamma(), 1e-3);
            }
        }).process(new WindowHandler("dialog") {
            @Override
            public Trigger process(Window window) {
                getTextBox(window, "name").setText(phase.getName());
                getTextBox(window, "citation").setText(phase.getCitation());

                // Crystal system
                JComboBox csCB =
                        getComboBox(window, "crystal system").getAwtComponent();
                csCB.setSelectedItem(phase.getSpaceGroup().getCrystalSystem());

                // Laue group
                JComboBox lgCB =
                        getComboBox(window, "Laue group").getAwtComponent();
                lgCB.setSelectedItem(phase.getSpaceGroup().getLaueGroup());

                // Space group
                JComboBox sgCB =
                        getComboBox(window, "space group").getAwtComponent();
                sgCB.setSelectedItem(phase.getSpaceGroup());

                // Unit cell
                UnitCell unitCell = phase.getUnitCell();
                NumberFormat format = NumberFormat.getNumberInstance();

                getTextBox(window, "a").setText(format.format(unitCell.getA()));
                getTextBox(window, "b").setText(format.format(unitCell.getB()));
                getTextBox(window, "b").setText(format.format(unitCell.getC()));
                getTextBox(window, "alpha").setText(
                        format.format(unitCell.getAlpha()));
                getTextBox(window, "beta").setText(
                        format.format(unitCell.getBeta()));
                getTextBox(window, "gamma").setText(
                        format.format(unitCell.getGamma()));

                return window.getButton("ok").triggerClick();
            }
        }).run();
    }

}
