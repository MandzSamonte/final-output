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
        setContentPane(new JLabel(new ImageIcon("/Users/johnmandysamonte/Library/Preferences/IdeaIC2019.3/scratches/FinalOutput/FinalOutput-master/Backgrounds/b2.png")));
        setVisible(false);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JComboBox cbbProductType = new JComboBox();

        lblTotal.setForeground(Color.white);

        JLabel lblOrders = new JLabel("Orders:");
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
        JButton printReceipt = new JButton("Print Receipt");
        printReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecieptFrame recieptFrame = new RecieptFrame();
                RecieptFrame.receiptTableModel.setRowCount(0);
                for(int i = 0; i < customerTableModel.getRowCount(); i++){
                    RecieptFrame.receiptTableModel.addRow(customerTableModel.getDataVector().elementAt(i));
                }
                customerTableModel.setRowCount(0);
                TOTAL = 0;
                lblTotal.setText(Integer.toString(TOTAL));

                recieptFrame.setVisible(true);
            }
        });
        printContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        printContainer.setOpaque(false);
        printContainer.add(printReceipt);
        printContainer.add(lblTotal);

        JButton voidButton = new JButton("Void");
        voidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auth == "admin"){
                    customerTableModel.setRowCount(0);
                    TOTAL = 0;
                    lblTotal.setText(Integer.toString(TOTAL));
                }else{
                    VoidItem voidItem = new VoidItem();
                    voidItem.setVisible(true);
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

    public static class VoidItem extends JFrame {
        public VoidItem() {
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


    public static class RecieptFrame extends JFrame {
        public static DefaultTableModel receiptTableModel = new DefaultTableModel();

        public RecieptFrame() {
            setVisible(false);
            setSize(400, 700);
            setDefaultCloseOperation(HIDE_ON_CLOSE);

            Container container = getContentPane();

            // Receipt Header
            JTextPane headerText = new JTextPane();
            headerText.setText(
                    "\nJOLLIBEE" +
                            "\n==Customers Copy==" +
                            "\nUnknown Place, Dasmariñas City, Cavite" +
                            "\nTel: ***********" +
                            "\nCounter #: " +
                            "\n******************************************\n"
            );
            StyledDocument headerDoc = headerText.getStyledDocument();
            SimpleAttributeSet headerCenter = new SimpleAttributeSet();
            StyleConstants.setAlignment(headerCenter, StyleConstants.ALIGN_CENTER);
            headerDoc.setParagraphAttributes(0, headerDoc.getLength(), headerCenter, false);


            //Receipt Footer
            JTextPane footerText = new JTextPane();
            footerText.setText(
                    "\n******************************************" +
                            "\nThis serves as your Official Receipt." +
                            "\nThank you, and please come again" +
                            "\nFor feedback, please call us at" +
                            "\nTel: ***********" +
                            "\nEmail: feedback.sample@sample.com" +
                            "\nVisit us also at www.jollibee.com.ph\n"
            );
            StyledDocument footerDoc = footerText.getStyledDocument();
            SimpleAttributeSet footerCenter = new SimpleAttributeSet();
            StyleConstants.setAlignment(footerCenter, StyleConstants.ALIGN_CENTER);
            footerDoc.setParagraphAttributes(0, footerDoc.getLength(), footerCenter, false);


            //Main Receipt
            JPanel mainReceiptPanel = new JPanel();
            mainReceiptPanel.setBackground(Color.white);
            mainReceiptPanel.setLayout(new BoxLayout(mainReceiptPanel, BoxLayout.Y_AXIS));

            String[] receiptColumnIdentifiers = {"Name", "Total"};
            receiptTableModel.setColumnIdentifiers(receiptColumnIdentifiers);

            String[] rowData = {"Sample", "2"};
            receiptTableModel.addRow(rowData);

            JTable receiptTable = new JTable(receiptTableModel);

            mainReceiptPanel.add(new JScrollPane(receiptTable));
            mainReceiptPanel.add(new JLabel("TOTAL: " + TOTAL));

            container.add(headerText, BorderLayout.NORTH);
            container.add(mainReceiptPanel, BorderLayout.CENTER);
            container.add(footerText, BorderLayout.SOUTH);
        }
    }

    public static void main(String args[]){
        Login login = new Login();
    }
}