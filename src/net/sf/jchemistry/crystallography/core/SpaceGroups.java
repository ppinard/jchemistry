/*
 * jChemistry
 * Copyright (C) 2011 Philippe T. Pinard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jchemistry.crystallography.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.sf.jchemistry.crystallography.core.CrystalSystem.*;
import static net.sf.jchemistry.crystallography.core.LaueGroup.*;

/**
 * Utilities to get and list space groups.
 * 
 * @author Philippe T. Pinard
 */
public final class SpaceGroups implements SpaceGroups1, SpaceGroups2 {

    /**
     * Return the space group with the specified index/number.
     * 
     * @param index
     *            index/number of the space group
     * @return space group
     * @throws IllegalArgumentException
     *             if the index is unknown
     */
    public static SpaceGroup fromIndex(int index) {
        SpaceGroup result = INDEXES.get(index);

        if (result == null)
            throw new IllegalArgumentException("Unknown space group index ("
                    + index + ").");

        return result;
    }



    /**
     * Return the space group with the specified symbol/name.
     * 
     * @param symbol
     *            symbol/name of the space group
     * @return space group
     * @throws IllegalArgumentException
     *             if the symbol is unknown
     */
    public static SpaceGroup fromSymbol(String symbol) {
        SpaceGroup result = SYMBOLS.get(symbol);

        if (result == null)
            throw new IllegalArgumentException("Unknown space group symbol ("
                    + symbol + ").");

        return result;
    }



    /**
     * Returns all the space groups with the specified crystal system.
     * 
     * @param crystalSystem
     *            crystal system
     * @return space groups in the crystal system
     */
    public static Set<SpaceGroup> list(CrystalSystem crystalSystem) {
        return CRYSTAL_SYSTEMS.get(crystalSystem);
    }



    /**
     * Returns all the space groups with the specified Laue group.
     * 
     * @param laueGroup
     *            Laue Group
     * @return space groups in the Laue group
     */
    public static Set<SpaceGroup> list(LaueGroup laueGroup) {
        return LAUE_GROUPS.get(laueGroup);
    }

    /** Lookup table with the space group's crystal system. */
    private static final Map<CrystalSystem, Set<SpaceGroup>> CRYSTAL_SYSTEMS =
            new HashMap<CrystalSystem, Set<SpaceGroup>>();

    /** Lookup table with the space group's index. */
    private static final Map<Integer, SpaceGroup> INDEXES =
            new HashMap<Integer, SpaceGroup>();

    /** Lookup table with the space group's Laue group. */
    private static final Map<LaueGroup, Set<SpaceGroup>> LAUE_GROUPS =
            new HashMap<LaueGroup, Set<SpaceGroup>>();

    /** Lookup table with the space group's symbol. */
    private static final Map<String, SpaceGroup> SYMBOLS =
            new HashMap<String, SpaceGroup>();

    static {
        initIndexes();
        initSymbols();
        initLaueGroups();
        initCrystalSystems();
    }



    /**
     * Don't let anyone instantiate this class.
     */
    private SpaceGroups() {
    }



    /**
     * Initializes the crystal systems lookup table.
     */
    private static void initCrystalSystems() {
        CRYSTAL_SYSTEMS.put(TRICLINIC, new HashSet<SpaceGroup>());
        CRYSTAL_SYSTEMS.get(TRICLINIC).addAll(LAUE_GROUPS.get(LG1));

        CRYSTAL_SYSTEMS.put(MONOCLINIC, new HashSet<SpaceGroup>());
        CRYSTAL_SYSTEMS.get(MONOCLINIC).addAll(LAUE_GROUPS.get(LG2m));

        CRYSTAL_SYSTEMS.put(ORTHORHOMBIC, new HashSet<SpaceGroup>());
        CRYSTAL_SYSTEMS.get(ORTHORHOMBIC).addAll(LAUE_GROUPS.get(LGmmm));

        CRYSTAL_SYSTEMS.put(TETRAGONAL, new HashSet<SpaceGroup>());
        CRYSTAL_SYSTEMS.get(TETRAGONAL).addAll(LAUE_GROUPS.get(LG4m));
        CRYSTAL_SYSTEMS.get(TETRAGONAL).addAll(LAUE_GROUPS.get(LG4mmm));

        CRYSTAL_SYSTEMS.put(TRIGONAL, new HashSet<SpaceGroup>());
        CRYSTAL_SYSTEMS.get(TRIGONAL).addAll(LAUE_GROUPS.get(LG3));
        CRYSTAL_SYSTEMS.get(TRIGONAL).addAll(LAUE_GROUPS.get(LG3m));

        CRYSTAL_SYSTEMS.put(HEXAGONAL, new HashSet<SpaceGroup>());
        CRYSTAL_SYSTEMS.get(HEXAGONAL).addAll(LAUE_GROUPS.get(LG6m));
        CRYSTAL_SYSTEMS.get(HEXAGONAL).addAll(LAUE_GROUPS.get(LG6mmm));

        CRYSTAL_SYSTEMS.put(CUBIC, new HashSet<SpaceGroup>());
        CRYSTAL_SYSTEMS.get(CUBIC).addAll(LAUE_GROUPS.get(LGm3));
        CRYSTAL_SYSTEMS.get(CUBIC).addAll(LAUE_GROUPS.get(LGm3m));
    }



