/*
 * jChemistry
 * Copyright (C) 2011 Philippe T. Pinard
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jchemistry.crystallography.test;

import net.sf.jchemistry.core.Element;
import net.sf.jchemistry.crystallography.core.AtomSitesFactory;
import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.Reflector;
import net.sf.jchemistry.crystallography.core.Reflectors;
import net.sf.jchemistry.crystallography.core.ScatteringFactorsFactory;
import net.sf.jchemistry.crystallography.core.SpaceGroups;
import net.sf.jchemistry.crystallography.core.UnitCellFactory;

public class PhaseFactory {

    public static Phase ferrite() {
        Phase phase =
                new Phase("Ferrite", SpaceGroups.fromIndex(229),
                        UnitCellFactory.cubic(2.87));

        phase.getAtoms().addAll(AtomSitesFactory.atomSitesBCC(Element.Fe));
        phase.computeReflectors(ScatteringFactorsFactory.XRAY_TABULATED, 2,
                0.01);

        return phase;
    }



    public static Phase silicon() {
        Phase phase =
                new Phase("Silicon", SpaceGroups.fromIndex(216),
                        UnitCellFactory.cubic(5.43));

        phase.getAtoms().addAll(AtomSitesFactory.atomSitesFCC(Element.Si));
        phase.computeReflectors(ScatteringFactorsFactory.XRAY_TABULATED, 2,
                0.01);

        return phase;
    }



    public static Phase zirconium() {
        Phase phase =
                new Phase("Zirconium", SpaceGroups.fromIndex(194),
                        UnitCellFactory.hexagonal(3.2, 5.15));

        phase.getAtoms().addAll(AtomSitesFactory.atomSitesHCP(Element.Zr));
        phase.computeReflectors(ScatteringFactorsFactory.XRAY_TABULATED, 2,
                0.01);

        return phase;
    }



    public static Phase kryptonite() {
        Phase phase =
                new Phase("Kryptonite", SpaceGroups.fromIndex(1),
                        UnitCellFactory.triclinic(1, 2, 3, 0.1, 0.2, 0.3));

        phase.getAtoms().addAll(
                AtomSitesFactory.atomSitesSingleAtom(Element.Es));

        Reflectors refls = phase.getReflectors();
        refls.add(new Reflector(1, 0, 0, 1.0));
        refls.add(new Reflector(0, 1, 0, 1.0));
        refls.add(new Reflector(0, 0, 1, 1.0));
        refls.add(new Reflector(1, 1, 0, 0.9));
        refls.add(new Reflector(0, 1, 1, 0.9));
        refls.add(new Reflector(1, 1, 1, 0.8));

        return phase;
    }
}
