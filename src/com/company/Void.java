package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Void extends JFrame {
    public Void() {
        setContentPane(new JLabel(new ImageIcon("/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/FinalOutput-master/Backgrounds/v1.png")));
        setResizable(false);
        setVisible(false);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Components
        JLabel lblUserName = new JLabel("Username: ");
        JTextField inpUsername = new JTextField();
        inpUsername.setPreferredSize(new Dimension(300, 40));
        lblUserName.setLabelFor(inpUsername);

        JLabel lblPassword = new JLabel("Password: ");
        JPasswordField inpPassword = new JPasswordField();
        inpPassword.setPreferredSize(new Dimension(300, 40));
        lblPassword.setLabelFor(inpPassword);

        JButton SubmitButton = new JButton("Void");
        SubmitButton.setPreferredSize(new Dimension(300, 40));

        // Functions
        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!inpUsername.getText().isEmpty() && !inpPassword.getText().isEmpty()){
                    try {
                        Scanner adminList = new Scanner(new FileReader("/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/adminList.txt"));
                        while(adminList.hasNext()){
                            String name = adminList.next();
                            String password = adminList.next();

                            if(name.equals(inpUsername.getText()) && password.equals(new String(inpPassword.getPassword()))){

                                Main.customerTableModel.setRowCount(0);
                                Main.TOTAL = 0;
                                Main.lblTotal.setText(Integer.toString(Main.TOTAL));
                                setVisible(false);
                                break;
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Layouts
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        add(lblUserName, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        add(inpUsername, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        add(lblPassword, gridBagConstraints);
        gridBagConstraints.gridy = 3;
        add(inpPassword, gridBagConstraints);
        gridBagConstraints.gridy = 4;
        add(SubmitButton, gridBagConstraints);
    }
}