    /**
     * Initializes the INDEXES lookup table.
     */
    private static void initIndexes() {
        INDEXES.put(1, SG1);
        INDEXES.put(2, SG2);
        INDEXES.put(3, SG3);
        INDEXES.put(4, SG4);
        INDEXES.put(5, SG5);
        INDEXES.put(6, SG6);
        INDEXES.put(7, SG7);
        INDEXES.put(8, SG8);
        INDEXES.put(9, SG9);
        INDEXES.put(10, SG10);
        INDEXES.put(11, SG11);
        INDEXES.put(12, SG12);
        INDEXES.put(13, SG13);
        INDEXES.put(14, SG14);
        INDEXES.put(15, SG15);
        INDEXES.put(16, SG16);
        INDEXES.put(17, SG17);
        INDEXES.put(18, SG18);
        INDEXES.put(19, SG19);
        INDEXES.put(20, SG20);
        INDEXES.put(21, SG21);
        INDEXES.put(22, SG22);
        INDEXES.put(23, SG23);
        INDEXES.put(24, SG24);
        INDEXES.put(25, SG25);
        INDEXES.put(26, SG26);
        INDEXES.put(27, SG27);
        INDEXES.put(28, SG28);
        INDEXES.put(29, SG29);
        INDEXES.put(30, SG30);
        INDEXES.put(31, SG31);
        INDEXES.put(32, SG32);
        INDEXES.put(33, SG33);
        INDEXES.put(34, SG34);
        INDEXES.put(35, SG35);
        INDEXES.put(36, SG36);
        INDEXES.put(37, SG37);
        INDEXES.put(38, SG38);
        INDEXES.put(39, SG39);
        INDEXES.put(40, SG40);
        INDEXES.put(41, SG41);
        INDEXES.put(42, SG42);
        INDEXES.put(43, SG43);
        INDEXES.put(44, SG44);
        INDEXES.put(45, SG45);
        INDEXES.put(46, SG46);
        INDEXES.put(47, SG47);
        INDEXES.put(48, SG48);
        INDEXES.put(49, SG49);
        INDEXES.put(50, SG50);
        INDEXES.put(51, SG51);
        INDEXES.put(52, SG52);
        INDEXES.put(53, SG53);
        INDEXES.put(54, SG54);
        INDEXES.put(55, SG55);
        INDEXES.put(56, SG56);
        INDEXES.put(57, SG57);
        INDEXES.put(58, SG58);
        INDEXES.put(59, SG59);
        INDEXES.put(60, SG60);
        INDEXES.put(61, SG61);
        INDEXES.put(62, SG62);
        INDEXES.put(63, SG63);
        INDEXES.put(64, SG64);
        INDEXES.put(65, SG65);
        INDEXES.put(66, SG66);
        INDEXES.put(67, SG67);
        INDEXES.put(68, SG68);
        INDEXES.put(69, SG69);
        INDEXES.put(70, SG70);
        INDEXES.put(71, SG71);
        INDEXES.put(72, SG72);
        INDEXES.put(73, SG73);
        INDEXES.put(74, SG74);
        INDEXES.put(75, SG75);
        INDEXES.put(76, SG76);
        INDEXES.put(77, SG77);
        INDEXES.put(78, SG78);
        INDEXES.put(79, SG79);
        INDEXES.put(80, SG80);
        INDEXES.put(81, SG81);
        INDEXES.put(82, SG82);
        INDEXES.put(83, SG83);
        INDEXES.put(84, SG84);
        INDEXES.put(85, SG85);
        INDEXES.put(86, SG86);
        INDEXES.put(87, SG87);
        INDEXES.put(88, SG88);
        INDEXES.put(89, SG89);
        INDEXES.put(90, SG90);
        INDEXES.put(91, SG91);
        INDEXES.put(92, SG92);
        INDEXES.put(93, SG93);
        INDEXES.put(94, SG94);
        INDEXES.put(95, SG95);
        INDEXES.put(96, SG96);
        INDEXES.put(97, SG97);
        INDEXES.put(98, SG98);
        INDEXES.put(99, SG99);
        INDEXES.put(100, SG100);
        INDEXES.put(101, SG101);
        INDEXES.put(102, SG102);
        INDEXES.put(103, SG103);
        INDEXES.put(104, SG104);
        INDEXES.put(105, SG105);
        INDEXES.put(106, SG106);
        INDEXES.put(107, SG107);
        INDEXES.put(108, SG108);
        INDEXES.put(109, SG109);
        INDEXES.put(110, SG110);
        INDEXES.put(111, SG111);
        INDEXES.put(112, SG112);
        INDEXES.put(113, SG113);
        INDEXES.put(114, SG114);
        INDEXES.put(115, SG115);
        INDEXES.put(116, SG116);
        INDEXES.put(117, SG117);
        INDEXES.put(118, SG118);
        INDEXES.put(119, SG119);
        INDEXES.put(120, SG120);
        INDEXES.put(121, SG121);
        INDEXES.put(122, SG122);
        INDEXES.put(123, SG123);
        INDEXES.put(124, SG124);
        INDEXES.put(125, SG125);
        INDEXES.put(126, SG126);
        INDEXES.put(127, SG127);
        INDEXES.put(128, SG128);
        INDEXES.put(129, SG129);
        INDEXES.put(130, SG130);
        INDEXES.put(131, SG131);
        INDEXES.put(132, SG132);
        INDEXES.put(133, SG133);
        INDEXES.put(134, SG134);
        INDEXES.put(135, SG135);
        INDEXES.put(136, SG136);
        INDEXES.put(137, SG137);
        INDEXES.put(138, SG138);
        INDEXES.put(139, SG139);
        INDEXES.put(140, SG140);
        INDEXES.put(141, SG141);
        INDEXES.put(142, SG142);
        INDEXES.put(143, SG143);
        INDEXES.put(144, SG144);
        INDEXES.put(145, SG145);
        INDEXES.put(146, SG146);
        INDEXES.put(147, SG147);
        INDEXES.put(148, SG148);
        INDEXES.put(149, SG149);
        INDEXES.put(150, SG150);
        INDEXES.put(151, SG151);
        INDEXES.put(152, SG152);
        INDEXES.put(153, SG153);
        INDEXES.put(154, SG154);
        INDEXES.put(155, SG155);
        INDEXES.put(156, SG156);
        INDEXES.put(157, SG157);
        INDEXES.put(158, SG158);
        INDEXES.put(159, SG159);
        INDEXES.put(160, SG160);
        INDEXES.put(161, SG161);
        INDEXES.put(162, SG162);
        INDEXES.put(163, SG163);
        INDEXES.put(164, SG164);
        INDEXES.put(165, SG165);
        INDEXES.put(166, SG166);
        INDEXES.put(167, SG167);
        INDEXES.put(168, SG168);
        INDEXES.put(169, SG169);
        INDEXES.put(170, SG170);
        INDEXES.put(171, SG171);
        INDEXES.put(172, SG172);
        INDEXES.put(173, SG173);
        INDEXES.put(174, SG174);
        INDEXES.put(175, SG175);
        INDEXES.put(176, SG176);
        INDEXES.put(177, SG177);
        INDEXES.put(178, SG178);
        INDEXES.put(179, SG179);
        INDEXES.put(180, SG180);
        INDEXES.put(181, SG181);
        INDEXES.put(182, SG182);
        INDEXES.put(183, SG183);
        INDEXES.put(184, SG184);
        INDEXES.put(185, SG185);
        INDEXES.put(186, SG186);
        INDEXES.put(187, SG187);
        INDEXES.put(188, SG188);
        INDEXES.put(189, SG189);
        INDEXES.put(190, SG190);
        INDEXES.put(191, SG191);
        INDEXES.put(192, SG192);
        INDEXES.put(193, SG193);
        INDEXES.put(194, SG194);
        INDEXES.put(195, SG195);
        INDEXES.put(196, SG196);
        INDEXES.put(197, SG197);
        INDEXES.put(198, SG198);
        INDEXES.put(199, SG199);
        INDEXES.put(200, SG200);
        INDEXES.put(201, SG201);
        INDEXES.put(202, SG202);
        INDEXES.put(203, SG203);
        INDEXES.put(204, SG204);
        INDEXES.put(205, SG205);
        INDEXES.put(206, SG206);
        INDEXES.put(207, SG207);
        INDEXES.put(208, SG208);
        INDEXES.put(209, SG209);
        INDEXES.put(210, SG210);
        INDEXES.put(211, SG211);
        INDEXES.put(212, SG212);
        INDEXES.put(213, SG213);
        INDEXES.put(214, SG214);
        INDEXES.put(215, SG215);
        INDEXES.put(216, SG216);
        INDEXES.put(217, SG217);
        INDEXES.put(218, SG218);
        INDEXES.put(219, SG219);
        INDEXES.put(220, SG220);
        INDEXES.put(221, SG221);
        INDEXES.put(222, SG222);
        INDEXES.put(223, SG223);
        INDEXES.put(224, SG224);
        INDEXES.put(225, SG225);
        INDEXES.put(226, SG226);
        INDEXES.put(227, SG227);
        INDEXES.put(228, SG228);
        INDEXES.put(229, SG229);
        INDEXES.put(230, SG230);

        // 7 extras
        INDEXES.put(1146, SG1146);
        INDEXES.put(1148, SG1148);
        INDEXES.put(1155, SG1155);
        INDEXES.put(1160, SG1160);
        INDEXES.put(1161, SG1161);
        INDEXES.put(1166, SG1166);
        INDEXES.put(1167, SG1167);
    }



