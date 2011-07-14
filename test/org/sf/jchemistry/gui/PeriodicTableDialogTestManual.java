package org.sf.jchemistry.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PeriodicTableDialogTestManual {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Periodic Table Dialog Test");
        final PeriodicTableDialog dialog = new PeriodicTableDialog(frame);

        JButton button = new JButton("show");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
                System.out.println(dialog.getSelection());
            }
        });
        frame.add(button);

        frame.pack();
        frame.setVisible(true);
    }
}
