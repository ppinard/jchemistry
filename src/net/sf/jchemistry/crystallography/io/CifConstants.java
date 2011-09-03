package net.sf.jchemistry.crystallography.io;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Constants for CIF parsing and writing.
 * 
 * @author ppinard
 */
public class CifConstants {

    /** Number format. */
    public static final NumberFormat FORMAT = new DecimalFormat(
            "##0.0###########");

    /** Tag for <code>_atom_site_fract_x</code>. */
    public static final String ATOM_SITE_FRACT_X = "atom_site_fract_x";

    /** Tag for <code>_atom_site_fract_y</code>. */
    public static final String ATOM_SITE_FRACT_Y = "atom_site_fract_y";

    /** Tag for <code>_atom_site_fract_z</code>. */
    public static final String ATOM_SITE_FRACT_Z = "atom_site_fract_z";

    /** Tag for <code>_atom_site_label</code>. */
    public static final String ATOM_SITE_LABEL = "atom_site_label";

    /** Tag for <code>_atom_site_occupancy</code>. */
    public static final String ATOM_SITE_OCCUPANCY = "atom_site_occupancy";

    /** Tag for <code>_atom_site_type_symbol</code>. */
    public static final String ATOM_SITE_TYPE_SYMBOL = "atom_site_type_symbol";

    /** Tag for <code>_cell_angle_alpha</code>. */
    public static final String CELL_ANGLE_ALPHA = "cell_angle_alpha";

    /** Tag for <code>_cell_angle_beta</code>. */
    public static final String CELL_ANGLE_BETA = "cell_angle_beta";

    /** Tag for <code>_cell_angle_gamma</code>. */
    public static final String CELL_ANGLE_GAMMA = "cell_angle_gamma";

    /** Tag for <code>_cell_length_a</code>. */
    public static final String CELL_LENGTH_A = "cell_length_a";

    /** Tag for <code>_cell_length_b</code>. */
    public static final String CELL_LENGTH_B = "cell_length_b";

    /** Tag for <code>_cell_length_c</code>. */
    public static final String CELL_LENGTH_C = "cell_length_c";

    /** Tag for <code>_chemical_name_common</code>. */
    public static final String CHEMICAL_NAME_COMMON = "chemical_name_common";

    /** Tag for <code>_chemical_name_mineral</code>. */
    public static final String CHEMICAL_NAME_MINERAL = "chemical_name_mineral";

    /** Tag for <code>_chemical_name_structure_typ</code>. */
    public static final String CHEMICAL_NAME_STRUCTURE_TYP =
            "chemical_name_structure_typ";

    /** Tag for <code>_chemical_name_systematic</code>. */
    public static final String CHEMICAL_NAME_SYSTEMATIC =
            "chemical_name_systematic";

    /** Tag for <code>_citation_author_name</code>. */
    public static final String CITATION_AUTHOR_NAME = "citation_author_name";

    /** Tag for <code>_citation_book_publisher</code>. */
    public static final String CITATION_BOOK_PUBLISHER =
            "citation_book_publisher";

    /** Tag for <code>_citation_book_publisher_city</code>. */
    public static final String CITATION_BOOK_PUBLISHER_CITY =
            "citation_book_publisher_city";

    /** Tag for <code>_citation_book_title</code>. */
    public static final String CITATION_BOOK_TTTLE = "citation_book_title";

    /** Tag for <code>_citation_editor_name</code>. */
    public static final String CITATION_EDITOR_NAME = "citation_editor_name";

    /** Tag for <code>_citation_id</code>. */
    public static final String CITATION_ID = "citation_id";

    /** Tag for <code>_citation_journal_abbrev</code>. */
    public static final String CITATION_JOURNAL_ABBREV =
            "citation_journal_abbrev";

    /** Tag for <code>_citation_journal_full</code>. */
    public static final String CITATION_JOURNAL_FULL = "citation_journal_full";

