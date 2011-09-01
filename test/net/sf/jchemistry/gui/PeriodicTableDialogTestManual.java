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
package net.sf.jchemistry.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import net.sf.jchemistry.core.Element;

public class PeriodicTableDialogTestManual {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Periodic Table Dialog Test");

        JButton button = new JButton("show");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Element> elements =
                        PeriodicTableDialogFactory.show(frame, true,
                                Element.Al, Element.Mg, Element.Al);
                System.out.println(elements);
            }
        });
        frame.add(button);

        frame.pack();
        frame.setVisible(true);
    }
}
