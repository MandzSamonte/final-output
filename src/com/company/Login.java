package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Login extends JFrame {
    public Login() {
        setContentPane(new JLabel(new ImageIcon("sayap1.png")));
        setResizable(true);
        setSize(1000, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.decode("#d90429"));

        //Login Basic Component
        JLabel lblLogin = new JLabel("LOGIN ");
        JComboBox cbbLoginType = new JComboBox();
        JButton btnLogin = new JButton("Login");
        JLabel lblErr = new JLabel("");

        lblLogin.setForeground(Color.white);
        lblLogin.setFont(new Font(lblLogin.getName(), Font.BOLD, 18));

        lblErr.setForeground(Color.white);

        // Setting Button Apperance
        btnLogin.setBorder(BorderFactory.createLineBorder(Color.white));
        btnLogin.setOpaque(false);
        btnLogin.setForeground(Color.white);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(Color.white);
                btnLogin.setForeground(Color.red);
                btnLogin.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setOpaque(false);
                btnLogin.setForeground(Color.white);
            }
        });

        //Counter Number Input
        JTextField txfCounterInput = new JTextField();

        //Admim Input
        JTextField txfAdminName = new JTextField();
        JPasswordField txfAdminPass = new JPasswordField();

        cbbLoginType.addItem("Counter");
        cbbLoginType.addItem("Admin");

        cbbLoginType.setPreferredSize(new Dimension(300, 40));
        btnLogin.setPreferredSize(new Dimension(300, 40));

        //Textfields
        txfCounterInput.setPreferredSize(new Dimension(300, 40));
        txfAdminName.setPreferredSize(new Dimension(300, 40));
        txfAdminPass.setPreferredSize(new Dimension(300, 40));

        //Separating Counter and Admin Input
        JPanel counterInputPanel = new JPanel();
        JPanel adminInputPanel = new JPanel();
        counterInputPanel.setOpaque(false);
        adminInputPanel.setOpaque(false);

        counterInputPanel.add(txfCounterInput);
        adminInputPanel.setLayout(new BoxLayout(adminInputPanel, BoxLayout.Y_AXIS));
        adminInputPanel.add(txfAdminName);
        adminInputPanel.add(txfAdminPass);

        //Setting Visibility
        counterInputPanel.setVisible(true);
        adminInputPanel.setVisible(false);

        cbbLoginType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    switch (cbbLoginType.getSelectedIndex()){
                        case 0:
                            counterInputPanel.setVisible(true);
                            adminInputPanel.setVisible(false);
                            break;
                        case 1:
                            counterInputPanel.setVisible(false);
                            adminInputPanel.setVisible(true);
                            break;
                    }
                }
            }
        });


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (cbbLoginType.getSelectedIndex()){
                    case 0:
                        if(!txfCounterInput.getText().isEmpty()){
                            Main.auth = "employee";
                            Main main = new Main();
                            main.setVisible(true);
                            setVisible(false);
                        } else {
                            lblErr.setText("Please Enter a Counter Number");
                        }
                        break;
                    case 1:
                        if(!txfAdminName.getText().isEmpty() && !txfAdminPass.getText().isEmpty()){
                            try {
                                Scanner adminList = new Scanner(new FileReader("/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/adminList.txt"));
                                while(adminList.hasNext()){
                                    String name = adminList.next();
                                    String password = adminList.next();

                                    if(name.equals(txfAdminName.getText()) && password.equals(new String(txfAdminPass.getPassword()))){
                                        Main main = new Main();
                                        main.auth = "admin";
                                        main.setVisible(true);
                                        setVisible(false);
                                        break;
                                    } else {
                                        lblErr.setText("Your input might be wrong, please try again");
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            lblErr.setText("Please Enter Name and Password");
                        }
                        break;
                }
            }
        });

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        getContentPane().setLayout(gridBagLayout);
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(0, 0 ,10, 0);
        getContentPane().add(lblLogin, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        getContentPane().add(cbbLoginType, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        getContentPane().add(counterInputPanel, gridBagConstraints);
        gridBagConstraints.gridy = 3;
        getContentPane().add(adminInputPanel, gridBagConstraints);
        gridBagConstraints.gridy = 4;
        getContentPane().add(btnLogin, gridBagConstraints);
        gridBagConstraints.gridy = 5;
        getContentPane().add(lblErr, gridBagConstraints);

        setVisible(true);
    }
}
