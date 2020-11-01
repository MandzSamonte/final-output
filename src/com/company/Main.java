package com.company;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

class Main extends JFrame {

    public static String auth;

    public static int TOTAL = 0;

    public static JLabel lblTotal = new JLabel();
    public static JTable tblCustomerOrder = new JTable();
    public static DefaultTableModel customerTableModel = new DefaultTableModel();

    public Main(){
        // setContentPane(new JLabel(new ImageIcon("/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/FinalOutput-master/Backgrounds/b2.png")));
        setVisible(false);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.decode("#edf2f4"));

        //Components
        JComboBox cbbProductType = new JComboBox();
        JLabel lblOrders = new JLabel("Orders:");



        lblTotal.setForeground(Color.white);


        lblOrders.setForeground(Color.white);

        DefaultTableModel tableModel = new DefaultTableModel();
        String[] productColumnIdentifiers = {"Name", "Price" };
        tableModel.setColumnIdentifiers(productColumnIdentifiers);

        String[] recieptColumnIdentifiers = {"Name","Price"};
        customerTableModel.setColumnIdentifiers(recieptColumnIdentifiers);

        JTable tblProductList = new JTable(tableModel);

        tblCustomerOrder.setModel(customerTableModel);

        String[] productType = {"Rice Meals", "Noodles", "Hotdog Sandwich", "Fries and Side", "Chicken", "Burger", "Breakfast", "Beverages and Desert"};
        String[][] productFilePath = {
                {"Rice Meals", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Rice Meals.txt"},
                {"Noodles", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Noodles.txt"},
                {"Hotdog Sandwich", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Hotdog Sandwich.txt"},
                {"Fries and Side", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Fries and Side.txt"},
                {"Chicken", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Chicken.txt"},
                {"Burger", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Burger.txt"},
                {"Breakfast", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Breakfast.txt"},
                {"Beverages and Desert", "/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/Products/Beverages and Desert.txt"}
        };

        for (int i = 0; i < productType.length; i++){
            cbbProductType.addItem(productType[i]);
        }

        //All Functions
        cbbProductType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    for (int i = 0; i < productFilePath.length; i++){
                        if(e.getItem().equals(productFilePath[i][0])){
                            try {
                                tableModel.setRowCount(0);
                                BufferedReader br = new BufferedReader(new FileReader(productFilePath[i][1]));
                                Object[] tableLines = br.lines().toArray();

                                for(int j = 0; j < tableLines.length; j++){
                                    String line = tableLines[j].toString().trim();
                                    String[] dataRow = line.split(",");
                                    tableModel.addRow(dataRow);
                                }
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }

                            break;
                        }
                    }
                }
            }
        });


        tblProductList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    customerTableModel.addRow(tableModel.getDataVector().elementAt(tblProductList.getSelectedRow()));

                    TOTAL += Integer.parseInt((String)customerTableModel.getValueAt(customerTableModel.getRowCount() - 1, 1));
                    lblTotal.setText(Integer.toString(TOTAL));
                }
            }
        });

        //All the Layout and Adding of Components
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        String[] tableHeader = {"Code Name", "Price"};
        String[][] data = {
                {"C1", "350"},
                {"C2", "450"}
        };
        setLayout(gridBagLayout);

        JPanel printContainer = new JPanel();


        JButton btnPrint = new JButton("Print Receipt");
        btnPrint.setMargin(new Insets(10, 5, 10, 5));
        btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrint.setBackground(Color.red);
        btnPrint.setBorderPainted(false);
        btnPrint.setOpaque(true);
        btnPrint.setForeground(Color.decode("#edf2f4"));
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reciept recieptFrame = new Reciept();
                Reciept.receiptTableModel.setRowCount(0);
                for(int i = 0; i < customerTableModel.getRowCount(); i++){
                    Reciept.receiptTableModel.addRow(customerTableModel.getDataVector().elementAt(i));
                }
                customerTableModel.setRowCount(0);
                TOTAL = 0;
                lblTotal.setText(Integer.toString(TOTAL));

                recieptFrame.setVisible(true);
            }
        });
        printContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        printContainer.setOpaque(false);
        printContainer.add(btnPrint);
        printContainer.add(lblTotal);

        JButton voidButton = new JButton("Void");
        voidButton.setPreferredSize(new Dimension(100, 40));
        voidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auth == "admin"){
                    customerTableModel.setRowCount (0);
                    TOTAL = 0;
                    lblTotal.setText(Integer.toString(TOTAL));
                }else{
                    Void voidFrame = new Void();
                    voidFrame.setVisible(true);
                }
            }
        });

        JPanel addItemContainer = new JPanel();
        addItemContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        addItemContainer.setOpaque(false);
        addItemContainer.add(voidButton);

        // Layout
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(20, 20, 20, 20);
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        add(cbbProductType, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        add(new JScrollPane(tblProductList), gridBagConstraints);
        gridBagConstraints.gridy = 2;
        add(addItemContainer, gridBagConstraints);
        gridBagConstraints.gridx =   1;
        gridBagConstraints.gridy = 0;
        add(lblOrders, gridBagConstraints);
        gridBagConstraints.gridy = 1;
        add(new JScrollPane(tblCustomerOrder), gridBagConstraints);
        gridBagConstraints.gridy = 2;
        add(printContainer, gridBagConstraints);
    }


    public static void main(String args[]){
        Login login = new Login();
    }
}