    /**
     * Initializes the Laue group lookup table.
     */
    private static void initLaueGroups() {
        LAUE_GROUPS.put(LG1, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG1).add(SG1);
        LAUE_GROUPS.get(LG1).add(SG2);

        LAUE_GROUPS.put(LG2m, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG2m).add(SG3);
        LAUE_GROUPS.get(LG2m).add(SG4);
        LAUE_GROUPS.get(LG2m).add(SG5);
        LAUE_GROUPS.get(LG2m).add(SG6);
        LAUE_GROUPS.get(LG2m).add(SG7);
        LAUE_GROUPS.get(LG2m).add(SG8);
        LAUE_GROUPS.get(LG2m).add(SG9);
        LAUE_GROUPS.get(LG2m).add(SG10);
        LAUE_GROUPS.get(LG2m).add(SG11);
        LAUE_GROUPS.get(LG2m).add(SG12);
        LAUE_GROUPS.get(LG2m).add(SG13);
        LAUE_GROUPS.get(LG2m).add(SG14);
        LAUE_GROUPS.get(LG2m).add(SG15);

        LAUE_GROUPS.put(LGmmm, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LGmmm).add(SG16);
        LAUE_GROUPS.get(LGmmm).add(SG17);
        LAUE_GROUPS.get(LGmmm).add(SG18);
        LAUE_GROUPS.get(LGmmm).add(SG19);
        LAUE_GROUPS.get(LGmmm).add(SG20);
        LAUE_GROUPS.get(LGmmm).add(SG21);
        LAUE_GROUPS.get(LGmmm).add(SG22);
        LAUE_GROUPS.get(LGmmm).add(SG23);
        LAUE_GROUPS.get(LGmmm).add(SG24);
        LAUE_GROUPS.get(LGmmm).add(SG25);
        LAUE_GROUPS.get(LGmmm).add(SG26);
        LAUE_GROUPS.get(LGmmm).add(SG27);
        LAUE_GROUPS.get(LGmmm).add(SG28);
        LAUE_GROUPS.get(LGmmm).add(SG29);
        LAUE_GROUPS.get(LGmmm).add(SG30);
        LAUE_GROUPS.get(LGmmm).add(SG31);
        LAUE_GROUPS.get(LGmmm).add(SG32);
        LAUE_GROUPS.get(LGmmm).add(SG33);
        LAUE_GROUPS.get(LGmmm).add(SG34);
        LAUE_GROUPS.get(LGmmm).add(SG35);
        LAUE_GROUPS.get(LGmmm).add(SG36);
        LAUE_GROUPS.get(LGmmm).add(SG37);
        LAUE_GROUPS.get(LGmmm).add(SG38);
        LAUE_GROUPS.get(LGmmm).add(SG39);
        LAUE_GROUPS.get(LGmmm).add(SG40);
        LAUE_GROUPS.get(LGmmm).add(SG41);
        LAUE_GROUPS.get(LGmmm).add(SG42);
        LAUE_GROUPS.get(LGmmm).add(SG43);
        LAUE_GROUPS.get(LGmmm).add(SG44);
        LAUE_GROUPS.get(LGmmm).add(SG45);
        LAUE_GROUPS.get(LGmmm).add(SG46);
        LAUE_GROUPS.get(LGmmm).add(SG47);
        LAUE_GROUPS.get(LGmmm).add(SG48);
        LAUE_GROUPS.get(LGmmm).add(SG49);
        LAUE_GROUPS.get(LGmmm).add(SG50);
        LAUE_GROUPS.get(LGmmm).add(SG51);
        LAUE_GROUPS.get(LGmmm).add(SG52);
        LAUE_GROUPS.get(LGmmm).add(SG53);
        LAUE_GROUPS.get(LGmmm).add(SG54);
        LAUE_GROUPS.get(LGmmm).add(SG55);
        LAUE_GROUPS.get(LGmmm).add(SG56);
        LAUE_GROUPS.get(LGmmm).add(SG57);
        LAUE_GROUPS.get(LGmmm).add(SG58);
        LAUE_GROUPS.get(LGmmm).add(SG59);
        LAUE_GROUPS.get(LGmmm).add(SG60);
        LAUE_GROUPS.get(LGmmm).add(SG61);
        LAUE_GROUPS.get(LGmmm).add(SG62);
        LAUE_GROUPS.get(LGmmm).add(SG63);
        LAUE_GROUPS.get(LGmmm).add(SG64);
        LAUE_GROUPS.get(LGmmm).add(SG65);
        LAUE_GROUPS.get(LGmmm).add(SG66);
        LAUE_GROUPS.get(LGmmm).add(SG67);
        LAUE_GROUPS.get(LGmmm).add(SG68);
        LAUE_GROUPS.get(LGmmm).add(SG69);
        LAUE_GROUPS.get(LGmmm).add(SG70);
        LAUE_GROUPS.get(LGmmm).add(SG71);
        LAUE_GROUPS.get(LGmmm).add(SG72);
        LAUE_GROUPS.get(LGmmm).add(SG73);
        LAUE_GROUPS.get(LGmmm).add(SG74);

        LAUE_GROUPS.put(LG4m, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG4m).add(SG75);
        LAUE_GROUPS.get(LG4m).add(SG76);
        LAUE_GROUPS.get(LG4m).add(SG77);
        LAUE_GROUPS.get(LG4m).add(SG78);
        LAUE_GROUPS.get(LG4m).add(SG79);
        LAUE_GROUPS.get(LG4m).add(SG80);
        LAUE_GROUPS.get(LG4m).add(SG81);
        LAUE_GROUPS.get(LG4m).add(SG82);
        LAUE_GROUPS.get(LG4m).add(SG83);
        LAUE_GROUPS.get(LG4m).add(SG84);
        LAUE_GROUPS.get(LG4m).add(SG85);
        LAUE_GROUPS.get(LG4m).add(SG86);
        LAUE_GROUPS.get(LG4m).add(SG87);
        LAUE_GROUPS.get(LG4m).add(SG88);

        LAUE_GROUPS.put(LG4mmm, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG4mmm).add(SG89);
        LAUE_GROUPS.get(LG4mmm).add(SG90);
        LAUE_GROUPS.get(LG4mmm).add(SG91);
        LAUE_GROUPS.get(LG4mmm).add(SG92);
        LAUE_GROUPS.get(LG4mmm).add(SG93);
        LAUE_GROUPS.get(LG4mmm).add(SG94);
        LAUE_GROUPS.get(LG4mmm).add(SG95);
        LAUE_GROUPS.get(LG4mmm).add(SG96);
        LAUE_GROUPS.get(LG4mmm).add(SG97);
        LAUE_GROUPS.get(LG4mmm).add(SG98);
        LAUE_GROUPS.get(LG4mmm).add(SG99);
        LAUE_GROUPS.get(LG4mmm).add(SG100);
        LAUE_GROUPS.get(LG4mmm).add(SG101);
        LAUE_GROUPS.get(LG4mmm).add(SG102);
        LAUE_GROUPS.get(LG4mmm).add(SG103);
        LAUE_GROUPS.get(LG4mmm).add(SG104);
        LAUE_GROUPS.get(LG4mmm).add(SG105);
        LAUE_GROUPS.get(LG4mmm).add(SG106);
        LAUE_GROUPS.get(LG4mmm).add(SG107);
        LAUE_GROUPS.get(LG4mmm).add(SG108);
        LAUE_GROUPS.get(LG4mmm).add(SG109);
        LAUE_GROUPS.get(LG4mmm).add(SG110);
        LAUE_GROUPS.get(LG4mmm).add(SG111);
        LAUE_GROUPS.get(LG4mmm).add(SG112);
        LAUE_GROUPS.get(LG4mmm).add(SG113);
        LAUE_GROUPS.get(LG4mmm).add(SG114);
        LAUE_GROUPS.get(LG4mmm).add(SG115);
        LAUE_GROUPS.get(LG4mmm).add(SG116);
        LAUE_GROUPS.get(LG4mmm).add(SG117);
        LAUE_GROUPS.get(LG4mmm).add(SG118);
        LAUE_GROUPS.get(LG4mmm).add(SG119);
        LAUE_GROUPS.get(LG4mmm).add(SG120);
        LAUE_GROUPS.get(LG4mmm).add(SG121);
        LAUE_GROUPS.get(LG4mmm).add(SG122);
        LAUE_GROUPS.get(LG4mmm).add(SG123);
        LAUE_GROUPS.get(LG4mmm).add(SG124);
        LAUE_GROUPS.get(LG4mmm).add(SG125);
        LAUE_GROUPS.get(LG4mmm).add(SG126);
        LAUE_GROUPS.get(LG4mmm).add(SG127);
        LAUE_GROUPS.get(LG4mmm).add(SG128);
        LAUE_GROUPS.get(LG4mmm).add(SG129);
        LAUE_GROUPS.get(LG4mmm).add(SG130);
        LAUE_GROUPS.get(LG4mmm).add(SG131);
        LAUE_GROUPS.get(LG4mmm).add(SG132);
        LAUE_GROUPS.get(LG4mmm).add(SG133);
        LAUE_GROUPS.get(LG4mmm).add(SG134);
        LAUE_GROUPS.get(LG4mmm).add(SG135);
        LAUE_GROUPS.get(LG4mmm).add(SG136);
        LAUE_GROUPS.get(LG4mmm).add(SG137);
        LAUE_GROUPS.get(LG4mmm).add(SG138);
        LAUE_GROUPS.get(LG4mmm).add(SG139);
        LAUE_GROUPS.get(LG4mmm).add(SG140);
        LAUE_GROUPS.get(LG4mmm).add(SG141);
        LAUE_GROUPS.get(LG4mmm).add(SG142);

        LAUE_GROUPS.put(LG3, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG3).add(SG143);
        LAUE_GROUPS.get(LG3).add(SG144);
        LAUE_GROUPS.get(LG3).add(SG145);
        LAUE_GROUPS.get(LG3).add(SG146);
        LAUE_GROUPS.get(LG3).add(SG1146);
        LAUE_GROUPS.get(LG3).add(SG147);
        LAUE_GROUPS.get(LG3).add(SG148);
        LAUE_GROUPS.get(LG3).add(SG1148);

        LAUE_GROUPS.put(LG3m, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG3m).add(SG149);
        LAUE_GROUPS.get(LG3m).add(SG150);
        LAUE_GROUPS.get(LG3m).add(SG151);
        LAUE_GROUPS.get(LG3m).add(SG152);
        LAUE_GROUPS.get(LG3m).add(SG153);
        LAUE_GROUPS.get(LG3m).add(SG154);
        LAUE_GROUPS.get(LG3m).add(SG155);
        LAUE_GROUPS.get(LG3m).add(SG1155);
        LAUE_GROUPS.get(LG3m).add(SG156);
        LAUE_GROUPS.get(LG3m).add(SG157);
        LAUE_GROUPS.get(LG3m).add(SG158);
        LAUE_GROUPS.get(LG3m).add(SG159);
        LAUE_GROUPS.get(LG3m).add(SG160);
        LAUE_GROUPS.get(LG3m).add(SG1160);
        LAUE_GROUPS.get(LG3m).add(SG161);
        LAUE_GROUPS.get(LG3m).add(SG1161);
        LAUE_GROUPS.get(LG3m).add(SG162);
        LAUE_GROUPS.get(LG3m).add(SG163);
        LAUE_GROUPS.get(LG3m).add(SG164);
        LAUE_GROUPS.get(LG3m).add(SG165);
        LAUE_GROUPS.get(LG3m).add(SG166);
        LAUE_GROUPS.get(LG3m).add(SG1166);
        LAUE_GROUPS.get(LG3m).add(SG167);
        LAUE_GROUPS.get(LG3m).add(SG1167);

        LAUE_GROUPS.put(LG6m, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG6m).add(SG168);
        LAUE_GROUPS.get(LG6m).add(SG169);
        LAUE_GROUPS.get(LG6m).add(SG170);
        LAUE_GROUPS.get(LG6m).add(SG171);
        LAUE_GROUPS.get(LG6m).add(SG172);
        LAUE_GROUPS.get(LG6m).add(SG173);
        LAUE_GROUPS.get(LG6m).add(SG174);
        LAUE_GROUPS.get(LG6m).add(SG175);
        LAUE_GROUPS.get(LG6m).add(SG176);

        LAUE_GROUPS.put(LG6mmm, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LG6mmm).add(SG177);
        LAUE_GROUPS.get(LG6mmm).add(SG178);
        LAUE_GROUPS.get(LG6mmm).add(SG179);
        LAUE_GROUPS.get(LG6mmm).add(SG180);
        LAUE_GROUPS.get(LG6mmm).add(SG181);
        LAUE_GROUPS.get(LG6mmm).add(SG182);
        LAUE_GROUPS.get(LG6mmm).add(SG183);
        LAUE_GROUPS.get(LG6mmm).add(SG184);
        LAUE_GROUPS.get(LG6mmm).add(SG185);
        LAUE_GROUPS.get(LG6mmm).add(SG186);
        LAUE_GROUPS.get(LG6mmm).add(SG187);
        LAUE_GROUPS.get(LG6mmm).add(SG188);
        LAUE_GROUPS.get(LG6mmm).add(SG189);
        LAUE_GROUPS.get(LG6mmm).add(SG190);
        LAUE_GROUPS.get(LG6mmm).add(SG191);
        LAUE_GROUPS.get(LG6mmm).add(SG192);
        LAUE_GROUPS.get(LG6mmm).add(SG193);
        LAUE_GROUPS.get(LG6mmm).add(SG194);

        LAUE_GROUPS.put(LGm3, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LGm3).add(SG195);
        LAUE_GROUPS.get(LGm3).add(SG196);
        LAUE_GROUPS.get(LGm3).add(SG197);
        LAUE_GROUPS.get(LGm3).add(SG198);
        LAUE_GROUPS.get(LGm3).add(SG199);
        LAUE_GROUPS.get(LGm3).add(SG200);
        LAUE_GROUPS.get(LGm3).add(SG201);
        LAUE_GROUPS.get(LGm3).add(SG202);
        LAUE_GROUPS.get(LGm3).add(SG203);
        LAUE_GROUPS.get(LGm3).add(SG204);
        LAUE_GROUPS.get(LGm3).add(SG205);
        LAUE_GROUPS.get(LGm3).add(SG206);

        LAUE_GROUPS.put(LGm3m, new HashSet<SpaceGroup>());
        LAUE_GROUPS.get(LGm3m).add(SG207);
        LAUE_GROUPS.get(LGm3m).add(SG208);
        LAUE_GROUPS.get(LGm3m).add(SG209);
        LAUE_GROUPS.get(LGm3m).add(SG210);
        LAUE_GROUPS.get(LGm3m).add(SG211);
        LAUE_GROUPS.get(LGm3m).add(SG212);
        LAUE_GROUPS.get(LGm3m).add(SG213);
        LAUE_GROUPS.get(LGm3m).add(SG214);
        LAUE_GROUPS.get(LGm3m).add(SG215);
        LAUE_GROUPS.get(LGm3m).add(SG216);
        LAUE_GROUPS.get(LGm3m).add(SG217);
        LAUE_GROUPS.get(LGm3m).add(SG218);
        LAUE_GROUPS.get(LGm3m).add(SG219);
        LAUE_GROUPS.get(LGm3m).add(SG220);
        LAUE_GROUPS.get(LGm3m).add(SG221);
        LAUE_GROUPS.get(LGm3m).add(SG222);
        LAUE_GROUPS.get(LGm3m).add(SG223);
        LAUE_GROUPS.get(LGm3m).add(SG224);
        LAUE_GROUPS.get(LGm3m).add(SG225);
        LAUE_GROUPS.get(LGm3m).add(SG226);
        LAUE_GROUPS.get(LGm3m).add(SG227);
        LAUE_GROUPS.get(LGm3m).add(SG228);
        LAUE_GROUPS.get(LGm3m).add(SG229);
        LAUE_GROUPS.get(LGm3m).add(SG230);
    }



