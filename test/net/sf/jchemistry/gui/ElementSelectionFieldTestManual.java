package net.sf.jchemistry.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import net.sf.jchemistry.core.Element;

public class ElementSelectionFieldTestManual {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new MigLayout());
        frame.add(panel);

        panel.add(new JLabel("Single"));
        final ElementSelectionField field1 = new ElementSelectionField();
        panel.add(field1, "wrap");

        panel.add(new JLabel("Multi"));
        final ElementSelectionField field2 = new ElementSelectionField();
        field2.setMultiSelection(true);
        panel.add(field2, "wrap");

        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Single: " + field1.getSelections());
                System.out.println("Multi: " + field2.getSelections());
            }
        });
        panel.add(displayButton);

        List<Element> selection = new ArrayList<Element>();
        selection.add(Element.Si);
        selection.add(Element.Be);
        field1.setSelection(selection);
        field2.setSelection(selection);

        frame.pack();
        frame.setVisible(true);
    }
}
