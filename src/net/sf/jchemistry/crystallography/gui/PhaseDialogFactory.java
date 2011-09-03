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
package net.sf.jchemistry.crystallography.gui;

import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.sf.jchemistry.crystallography.core.Phase;
import net.sf.jchemistry.crystallography.core.SpaceGroups;
import net.sf.jchemistry.crystallography.core.UnitCellFactory;
import net.sf.jchemistry.util.IOUtils;
import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * Factory of dialog to create, edit and view a phase.
 * 
 * @author ppinard
 */
public class PhaseDialogFactory {

    /** Icon for the add button. */
    private static final ImageIcon ADD_ICON =
            new ImageIcon(
                    IOUtils.getURL("net/sf/jchemistry/crystallography/gui/icon/list-add_(22x22).png"));

    /** Icon for the remove button. */
    private static final ImageIcon REMOVE_ICON =
            new ImageIcon(
                    IOUtils.getURL("net/sf/jchemistry/crystallography/gui/icon/list-remove_(22x22).png"));

    /** Icon for the clear button. */
    private static final ImageIcon CLEAR_ICON =
            new ImageIcon(
                    IOUtils.getURL("net/sf/jchemistry/crystallography/gui/icon/list-clear_(22x22).png"));

    /** Icon for the calculation button. */
    private static final ImageIcon CALC_ICON =
            new ImageIcon(
                    IOUtils.getURL("net/sf/jchemistry/crystallography/gui/icon/calc_(22x22).png"));

    /** Default values when creating a new phase. */
    private static final Phase NEW_PHASE = new Phase("Untitled",
            SpaceGroups.SG1, UnitCellFactory.triclinic(1, 2, 3, 0.1, 0.2, 0.3));



    /**
     * Shows a dialog to edit a phase. If the dialog is closed by the user
     * clicking on the <code>Ok</code> button, a new phase is returned (new
     * object != original phase), otherwise the original phase is returned.
     * 
     * @param owner
     *            parent window
     * @param phase
     *            a phase
     * @return new phase
     */
    public static Phase showEditDialog(Window owner, Phase phase) {
        PhaseEditDialog dialog =
                new PhaseEditDialog(owner, "Edit phase", phase, ADD_ICON,
                        REMOVE_ICON, CLEAR_ICON, CALC_ICON);
        if (dialog.show() != JOptionPane.OK_OPTION)
            return phase;

        return dialog.getPhase();
    }



    /**
     * Shows a dialog to view the values of the specified phase. The values
     * cannot be edited but only view.
     * 
     * @param owner
     *            parent window
     * @param phases
     *            one or more phases
     */
    public static void showInfoDialog(Window owner, Phase... phases) {
        PhaseInfoDialog dialog = new PhaseInfoDialog(owner, "Info", phases);
        dialog.show();
    }



    /**
     * Shows a dialog to create a new phase. If the dialog is closed by the user
     * clicking on the <code>Ok</code> button, the new phase is returned,
     * otherwise <code>null</code>.
     * 
     * @param owner
     *            parent window
     * @return new phase or <code>null</code>
     */
    @CheckForNull
    public static Phase showNewDialog(Window owner) {
        PhaseEditDialog dialog =
                new PhaseEditDialog(owner, "New phase", NEW_PHASE, ADD_ICON,
                        REMOVE_ICON, CLEAR_ICON, CALC_ICON);
        if (dialog.show() != JOptionPane.OK_OPTION)
            return null;

        return dialog.getPhase();
    }
}