    /** Tag for <code>_citation_journal_volume</code>. */
    public static final String CITATION_JOURNAL_VOLUME =
            "citation_journal_volume";

    /** Tag for <code>_citation_page_first</code>. */
    public static final String CITATION_PAGE_FIRST = "citation_page_first";

    /** Tag for <code>_citation_page_last</code>. */
    public static final String CITATION_PAGE_LAST = "citation_page_last";

    /** Tag for <code>_citation_title</code>. */
    public static final String CITATION_TITLE = "citation_title";

    /** Tag for <code>_citation_year</code>. */
    public static final String CITATION_YEAR = "citation_year";

    /** Tag for <code>_journal_name_full</code>. */
    public static final String JOURNAL_NAME_FULL = "journal_name_full";

    /** Tag for <code>_journal_page_first</code>. */
    public static final String JOURNAL_PAGE_FIRST = "journal_page_first";

    /** Tag for <code>_journal_page_last</code>. */
    public static final String JOURNAL_PAGE_LAST = "journal_page_last";

    /** Tag for <code>_journal_volume</code>. */
    public static final String JOURNAL_VOLUME = "journal_volume";

    /** Tag for <code>_journal_year</code>. */
    public static final String JOURNAL_YEAR = "journal_year";

    /** Tag for <code>_publ_author_name</code>. */
    public static final String PUBL_AUTHOR_NAME = "publ_author_name";

    /** Tag for <code>_publ_section_references</code>. */
    public static final String PUBL_SECTION_REFERENCES =
            "publ_section_references";

    /** Tag for <code>_publ_section_title</code>. */
    public static final String PUBL_SECTION_TITLE = "publ_section_title";

    /** Tag for <code>_refln_F_calc</code>. */
    public static final String REFLN_F_CALC = "refln_F_calc";

    /** Tag for <code>_refln_F_meas</code>. */
    public static final String REFLN_F_MEAS = "refln_F_meas";

    /** Tag for <code>_refln_F_squared_calc</code>. */
    public static final String REFLN_F_SQUARED_CALC = "refln_F_squared_calc";

    /** Tag for <code>_refln_F_squared_meas</code>. */
    public static final String REFLN_F_SQUARED_MEAS = "refln_F_squared_meas";

    /** Tag for <code>_refln_index_h</code>. */
    public static final String REFLN_INDEX_H = "refln_index_h";

    /** Tag for <code>_refln_index_k</code>. */
    public static final String REFLN_INDEX_K = "refln_index_k";

    /** Tag for <code>_refln_index_l</code>. */
    public static final String REFLN_INDEX_L = "refln_index_l";

    /** Tag for <code>_refln_intensity_calc</code>. */
    public static final String REFLN_INTENSITY_CALC = "refln_intensity_calc";

    /** Tag for <code>_refln_intensity_meas</code>. */
    public static final String REFLN_INTENSITY_MEAS = "refln_intensity_meas";

    /** Tag for <code>_space_group_IT_number</code>. */
    public static final String SPACE_GROUP_IT_NUMBER = "space_group_IT_number";

    /** Tag for <code>_space_group_symop_operation_xyz</code>. */
    public static final String SPACE_GROUP_SYMOP_OPERATION_XYZ =
            "space_group_symop_operation_xyz";

    /** Tag for <code>_symmetry_equiv_pos_as_xyz</code>. */
    public static final String SYMMETRY_EQUIV_POS_AS_XYZ =
            "symmetry_equiv_pos_as_xyz";

    /** Tag for <code>_symmetry_Int_Tables_number</code>. */
    public static final String SYMMETRY_INT_TABLES_NUMBER =
            "symmetry_Int_Tables_number";

    /** Tag for <code>_symmetry_space_group_name_H</code>. */
    public static final String SYMMETRY_SPACE_GROUP_NAME_HM =
            "symmetry_space_group_name_H-M";
}