    /**
     * Initializes the SYMBOLS lookup table.
     */
    private static void initSymbols() {
        SYMBOLS.put("P1", SG1);
        SYMBOLS.put("P-1", SG2);
        SYMBOLS.put("P2", SG3);
        SYMBOLS.put("P21", SG4);
        SYMBOLS.put("C2", SG5);
        SYMBOLS.put("Pm", SG6);
        SYMBOLS.put("Pc", SG7);
        SYMBOLS.put("Cm", SG8);
        SYMBOLS.put("Cc", SG9);
        SYMBOLS.put("P2/m", SG10);
        SYMBOLS.put("P21/m", SG11);
        SYMBOLS.put("C2/m", SG12);
        SYMBOLS.put("P2/c", SG13);
        SYMBOLS.put("P21/c", SG14);
        SYMBOLS.put("C2/c", SG15);
        SYMBOLS.put("P222", SG16);
        SYMBOLS.put("P2221", SG17);
        SYMBOLS.put("P21212", SG18);
        SYMBOLS.put("P212121", SG19);
        SYMBOLS.put("C2221", SG20);
        SYMBOLS.put("C222", SG21);
        SYMBOLS.put("F222", SG22);
        SYMBOLS.put("I222", SG23);
        SYMBOLS.put("I212121", SG24);
        SYMBOLS.put("Pmm2", SG25);
        SYMBOLS.put("Pmc21", SG26);
        SYMBOLS.put("Pcc2", SG27);
        SYMBOLS.put("Pma2", SG28);
        SYMBOLS.put("Pca21", SG29);
        SYMBOLS.put("Pnc2", SG30);
        SYMBOLS.put("Pmn21", SG31);
        SYMBOLS.put("Pba2", SG32);
        SYMBOLS.put("Pna21", SG33);
        SYMBOLS.put("Pnn2", SG34);
        SYMBOLS.put("Cmm2", SG35);
        SYMBOLS.put("Cmc21", SG36);
        SYMBOLS.put("Ccc2", SG37);
        SYMBOLS.put("Amm2", SG38);
        SYMBOLS.put("Abm2", SG39);
        SYMBOLS.put("Ama2", SG40);
        SYMBOLS.put("Aba2", SG41);
        SYMBOLS.put("Fmm2", SG42);
        SYMBOLS.put("Fdd2", SG43);
        SYMBOLS.put("Imm2", SG44);
        SYMBOLS.put("Iba2", SG45);
        SYMBOLS.put("Ima2", SG46);
        SYMBOLS.put("Pmmm", SG47);
        SYMBOLS.put("Pnnn", SG48);
        SYMBOLS.put("Pccm", SG49);
        SYMBOLS.put("Pban", SG50);
        SYMBOLS.put("Pmma", SG51);
        SYMBOLS.put("Pnna", SG52);
        SYMBOLS.put("Pmna", SG53);
        SYMBOLS.put("Pcca", SG54);
        SYMBOLS.put("Pbam", SG55);
        SYMBOLS.put("Pccn", SG56);
        SYMBOLS.put("Pbcm", SG57);
        SYMBOLS.put("Pnnm", SG58);
        SYMBOLS.put("Pmmn", SG59);
        SYMBOLS.put("Pbcn", SG60);
        SYMBOLS.put("Pbca", SG61);
        SYMBOLS.put("Pnma", SG62);
        SYMBOLS.put("Cmcm", SG63);
        SYMBOLS.put("Cmca", SG64);
        SYMBOLS.put("Cmmm", SG65);
        SYMBOLS.put("Cccm", SG66);
        SYMBOLS.put("Cmma", SG67);
        SYMBOLS.put("Ccca", SG68);
        SYMBOLS.put("Fmmm", SG69);
        SYMBOLS.put("Fddd", SG70);
        SYMBOLS.put("Immm", SG71);
        SYMBOLS.put("Ibam", SG72);
        SYMBOLS.put("Ibca", SG73);
        SYMBOLS.put("Imma", SG74);
        SYMBOLS.put("P4", SG75);
        SYMBOLS.put("P41", SG76);
        SYMBOLS.put("P42", SG77);
        SYMBOLS.put("P43", SG78);
        SYMBOLS.put("I4", SG79);
        SYMBOLS.put("I41", SG80);
        SYMBOLS.put("P-4", SG81);
        SYMBOLS.put("I-4", SG82);
        SYMBOLS.put("P4/m", SG83);
        SYMBOLS.put("P42/m", SG84);
        SYMBOLS.put("P4/n", SG85);
        SYMBOLS.put("P42/n", SG86);
        SYMBOLS.put("I4/m", SG87);
        SYMBOLS.put("I41/a", SG88);
        SYMBOLS.put("P422", SG89);
        SYMBOLS.put("P4212", SG90);
        SYMBOLS.put("P4122", SG91);
        SYMBOLS.put("P41212", SG92);
        SYMBOLS.put("P4222", SG93);
        SYMBOLS.put("P42212", SG94);
        SYMBOLS.put("P4322", SG95);
        SYMBOLS.put("P43212", SG96);
        SYMBOLS.put("I422", SG97);
        SYMBOLS.put("I4122", SG98);
        SYMBOLS.put("P4mm", SG99);
        SYMBOLS.put("P4bm", SG100);
        SYMBOLS.put("P42cm", SG101);
        SYMBOLS.put("P42nm", SG102);
        SYMBOLS.put("P4cc", SG103);
        SYMBOLS.put("P4nc", SG104);
        SYMBOLS.put("P42mc", SG105);
        SYMBOLS.put("P42bc", SG106);
        SYMBOLS.put("I4mm", SG107);
        SYMBOLS.put("I4cm", SG108);
        SYMBOLS.put("I41md", SG109);
        SYMBOLS.put("I41cd", SG110);
        SYMBOLS.put("P-42m", SG111);
        SYMBOLS.put("P-42c", SG112);
        SYMBOLS.put("P-421m", SG113);
        SYMBOLS.put("P-421c", SG114);
        SYMBOLS.put("P-4m2", SG115);
        SYMBOLS.put("P-4c2", SG116);
        SYMBOLS.put("P-4b2", SG117);
        SYMBOLS.put("P-4n2", SG118);
        SYMBOLS.put("I-4m2", SG119);
        SYMBOLS.put("I-4c2", SG120);
        SYMBOLS.put("I-42m", SG121);
        SYMBOLS.put("I-42d", SG122);
        SYMBOLS.put("P4/mmm", SG123);
        SYMBOLS.put("P4/mcc", SG124);
        SYMBOLS.put("P4/nbm", SG125);
        SYMBOLS.put("P4/nnc", SG126);
        SYMBOLS.put("P4/mbm", SG127);
        SYMBOLS.put("P4/mnc", SG128);
        SYMBOLS.put("P4/nmm", SG129);
        SYMBOLS.put("P4/ncc", SG130);
        SYMBOLS.put("P42/mmc", SG131);
        SYMBOLS.put("P42/mcm", SG132);
        SYMBOLS.put("P42/nbc", SG133);
        SYMBOLS.put("P42/nnm", SG134);
        SYMBOLS.put("P42/mbc", SG135);
        SYMBOLS.put("P42/mnm", SG136);
        SYMBOLS.put("P42/nmc", SG137);
        SYMBOLS.put("P42/ncm", SG138);
        SYMBOLS.put("I4/mmm", SG139);
        SYMBOLS.put("I4/mcm", SG140);
        SYMBOLS.put("I41/amd", SG141);
        SYMBOLS.put("I41/acd", SG142);
        SYMBOLS.put("P3", SG143);
        SYMBOLS.put("P31", SG144);
        SYMBOLS.put("P32", SG145);
        SYMBOLS.put("H3", SG146);
        SYMBOLS.put("R3", SG1146);
        SYMBOLS.put("P-3", SG147);
        SYMBOLS.put("H-3", SG148);
        SYMBOLS.put("R-3", SG1148);
        SYMBOLS.put("P312", SG149);
        SYMBOLS.put("P321", SG150);
        SYMBOLS.put("P3112", SG151);
        SYMBOLS.put("P3121", SG152);
        SYMBOLS.put("P3212", SG153);
        SYMBOLS.put("P3221", SG154);
        SYMBOLS.put("H32", SG155);
        SYMBOLS.put("R32", SG1155);
        SYMBOLS.put("P3m1", SG156);
        SYMBOLS.put("P31m", SG157);
        SYMBOLS.put("P3c1", SG158);
        SYMBOLS.put("P31c", SG159);
        SYMBOLS.put("H3m", SG160);
        SYMBOLS.put("R3m", SG1160);
        SYMBOLS.put("H3c", SG161);
        SYMBOLS.put("R3c", SG1161);
        SYMBOLS.put("P-31m", SG162);
        SYMBOLS.put("P-31c", SG163);
        SYMBOLS.put("P-3m1", SG164);
        SYMBOLS.put("P-3c1", SG165);
        SYMBOLS.put("H-3m", SG166);
        SYMBOLS.put("R-3m", SG1166);
        SYMBOLS.put("H-3c", SG167);
        SYMBOLS.put("R-3c", SG1167);
        SYMBOLS.put("P6", SG168);
        SYMBOLS.put("P61", SG169);
        SYMBOLS.put("P65", SG170);
        SYMBOLS.put("P62", SG171);
        SYMBOLS.put("P64", SG172);
        SYMBOLS.put("P63", SG173);
        SYMBOLS.put("P-6", SG174);
        SYMBOLS.put("P6/m", SG175);
        SYMBOLS.put("P63/m", SG176);
        SYMBOLS.put("P622", SG177);
        SYMBOLS.put("P6122", SG178);
        SYMBOLS.put("P6522", SG179);
        SYMBOLS.put("P6222", SG180);
        SYMBOLS.put("P6422", SG181);
        SYMBOLS.put("P6322", SG182);
        SYMBOLS.put("P6mm", SG183);
        SYMBOLS.put("P6cc", SG184);
        SYMBOLS.put("P63cm", SG185);
        SYMBOLS.put("P63mc", SG186);
        SYMBOLS.put("P-6m2", SG187);
        SYMBOLS.put("P-6c2", SG188);
        SYMBOLS.put("P-62m", SG189);
        SYMBOLS.put("P-62c", SG190);
        SYMBOLS.put("P6/mmm", SG191);
        SYMBOLS.put("P6/mcc", SG192);
        SYMBOLS.put("P63/mcm", SG193);
        SYMBOLS.put("P63/mmc", SG194);
        SYMBOLS.put("P23", SG195);
        SYMBOLS.put("F23", SG196);
        SYMBOLS.put("I23", SG197);
        SYMBOLS.put("P213", SG198);
        SYMBOLS.put("I213", SG199);
        SYMBOLS.put("Pm-3", SG200);
        SYMBOLS.put("Pn-3", SG201);
        SYMBOLS.put("Fm-3", SG202);
        SYMBOLS.put("Fd-3", SG203);
        SYMBOLS.put("Im-3", SG204);
        SYMBOLS.put("Pa-3", SG205);
        SYMBOLS.put("Ia-3", SG206);
        SYMBOLS.put("P432", SG207);
        SYMBOLS.put("P4232", SG208);
        SYMBOLS.put("F432", SG209);
        SYMBOLS.put("F4132", SG210);
        SYMBOLS.put("I432", SG211);
        SYMBOLS.put("P4332", SG212);
        SYMBOLS.put("P4132", SG213);
        SYMBOLS.put("I4132", SG214);
        SYMBOLS.put("P-43m", SG215);
        SYMBOLS.put("F-43m", SG216);
        SYMBOLS.put("I-43m", SG217);
        SYMBOLS.put("P-43n", SG218);
        SYMBOLS.put("F-43c", SG219);
        SYMBOLS.put("I-43d", SG220);
        SYMBOLS.put("Pm-3m", SG221);
        SYMBOLS.put("Pn-3n", SG222);
        SYMBOLS.put("Pm-3n", SG223);
        SYMBOLS.put("Pn-3m", SG224);
        SYMBOLS.put("Fm-3m", SG225);
        SYMBOLS.put("Fm-3c", SG226);
        SYMBOLS.put("Fd-3m", SG227);
        SYMBOLS.put("Fd-3c", SG228);
        SYMBOLS.put("Im-3m", SG229);
        SYMBOLS.put("Ia-3d", SG230);
    }
}
