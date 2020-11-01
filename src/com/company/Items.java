package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Items extends JFrame {
    public Items() {
        setVisible(false);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        // Components
        JTable tblItems = new JTable();
        DefaultTableModel tblItemsModel = new DefaultTableModel();
        String[] itemColumnIdentifiers = {"Name", "Price"};
        tblItemsModel.setColumnIdentifiers(itemColumnIdentifiers);
        tblItems.setModel(tblItemsModel);
        tblItems.setPreferredSize(new Dimension(500, 600));


        JTextField tfdItemName = new JTextField();
        tfdItemName.setPreferredSize(new Dimension(150, 50));
        tfdItemName.setMaximumSize(new Dimension(Integer.MAX_VALUE, tfdItemName.getPreferredSize().height));

        JTextField tfdItemPrice = new JTextField();
        tfdItemPrice.setPreferredSize(new Dimension(150, 50));
        tfdItemPrice.setMaximumSize(new Dimension(Integer.MAX_VALUE, tfdItemName.getPreferredSize().height));

        JButton btnSubmit = new JButton("Save Item");
        btnSubmit.setMargin(new Insets(10, 5, 10, 5));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.setBackground(Color.red);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setOpaque(true);
        btnSubmit.setForeground(Color.decode("#edf2f4"));


        // Functions

        // Layout
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setPreferredSize(new Dimension(300, 500));
        pnlForm.add(new JLabel("Product Name"));
        pnlForm.add(tfdItemName);
        pnlForm.add(new JLabel("Product Price"));
        pnlForm.add(tfdItemPrice);
        pnlForm.add(btnSubmit);

        setLayout(gridBagLayout);
        gridBagConstraints.insets = new Insets(0, 20, 0,20);
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.gridx = 0;
        add(new JScrollPane(tblItems), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        add(pnlForm, gridBagConstraints);
    }
}
