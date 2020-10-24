package com.company;

import javax.swing.*;

public class Sample extends JFrame {
    public Sample() {
        setSize(1000, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        JLabel sample = new JLabel("sample");

        add(sample);
    }
}
