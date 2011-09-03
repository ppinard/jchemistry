package net.sf.jchemistry.crystallography.io;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.UnitCell;
import net.sf.jchemistry.crystallography.test.PhaseFactory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CifSaverTest {

    private Phase phase;

    private CifSaver saver;

    private Writer out;



    @Before
    public void setUp() throws Exception {
        phase = PhaseFactory.kryptonite();
        saver = new CifSaver();
        out = new StringWriter();
    }



    @Test
    public void testSaveWriterPhase() throws IOException {
        saver.save(out, phase);

        String[] expected =
                { "data_", "_chemical_name_common 'Kryptonite'",
                        "_publ_section_references 'No reference'",
                        "_cell_length_a 1.0", "_cell_length_b 2.0",
                        "_cell_length_c 3.0", "_cell_angle_alpha 5.73",
                        "_cell_angle_beta 11.46", "_cell_angle_gamma 17.19",
                        "_space_group_IT_number 1",
                        "_symmetry_space_group_name_H-M 'P1'", "loop_",
                        "_atom_site_label", "_atom_site_type_symbol",
                        "_atom_site_fract_x", "_atom_site_fract_y",
                        "_atom_site_fract_z", "_atom_site_occupancy",
                        "Es1 Es 0.0 0.0 0.0 1.0", "loop_", "_refln_index_h",
                        "_refln_index_k", "_refln_index_l",
                        "_refln_intensity_calc", "0 0 1 1.0", "0 1 0 1.0",
                        "0 1 1 0.9", "1 0 0 1.0", "1 1 0 0.9", "1 1 1 0.8" };
        String[] actual =
                out.toString().split(System.getProperty("line.separator"));
        assertArrayEquals(expected, actual);
    }



    @Test
    public void testSaveAndLoad() throws IOException {
        saver.save(out, phase);

        Phase newPhase = new CifLoader().load(new StringReader(out.toString()));

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

        assertEquals(phase.getAtoms().size(), newPhase.getAtoms().size());

        assertEquals(phase.getReflectors().size(),
                newPhase.getReflectors().size());
    }
}
